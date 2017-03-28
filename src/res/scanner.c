/* Read c source code from stdin,
 * Output tokens to stdout, error msg to stderr
 *
 * Don't support:
 *  comment,
 *  char literal,
 *  string literal,
 *  integer literal with postfix,
 *  floating number literal,
 */

#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static FILE* in_file;
static FILE* out_file;

enum token_t
{
    // keyword
    SPACE = 1,
    MAIN,
    IF,
    THEN,
    WHILE,
    DO,
    STATIC,
    INT,
    DOUBLE,
    STRUCT,
    BREAK,
    ELSE,
    LONG,
    SWITCH,
    CASE,
    TYPEDEF,
    CHAR,
    RETURN,
    CONST,
    FLOAT,
    SHORT,
    CONTINUE,
    FOR,
    VOID,
    DEFAULT,
    SIZEOF,
    // !~+-*\/%<>=&^|,
    OP_NOT,
    OP_BIN_NEGATE,
    OP_PLUS_DEREFERENCE,
    OP_MINUS,
    OP_MULTIPLY,
    OP_DIVIDE,
    OP_MOD,
    OP_ABOVE,
    OP_BLOW,
    OP_ASSIGN,
    OP_BIN_AND,
    OP_BIN_XOR,
    OP_BIN_OR,
    OP_COMMA,
    // "++", "--",  "->",  "<<", ">>", "<=", ">=",
    // "==", "!=",  "&&",  "||", "*=", "/=", "+=",
    // "-=", "<<=", ">>=", "&=", "|=", "^="
    OP_INCREMENT,
    OP_DECREMENT,
    OP_ACCESS_PTR,
    OP_LEFT_SHIFT,
    OP_RIGHT_SHIFT,
    OP_NOT_ABOVE,
    OP_NOT_BLOW,
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
    // (){}[];
    DIM_LPARENTHESE,
    DIM_RPARENTHESE,
    DIM_LBRACE,
    DIM_RBRACE,
    DIM_LSQUARE,
    DIM_RSQUARE,
    DIM_SEMICOLON,
    // identifier, integer literal
    IDENTIFIER,
    INTEGER
};

void eat_space(FILE* in_file);
/* return token type(an enum positive integer) if found, otherwise 0 */
int is_single_optor(int c);
int is_delimiter(int c);
int is_compound_optor(char* word);
int is_keyword(char* word);
/* output to out_file */
void output_token_char(enum token_t t, int c);
void output_token_word(enum token_t t, char* word);

int
main()
{
    /* some initialization */

    in_file = stdin;
    out_file = stdout;

    char word[256] = { 0 };

    /* word-level loop */

    int c;
    while ((c = getc(in_file)) != EOF) {
        int len = 0;

        /* 1. skip the space: space \t \n \r \f */

        if (isspace(c)) {
            eat_space(in_file);
            output_token_char(SPACE, ' ');
            continue;
        }

        /* 2. read a whole token */

        if (is_single_optor(c)) {
            // FIXME: <<= >>== didn't work
            word[len++] = c;
            // read next char
            int next_c;
            next_c = getc(in_file);
            // next char is optor?
            if (is_single_optor(next_c)) {
                word[len++] = next_c;
                word[len] = '\0';
                // two chars' compund optor?
                enum token_t t = is_compound_optor(word);
                if (t) {
                    // if yes
                    output_token_word(t, word);
                    continue;
                } else {
                    // if no
                    output_token_char(is_single_optor(word[0]), word[0]);
                    output_token_char(is_single_optor(word[1]), word[1]);
                    continue;
                }
            } else {
                output_token_char(is_single_optor(c), word[0]);
                ungetc(next_c, in_file);
                continue;
            }
        } else if (is_delimiter(c)) {
            output_token_char(is_delimiter(c), c);
            continue;
        } else if (isalpha(c) || c == '_') {
            word[len++] = c;

            int next_c = getc(in_file);
            while (isalpha(next_c) || isdigit(next_c) || next_c == '_') {
                word[len++] = next_c;
                next_c = getc(in_file);
            }
            ungetc(next_c, in_file);
            word[len] = '\0';

            // keyword or identifier
            enum token_t t = is_keyword(word);
            if (t) {
                output_token_word(t, word);
            } else {
                output_token_word(IDENTIFIER, word);
            }

            continue;
        } else if (isdigit(c)) {
            word[len++] = c;

            int next_c = getc(in_file);
            while (isdigit(next_c)) {
                word[len++] = next_c;
                next_c = getc(in_file);
            }
            if (isalpha(next_c)) {
                // special case in integer literal, aplha can't follow a
                // integer literal.
                // for example: 1234t
                goto fail;
            }
            ungetc(next_c, in_file);
            word[len] = '\0';

            output_token_word(INTEGER, word);
            continue;
        }
    }

    // return status to OS

    return 0;
fail:
    fprintf(stderr, "lexcial error!\n");
    return -1;
}

void
eat_space(FILE* in_file)
{
    int c;
    // until the char that is not space
    while (isspace((c = getc(in_file)))) {
        continue;
    }

    // put non-space char backinto stream
    ungetc(c, in_file);
}

int
is_single_optor(int c)
{
    char single_optor_str[] = "!~+-*/%<>=&^|,";

    // sizeof(single_optor_str) := its length + 1
    size_t len = sizeof(single_optor_str) - 1;
    for (size_t i = 0; i < len; i++) {
        if (c == single_optor_str[i]) {
            return OP_NOT + i;
        }
    }

    // if not found
    return 0;
}

int
is_delimiter(int c)
{
    char delimiter_str[] = "(){}[];";

    // sizeof(delimiter_str) := its length + 1
    size_t len = sizeof(delimiter_str) - 1;
    for (size_t i = 0; i < len; i++) {
        if (c == delimiter_str[i])
            return DIM_LPARENTHESE + i;
    }

    return 0;
}

int
is_compound_optor(char* word)
{
    char* compound_optor_tbl[] = { "++", "--",  "->",  "<<", ">>", "<=", ">=",
                                   "==", "!=",  "&&",  "||", "*=", "/=", "+=",
                                   "-=", "<<=", ">>=", "&=", "|=", "^=" };

    size_t len = sizeof(compound_optor_tbl) / sizeof(char*);
    for (size_t i = 0; i < len; i++) {
        if (!strcmp(word, compound_optor_tbl[i])) {
            return OP_INCREMENT + i;
        }
    }

    return 0;
}

int
is_keyword(char* word)
{
    char* keyword_tbl[] = {
        "main",    "if",     "then",    "while",  "do",    "static", "int",
        "double",  "struct", "break",   "else",   "long",  "switch", "case",
        "typedef", "char",   "return",  "const",  "float", "short",  "continue",
        "for",     "void",   "default", "sizeof", "do"
    };

    size_t len = sizeof(keyword_tbl) / sizeof(char*);
    for (size_t i = 0; i < len; i++) {
        if (!strcmp(word, keyword_tbl[i])) {
            return MAIN + i;
        }
    }

    return 0;
}

void
output_token_char(enum token_t t, int c)
{
    fprintf(out_file, "(%d, %c)\n", t, c);
}

void
output_token_word(enum token_t t, char* word)
{
    fprintf(out_file, "(%d, %s)\n", t, word);
}
