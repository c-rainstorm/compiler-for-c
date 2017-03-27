package com.rainstorm.lexer.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rainstorm on 3/27/17.
 */
public class Delimiter extends Token{

    private static Map<String, Delimiter> delimiters = new HashMap<>();

    static {
        delimiters.put("(", new Delimiter("(", Tag.DIM_LPARENTHESE));
        delimiters.put(")", new Delimiter(")", Tag.DIM_RPARENTHESE));
        delimiters.put("{", new Delimiter("{", Tag.DIM_LBRACE));
        delimiters.put("}", new Delimiter("}", Tag.DIM_RBRACE));
        delimiters.put("[", new Delimiter("[", Tag.DIM_LSQUARE));
        delimiters.put("]", new Delimiter("]", Tag.DIM_RSQUARE));
        delimiters.put(";", new Delimiter(";", Tag.DIM_SEMICOLON));
        delimiters.put(",", new Delimiter(",", Tag.DIM_COMMA));
        delimiters.put("#", new Delimiter("#", Tag.DIM_NUMBER_SIGN));
    }

    private Delimiter(String value, Tag tag) {
        super(value, tag);
    }

    public static boolean isDelimiter(char delimiter){
        return isDelimiter(String.valueOf(delimiter));
    }

    public static boolean isDelimiter(String delimiter){
        return delimiters.containsKey(delimiter);
    }

    public static Delimiter get(char delimiter){
        return get(String.valueOf(delimiter));
    }

    public static Delimiter get(String delimiter){
        return delimiters.get(delimiter);
    }

    public static void main(String[] args) {
        System.out.println(delimiters.get("("));
    }

}
