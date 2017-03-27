package com.rainstorm.lexer.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rainstorm on 3/27/17.
 */

public class Operator extends Token{

    private static Map<String, Operator> singleChar = new HashMap<>(32);
    private static Map<String, Operator> doubleChar = new HashMap<>(32);
    private static Map<String, Operator> tripleChar = new HashMap<>();

    static {
        singleChar.put("!", new Operator("!", Tag.OP_NOT));
        singleChar.put("~", new Operator("~", Tag.OP_BIN_NEGATE));
        singleChar.put("+", new Operator("+", Tag.OP_PLUS_DEREFERENCE));
        singleChar.put("-", new Operator("-", Tag.OP_MINUS));
        singleChar.put("*", new Operator("*", Tag.OP_MULTIPLY));
        singleChar.put("/", new Operator("/", Tag.OP_DIVIDE));
        singleChar.put("%", new Operator("%", Tag.OP_MOD));
        singleChar.put("<", new Operator("<", Tag.OP_LESS));
        singleChar.put(">", new Operator(">", Tag.OP_GREAT));
        singleChar.put("=", new Operator("=", Tag.OP_ASSIGN));
        singleChar.put("&", new Operator("&", Tag.OP_BIN_AND));
        singleChar.put("^", new Operator("^", Tag.OP_BIN_XOR));
        singleChar.put("|", new Operator("|", Tag.OP_BIN_OR));
        singleChar.put(":", new Operator(":", Tag.OP_COLON));
        singleChar.put("?", new Operator("?", Tag.OP_QUESTION));
        singleChar.put(".", new Operator(".", Tag.OP_DOT));

        doubleChar.put("++", new Operator("++", Tag.OP_INCREMENT));
        doubleChar.put("--", new Operator("--", Tag.OP_DECREMENT));
        doubleChar.put("->", new Operator("->", Tag.OP_ACCESS_PTR));
        doubleChar.put("<<", new Operator("<<", Tag.OP_LEFT_SHIFT));
        doubleChar.put(">>", new Operator(">>", Tag.OP_RIGHT_SHIFT));
        doubleChar.put("<=", new Operator("<=", Tag.OP_LESS_EQUAL));
        doubleChar.put(">=", new Operator(">=", Tag.OP_GREAT_EQUAL));
        doubleChar.put("==", new Operator("==", Tag.OP_EQUAL));
        doubleChar.put("!=", new Operator("!=", Tag.OP_NOT_EQUAL));
        doubleChar.put("&&", new Operator("&&", Tag.OP_LOGIC_AND));
        doubleChar.put("||", new Operator("||", Tag.OP_LOGIC_OR));
        doubleChar.put("*=", new Operator("*=", Tag.OP_MULTIPLY_ASSIGN));
        doubleChar.put("/=", new Operator("/=", Tag.OP_DIVIDE_ASSIGN));
        doubleChar.put("+=", new Operator("+=", Tag.OP_ADD_ASSIGN));
        doubleChar.put("-=", new Operator("-=", Tag.OP_MINUS_ASSIGN));
        doubleChar.put("&=", new Operator("&=", Tag.OP_BIN_AND_ASSIGN));
        doubleChar.put("^=", new Operator("^=", Tag.OP_BIN_XOR_ASSIGN));
        doubleChar.put("|=", new Operator("|=", Tag.OP_BIN_OR_ASSIGN));

        tripleChar.put("<<=", new Operator("<<=", Tag.OP_LSHIFT_ASSIGN));
        tripleChar.put(">>=", new Operator(">>=", Tag.OP_RSHIFT_ASSIGN));

    }

    private Operator(String value, Tag tag) {
        super(value, tag);
    }

    public static boolean isOperator(char operator){
        return isOperator(String.valueOf(operator));
    }

    public static boolean isOperator(String operator){
        if(operator == null){
            return false;
        }
        int length = operator.length();

        if(length == 1){
            return singleChar.containsKey(operator);
        }else if(length == 2){
            return doubleChar.containsKey(operator);
        }else if(length == 3){
            return tripleChar.containsKey(operator);
        }

        return false;
    }

    public static Operator get(char operator){
        return get(String.valueOf(operator));
    }
    public static Operator get(String operator){
        if(operator == null){
            return null;
        }
        int length = operator.length();

        if(length == 1){
            return singleChar.get(operator);
        }else if(length == 2){
            return doubleChar.get(operator);
        }else if(length == 3){
            return tripleChar.get(operator);
        }

        return null;
    }


}
