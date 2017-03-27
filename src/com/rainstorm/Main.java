package com.rainstorm;

import com.rainstorm.lexer.Lexer;
import com.rainstorm.lexer.entity.Token;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Lexer lexer = new Lexer("/home/rainstorm/workspace/idea/Lexer/src/res/test.c");
        try {
            List<Token> tokens = lexer.scan();
            for(Token token : tokens){
                System.out.println(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
