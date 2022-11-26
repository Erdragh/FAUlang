package com.github.erdragh.faulang.einlesegeraet;

import com.github.erdragh.faulang.Haupt;
import com.github.erdragh.faulang.symbol.Symbol;
import com.github.erdragh.faulang.symbol.SymbolTyp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.erdragh.faulang.symbol.SymbolTyp.*;

public class Einlesegeraet {
    private final String quelle;
    private final List<Symbol> symbole = new ArrayList<>();
    private int start = 0;
    private int aktuell = 0;
    private int zeile = 1;

    private static final Map<String, SymbolTyp> schluesselwoerter;

    static {
        schluesselwoerter = new HashMap<>();
        schluesselwoerter.put("Verum", VERUM);
        schluesselwoerter.put("Falsum", FALSUM);
        schluesselwoerter.put("AbbildungVon", ABBILDUNG_VON);
        schluesselwoerter.put("Nach", NACH);
        schluesselwoerter.put("Verstanden?", VERSTANDEN);
        schluesselwoerter.put("Ja", JA);
        schluesselwoerter.put("Nein?Ok", NEIN_OK);
        schluesselwoerter.put("Versuche", VERSUCHE);
        schluesselwoerter.put("Scheitere", SCHEITERE);
        schluesselwoerter.put("Sei", SEI);
        schluesselwoerter.put("Wenn", WENN);
        schluesselwoerter.put("Dann", DANN);
        schluesselwoerter.put("Sonst", SONST);
    }

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
            case ',': symbolHinzufuegen(KOMMA); break;
            case '.': symbolHinzufuegen(PUNKT); break;
            // Kommentare gehen bis ans ende der Zeile
            case '#': while (vorausschauen() != '\n' && !istAmEnde()) fortschreiten();
            break;
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
                } else if (istAlpha(z)) {
                    bezeichner();
                } else {
                    Haupt.fehler(zeile, "lern besser schreiben, falsches Zeichen!");
                }
                break;
        }
    }

    private void bezeichner() {
        while (istAlphaZahlisch(vorausschauen())) fortschreiten();

        String text = quelle.substring(start, aktuell);
        SymbolTyp typ = schluesselwoerter.get(text);
        if (typ == null) typ = BEZEICHNER;

        symbolHinzufuegen(typ);
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
        while (vorausschauen() != '"' && !istAmEnde()) {
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

    private boolean istAlpha(char z) {
        return (z >= 'a' && z <= 'z') || (z >= 'A' && z <= 'Z') || z == '_';
    }

    private boolean istAlphaZahlisch(char z) {
        // Fragezeichen muss hier auch behandlet werden, damit
        // das Verstanden? Schlüsselwort funktioniert
        return istAlpha(z) || Character.isDigit(z) || z == '?';
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
