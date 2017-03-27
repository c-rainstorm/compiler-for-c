package com.rainstorm.lexer.entity;

/**
 * Created by rainstorm on 3/26/17.
 */
public enum Tag {
    SPACE,
    AUTO,
    SIGNED,
    UNSIGNED,
    CONST,
    STATIC,
    REGISTER,
    VOID,
    CHAR,
    SHORT,
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    SWITCH,
    CASE,
    IF,
    ELSE,
    DO,
    WHILE,
    FOR,
    BREAK,
    CONTINUE,
    RETURN,
    DEFAULT,
    SIZEOF,
    ENTRY,
    EXTERN,
    TYPEDEF,
    STRUCT,
    UNION,
    ENUM,
    GOTO,
    VOLATILE,

    // !~+-*/%<>=&^|?:.
    OP_NOT,
    OP_BIN_NEGATE,
    OP_PLUS_DEREFERENCE,
    OP_MINUS,
    OP_MULTIPLY,
    OP_DIVIDE,
    OP_MOD,
    OP_LESS,
    OP_GREAT,
    OP_ASSIGN,
    OP_BIN_AND,
    OP_BIN_XOR,
    OP_BIN_OR,
    OP_QUESTION,
    OP_COLON,
    OP_DOT,

    // "++", "--",  "->",  "<<", ">>", "<=", ">=",
    // "==", "!=",  "&&",  "||", "*=", "/=", "+=",
    // "-=", "<<=", ">>=", "&=", "|=", "^="
    OP_INCREMENT,
    OP_DECREMENT,
    OP_ACCESS_PTR,
    OP_LEFT_SHIFT,
    OP_RIGHT_SHIFT,
    OP_LESS_EQUAL,
    OP_GREAT_EQUAL,
    OP_EQUAL,
    OP_NOT_EQUAL,
    OP_LOGIC_AND,
    OP_LOGIC_OR,
    OP_MULTIPLY_ASSIGN,
    OP_DIVIDE_ASSIGN,
    OP_ADD_ASSIGN,
    OP_MINUS_ASSIGN,
    OP_LSHIFT_ASSIGN,
    OP_RSHIFT_ASSIGN,
    OP_BIN_AND_ASSIGN,
    OP_BIN_XOR_ASSIGN,
    OP_BIN_OR_ASSIGN,

    // (){}[];,#
    DIM_LPARENTHESE,
    DIM_RPARENTHESE,
    DIM_LBRACE,
    DIM_RBRACE,
    DIM_LSQUARE,
    DIM_RSQUARE,
    DIM_SEMICOLON,
    DIM_COMMA,
    DIM_NUMBER_SIGN,

    // identifier, integer literal
    IDENTIFIER,
    INTEGER,
    REAL,
    STRING;

    @Override
    public String toString() {
        return Integer.toString(this.ordinal());
    }

    public static void main(String[] args) {
        System.out.println(Tag.BREAK);
    }
}
