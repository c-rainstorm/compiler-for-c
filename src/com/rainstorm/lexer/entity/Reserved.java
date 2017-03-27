package com.rainstorm.lexer.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rainstorm on 3/27/17.
 */
public class Reserved extends Token {

    private static Map<String, Reserved> reserves = new HashMap<>(64);

    static {
        reserves.put("auto", new Reserved("auto", Tag.AUTO));
        reserves.put("signed", new Reserved("signed", Tag.SIGNED));
        reserves.put("unsigned", new Reserved("unsigned", Tag.UNSIGNED));
        reserves.put("const", new Reserved("const", Tag.CONST));
        reserves.put("static", new Reserved("static", Tag.STATIC));
        reserves.put("register", new Reserved("register", Tag.REGISTER));
        reserves.put("void", new Reserved("void", Tag.VOID));
        reserves.put("char", new Reserved("char", Tag.CHAR));
        reserves.put("short", new Reserved("short", Tag.SHORT));
        reserves.put("int", new Reserved("int", Tag.INT));
        reserves.put("long", new Reserved("long", Tag.LONG));
        reserves.put("float", new Reserved("float", Tag.FLOAT));
        reserves.put("double", new Reserved("double", Tag.DOUBLE));
        reserves.put("switch", new Reserved("switch", Tag.SWITCH));
        reserves.put("case", new Reserved("case", Tag.CASE));
        reserves.put("if", new Reserved("if", Tag.IF));
        reserves.put("else", new Reserved("else", Tag.ELSE));
        reserves.put("do", new Reserved("do", Tag.DO));
        reserves.put("while", new Reserved("while", Tag.WHILE));
        reserves.put("for", new Reserved("for", Tag.FOR));
        reserves.put("break", new Reserved("break", Tag.BREAK));
        reserves.put("continue", new Reserved("continue", Tag.CONTINUE));
        reserves.put("return", new Reserved("return", Tag.RETURN));
        reserves.put("default", new Reserved("default", Tag.DEFAULT));
        reserves.put("sizeof", new Reserved("sizeof", Tag.SIZEOF));
        reserves.put("entry", new Reserved("entry", Tag.ENTRY));
        reserves.put("extern", new Reserved("extern", Tag.EXTERN));
        reserves.put("typedef", new Reserved("typedef", Tag.TYPEDEF));
        reserves.put("struct", new Reserved("struct", Tag.STRUCT));
        reserves.put("union", new Reserved("union", Tag.UNION));
        reserves.put("enum", new Reserved("enum", Tag.ENUM));
        reserves.put("goto", new Reserved("goto", Tag.GOTO));
        reserves.put("volatile", new Reserved("volatile", Tag.VOLATILE));
    }

    private Reserved(String value, Tag tag) {
        super(value, tag);
        int main;
    }

    public static boolean isReserved(String reserved){
        return reserves.containsKey(reserved);
    }

    public static Reserved get(String reserved){
        return reserves.get(reserved);
    }
}
