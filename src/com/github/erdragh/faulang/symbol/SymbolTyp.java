package com.github.erdragh.faulang.symbol;

public enum SymbolTyp {
    // Einzeichige Symbole
    LINKE_GESCHWEIFTE_KLAMMER, RECHTE_GESCHWEIFTE_KLAMMER, DOPPELPUNKT, GROESSER_ALS,
    PLUS, MINUS, STERN, SCHRAEGSTRICH, NICHT, KOMMA, PUNKT,

    // Zweizeichige Symbole
    NICHT_UND,

    // LITERALE
    BEZEICHNER, ZEICHENKETTE, ZAHL,

    // SCHLÜSSELWÖRTER
    VERUM, FALSUM, ABBILDUNG_VON, NACH, VERSTANDEN, JA, NEIN_OK, VERSUCHE, SCHEITERE,
    SEI, WENN, DANN, SONST,

    EOF
}
