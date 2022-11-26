package com.github.erdragh.faulang.symbol;

public class Symbol {
    private final SymbolTyp typ;
    private final String lexem;
    private final Object literal;
    private final int zeile;

    public Symbol(SymbolTyp typ, String lexem, Object literal, int zeile) {
        this.typ = typ; this.lexem = lexem; this.literal = literal; this.zeile = zeile;
    }

    @Override
    public String toString() {
        return typ + " '" + lexem + "' " + (literal != null ? (literal + " ") : " ") + zeile;
    }
}
