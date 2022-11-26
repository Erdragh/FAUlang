package com.github.erdragh.faulang;

import com.github.erdragh.faulang.einlesegeraet.Einlesegeraet;
import com.github.erdragh.faulang.symbol.Symbol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Haupt {

  public static final String NAME = "faul";

  static boolean sollteExmatrikulieren = false;

  public static void main(String[] parameter) throws IOException {
    if (parameter.length > 1) {
      System.out.println("Benutzen: " + NAME + " [Skript]");
      System.exit(64);
    } else if (parameter.length == 1) {
      dateiFaulen(parameter[0]);
    } else {
      eingabeFaulen();
    }
  }

  private static void dateiFaulen(String skriptPfad) throws IOException {
    byte[] dateiAlsBytes = Files.readAllBytes(Paths.get(skriptPfad));
    faulen(new String(dateiAlsBytes, Charset.defaultCharset()));
    // Falls der dumme Nutzer mal wieder Scheiße gebaut hat, sollte
    // der Interpretierer beendet werden.
    if (sollteExmatrikulieren) {
      System.err.println("Antrag auf Exmatrikulation: https://www.fau.de/files/2013/10/Exmatrikulation.pdf");
      System.err.println("Du Idiot hast Fehler gemacht:");
      System.exit(65);
    }
  }

  private static void eingabeFaulen() throws IOException {
    InputStreamReader eingabe = new InputStreamReader(System.in);
    BufferedReader leser = new BufferedReader(eingabe);

    while (true) {
      System.out.print(">> ");
      String zeile = leser.readLine();
      if (zeile == null) break;
      faulen(zeile);
      sollteExmatrikulieren = false;
    }
  }

  private static void faulen(String skript) {
    Einlesegeraet einlesegeraet = new Einlesegeraet(skript);
    List<Symbol> symbole = einlesegeraet.symboleEinlesen();

    for (Symbol symbol : symbole) {
      System.out.println(symbol);
    }
  }

  public static void fehler(int zeile, String grund) {
    anzeigeIstRaus(zeile, "", grund);
  }

  private static void anzeigeIstRaus(int zeile, String ort, String grund) {
    System.err.println("in Zeile: " + zeile + " gemacht: " + grund);
    if (ort != null) System.err.println("Komm, geh da hin und entschuldige dich: " + ort);
    sollteExmatrikulieren = true;
  }
}
