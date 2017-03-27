package com.rainstorm.lexer.entity;

/**
 * Created by rainstorm on 3/27/17.
 */
public class Identifier extends Token {
    public Identifier(String value) {
        super(value, Tag.IDENTIFIER);
    }
}
