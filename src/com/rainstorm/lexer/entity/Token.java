package com.rainstorm.lexer.entity;

/**
 * Created by rainstorm on 3/26/17.
 */
public class Token {
    public Tag tag;
    public String value;

    public static Token SPACE = new Token(" ", Tag.SPACE);

    public Token(String value, Tag tag){
        this.value = value;
        this.tag = tag;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(value).append(", ").append(tag.toString()).append(")");

        return builder.toString();
    }
}
