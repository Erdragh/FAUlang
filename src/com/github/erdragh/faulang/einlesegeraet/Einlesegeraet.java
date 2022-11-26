package com.github.erdragh.faulang.einlesegeraet;

import com.github.erdragh.faulang.Haupt;
import com.github.erdragh.faulang.symbol.Symbol;
import com.github.erdragh.faulang.symbol.SymbolTyp;

import java.util.ArrayList;
import java.util.List;

import static com.github.erdragh.faulang.symbol.SymbolTyp.*;

public class Einlesegeraet {
    private final String quelle;
    private final List<Symbol> symbole = new ArrayList<>();
    private int start = 0;
    private int aktuell = 0;
    private int zeile = 1;

    public Einlesegeraet(String quelle) {
        this.quelle = quelle;
    }

    public List<Symbol> symboleEinlesen() {
        while (!istAmEnde()) {
            // Wir befinden uns am Beginn des neuen Lexems
            start = aktuell;
            symbolEinlesen();
        }

        symbole.add(new Symbol(EOF, "", null, zeile));
        return symbole;
    }

    private void symbolEinlesen() {
        char z = fortschreiten();
        switch (z) {
            case '{': symbolHinzufuegen(LINKE_GESCHWEIFTE_KLAMMER); break;
            case '}': symbolHinzufuegen(RECHTE_GESCHWEIFTE_KLAMMER); break;
            case ':': symbolHinzufuegen(DOPPELPUNKT); break;
            case '>': symbolHinzufuegen(GROESSER_ALS); break;
            case '+': symbolHinzufuegen(PLUS); break;
            case '-': symbolHinzufuegen(MINUS); break;
            case '*': symbolHinzufuegen(STERN); break;
            case '/': symbolHinzufuegen(SCHRAEGSTRICH); break;
            case '!': symbolHinzufuegen(vergleichen('&') ? NICHT_UND : NICHT);
            default:
                Haupt.fehler(zeile, "lern besser schreiben, falsches Zeichen!");
                break;
        }
    }

    private boolean vergleichen(char erwartet) {
        if (istAmEnde()) return false;
        if (quelle.charAt(aktuell) != erwartet) return false;

        aktuell++;
        return true;
    }

    private boolean istAmEnde() {
        return aktuell >= quelle.length();
    }

    private char fortschreiten() {
        return quelle.charAt(aktuell++);
    }

    private void symbolHinzufuegen(SymbolTyp typ) {
        symbolHinzufuegen(typ, null);
    }

    private void symbolHinzufuegen(SymbolTyp typ, Object literal) {
        String text = quelle.substring(start, aktuell);
        symbole.add(new Symbol(typ, text, literal, zeile));
    }
}
