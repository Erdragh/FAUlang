package com.github.erdragh.faulang;

public class Haupt {

  public static final String NAME = "faul";

  public static void main(String[] parameter) {
    if (parameter.length > 1) {
      System.out.println("Benutzen: " + NAME + " [Skript]");
      System.exit(64);
    } else if (parameter.length == 1) {
      dateiFaulen(parameter[0]);
    } else {
      eingabeFaulen();
    }
  }

  private static void dateiFaulen(String skriptPfad) {

  }

  private static void eingabeFaulen() {

  }
}
