package com.rainstorm.lexer.entity;

/**
 * Created by rainstorm on 3/27/17.
 */
public class Real extends Token{
    public Real(String value) {
        super(value, Tag.REAL);
    }
}
