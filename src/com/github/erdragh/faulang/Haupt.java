package com.github.erdragh.faulang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Haupt {

  public static final String NAME = "faul";

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
  }

  private static void eingabeFaulen() throws IOException {
    InputStreamReader eingabe = new InputStreamReader(System.in);
    BufferedReader leser = new BufferedReader(eingabe);

    while (true) {
      System.out.print(">> ");
      String zeile = leser.readLine();
      if (zeile == null) break;
      faulen(zeile);
    }
  }

  private static void faulen(String skript) {
    System.out.println("FAUlang wird nun folgendes Skript korrigieren:");
    System.out.println(skript);
  }
}
