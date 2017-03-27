package com.rainstorm.lexer.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rainstorm on 3/27/17.
 */
public class LString extends Token{

    private static Map<String, LString> quotes = new HashMap<>();

    static {
        quotes.put("'", new LString("'"));
        quotes.put("\"", new LString("\""));
    }

    public LString(String value) {
        super(value, Tag.STRING);
    }

    public static boolean isQuote(char quote){
        return quotes.containsKey(String.valueOf(quote));
    }

    public static void main(String[] args) {
        System.out.println("\"");
    }
}
