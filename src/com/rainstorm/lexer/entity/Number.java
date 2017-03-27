package com.rainstorm.lexer.entity;

/**
 * Created by rainstorm on 3/27/17.
 */
public class Number extends Token {
    public Number(String value) {
        super(value, Tag.INTEGER);
    }
}
