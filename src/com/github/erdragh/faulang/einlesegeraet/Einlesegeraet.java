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
            // Kommentare gehen bis ans ende der Zeile
            case '#': while (vorausschauen() != '\n' && !istAmEnde()) fortschreiten();
            case '!': symbolHinzufuegen(vergleichen('&') ? NICHT_UND : NICHT);
            // TODO: Weißplatz nicht ignorieren.
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                zeile++;
                break;
            case '"': zeichenkette(); break;
            default:
                if (Character.isDigit(z)) {
                    zahl();
                } else {
                    Haupt.fehler(zeile, "lern besser schreiben, falsches Zeichen!");
                }
                break;
        }
    }

    private void zahl() {
        while (Character.isDigit(vorausschauen())) fortschreiten();

        if (vorausschauen() == '.' && Character.isDigit(zweiVorausschauen())) {
            fortschreiten();

            while (Character.isDigit(vorausschauen())) fortschreiten();
        }

        symbolHinzufuegen(ZAHL, Double.parseDouble(quelle.substring(start, aktuell)));
    }

    private void zeichenkette() {
        while (vorausschauen() != '"' && istAmEnde()) {
            if (vorausschauen() == '\n') zeile++;
            fortschreiten();
        }
        if (istAmEnde()) {
            Haupt.fehler(zeile, "Zeichenkette wurde nicht geschlossen.");
            return;
        }
        fortschreiten();

        String wert = quelle.substring(start + 1, aktuell - 1);
        symbolHinzufuegen(ZEICHENKETTE, wert);
    }

    private boolean vergleichen(char erwartet) {
        if (istAmEnde()) return false;
        if (quelle.charAt(aktuell) != erwartet) return false;

        aktuell++;
        return true;
    }

    private char vorausschauen() {
        if (istAmEnde()) return '\0';
        return quelle.charAt(aktuell);
    }

    private char zweiVorausschauen() {
        if (aktuell + 1 >= quelle.length()) return '\0';
        return quelle.charAt(aktuell + 1);
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
