package com.rainstorm.lexer;

import com.rainstorm.lexer.entity.*;
import com.rainstorm.lexer.entity.Number;
import com.rainstorm.lexer.exception.SyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 词法分析器。
 * 1. 支持提示语法错误所在行号
 * 2. 支持注释（单行，多行）
 * 3. 支持浮点数
 * 4. 支持字符串字面量
 * 5. 支持字符字面量（单字符或转义字符）
 */
public class Lexer {
    private static Logger logger = LogManager.getLogger();
    private int line = 1;
    private List<Token> tokens = new ArrayList<>(512);
    private BufferedReader reader;
    private boolean EOF = false;
    private char peek = ' ';

    public Lexer(String file) {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Lexer(InputStream in) {
        reader = new BufferedReader(new InputStreamReader(in));
    }

    /**
     * 扫描整个源文件，返回 包含所有 Token 的列表
     *
     * @return Token 列表
     * @throws IOException
     */
    public List<Token> scan() throws IOException {
        try {
            while (!EOF) {
                removeWhite();
                if (Delimiter.isDelimiter(peek)) {
                    tokens.add(Delimiter.get(peek));
                    readch();
                } else if (Operator.isOperator(peek)) {
                    Token token = getOperator();
                    if (token != Token.SPACE) {
                        tokens.add(token);
                    }
                } else if (Character.isDigit(peek)) {
                    tokens.add(getDigit());
                } else if (LString.isQuote(peek)) {
                    tokens.add(getLString());
                } else if (Character.isLetter(peek) || peek == '_') {
                    tokens.add(getIdentifier());
                } else if(!EOF){
                    throw new SyntaxException("Error: line " + line + " Syntax error!");
                }
            }

        } catch (SyntaxException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            reader.close();
        }
        return tokens;
    }

    /**
     * 获取一个标志符 Token
     * <p>
     * 包括 保留字 和 标识符
     *
     * @return
     */
    private Token getIdentifier() throws IOException {
        StringBuilder builder = new StringBuilder(64);

        // 读取整个标识符
        do {
            builder.append(peek);
            readch();
        } while (Character.isLetterOrDigit(peek) || peek == '_');

        String id = builder.toString();

        Token token;
        if (Reserved.isReserved(id)) {
            token = Reserved.get(id);
        } else {
            token = new Identifier(id);
        }

        return token;
    }

    /**
     * 获取一个字符字面量或字符串字面量 Token
     * <p>
     * 字符
     *
     * @return
     */
    private Token getLString() throws IOException, SyntaxException {

        StringBuilder builder = new StringBuilder(64);
        builder.append(peek);
        if (peek == '\'') {
            //如果是单引号
            // 单引号有两种情况：1. 普通字符； 2.转义字符。
            // 其中转义字符是两个字符，普通字符为一个。
            readch();
            assert EOF == false;
            if (peek == '\\') {
                // 如果是转义字符
                builder.append(peek);
                readch();
            }
            builder.append(peek);
            readch();
            assert EOF == false;
            if (peek != '\'') {
                //如果超过了两个字符
                throw new SyntaxException("Error: line " + line + " Syntax error!");
            }
            builder.append(peek);


        } else {
            /**
             * !!!!! 注释中引号未处理 ！！！！！！
             */
            for (readch(); !EOF; readch()) {
                if (peek == '"' && '\\' != builder.charAt(builder.length() - 1)) {
                    //该双引号未转义
                    builder.append(peek);
                    break;
                }
                builder.append(peek);
            }
        }

        LString token;
        //若第二个引号后字符不合法，抛出异常
        readch();
        if (EOF || Character.isWhitespace(peek)
                || Delimiter.isDelimiter(peek)
                || Operator.isOperator(peek)) {
            token = new LString(builder.toString());
        } else {
            throw new SyntaxException("Error: line " + line + " Syntax error!");
        }

        return token;
    }


    /**
     * 获取一个完整的 数字 Token（整数，实数）
     *
     * @return
     */
    private Token getDigit() throws IOException, SyntaxException {
        boolean isInteger = true;
        StringBuilder builder = new StringBuilder(64);
        do {
            builder.append(peek);
            readch();
        } while (Character.isDigit(peek));
        if (peek == '.') {

            isInteger = false;
            do {
                builder.append(peek);
                readch();
            } while (Character.isDigit(peek));
        }

        if (Character.isLetter(peek)) {
            throw new SyntaxException("Error: line " + line + " Syntax error!");
        }

        Token token;

        if (isInteger) {
            token = new Number(builder.toString());
        } else {
            token = new Real(builder.toString());
        }

        return token;
    }

    /**
     * 获取一个操作符 Token （包括单字符的，双字符的，三字符的）
     * <p>
     * 期间自动过滤注释
     *
     * @return
     */
    private Token getOperator() throws IOException, SyntaxException {
        StringBuilder builder = new StringBuilder();
        do {
            builder.append(peek);
            readch();
            if (builder.charAt(builder.length() - 1) == '/' && peek == '/') {
                //过滤单行注释
                builder.deleteCharAt(builder.length() - 1);
                while (!EOF) {
                    readch();
                    if (peek == '\n') {
                        line++;
                        readch();
                        break;
                    }
                }
            } else if (builder.charAt(builder.length() - 1) == '/' && peek == '*') {
                //过滤多行注释
                builder.deleteCharAt(builder.length() - 1);
                char first = ' ';
                while (!EOF) {
                    readch();
                    if (peek == '\n') {
                        line++;
                    }
                    if (first == ' ' && peek == '*') {
                        first = '*';
                    } else if (first == '*' && peek == '/') {
                        readch();
                        break;
                    } else {
                        first = ' ';
                    }
                }
            }

        } while (Operator.isOperator(peek));

        Token operator;
        String ope = builder.toString();
        if (builder.length() == 0) {
            operator = Token.SPACE;
        } else if (Operator.isOperator(ope)) {
            operator = Operator.get(ope);
        } else {
            throw new SyntaxException("Error: line " + line + " Syntax error!");
        }

        return operator;
    }

    /**
     * 消除连续的空白符
     *
     * @throws IOException
     */
    private void removeWhite() throws IOException {
        if (!Character.isWhitespace(peek)) {
            return;
        }
        for (; !EOF; readch()) {
            if ('\n' == peek) {
                line++;
            } else if (Character.isWhitespace(peek)) {
                continue;
            } else {
                break;
            }
        }
    }

    /**
     * 读取一个字符到 peek， 当读到文件尾时，EOF 标记为 true，peek 为 -1（不可用）
     *
     * @throws IOException
     */
    private void readch() throws IOException {
        int ch = reader.read();

        if (ch == -1) {
            EOF = true;
        }

        peek = (char) ch;
        if (logger.isDebugEnabled()) {
            logger.debug(peek);
        }
    }

    public static void main(String[] args) {
        System.out.println(Character.isWhitespace('\t') + "\"");
        System.out.println(Character.isWhitespace(' '));
        System.out.println(Character.isWhitespace('\n'));
        System.out.println(Character.isWhitespace('\r'));
        System.out.println(Character.isWhitespace('\f'));
    }

}
