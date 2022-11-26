# Syntax von FAUlang

Das hier ist eine Sammlung an Ideen für die Syntax der FAUlang.

  ```
  pruefungAnmelden: AbbildungVon {} Nach Erfuelltheit Verstanden?
    Wenn CampoFunktioniert{} Dann Verstanden?
      Ja Verum
    Nein?Ok
    Sonst Verstanden?
      Ja Falsum
    Nein?Ok
  Nein?Ok
  
  pruefungAntreten: AbbildungVon {} Nach Zahl Verstanden?
    Ja Zufall{0, 100}
  Nein?Ok
  
  whatsApp: AbbildungVon {nachricht: Zeichenkette} Nach Nichts Verstanden?
    Drucken{nachricht}
  Nein?Ok
  
  exmatrikuliere: AbbildungVon {} Nach Nichts Verstanden?
    Drucken{"Kein Bock Mehr"}
  Nein?Ok
  
  problemAusgeben: AbbildungVon {problem: Problem} Nach Nichts Verstanden?
    Drucken{problem.nachricht}
  Nein?Ok
  
  istBestanden: AbbildungVon {punktezahl: Zahl} Nach Erfuelltheit Verstanden?
    Ja punktestand > 50
  Nein?Ok
    
  haupt: AbbildungVon {parameter: Menge} Nach Nichts Verstanden?
    Versuche Verstanden?
      Sei angemeldet: Erfuelltheit pruefungAnmelden{}
      Wenn angemeldet Dann Verstanden?
      Sei ergebnis: Zahl pruefungAntreten{}
        Wenn istBestanden{ergebnis} Dann Verstanden?
          whatsApp{"Alter geil, ich hab in der Prüfung " + ergebnis + " Punkte"}
        Nein?Ok
        Sonst Verstanden?
          exmatrikuliere{}
        Nein?Ok
      Nein?Ok
    Nein?Ok
    Scheitere An problem: Problem Verstanden?
      problemAusgeben{problem}
    Nein?Ok
  Nein?Ok
  ```
Anmerkungen:

* Ausdrücke, die mit einem Kleinbuchstaben anfangen sind vom Nutzer definiert.
* Ausdrücke, die mit einem Großbuchstaben anfangen sind vom System.
* Ein Doppelpunkt stellt eine Typ-Zuweisung dar.
* Weißplatz ist signifikant
* Es gibt trotzdem eine Deklaration für den Beginn und das Ende eines Quellcodeabteils:
  `Verstanden?` für den Anfang und `Nein?Ok` für das Ende. 
* Der Ausdruck, der den Wert in einer Methode zurückgibt, ist `Ja`, was signalisiert, dass
  man den Zweck der Methode verstanden hat und somit einen Wert zurückgeben kann.
* Methodenaufrufe werden mit dem Bezeichner der Methode und geschweiften Klammern
  ausgeführt.
* Es existieren folgende Datentypen:
  * `Nichts`: Stellt das Nicht-Vorhandensein eines Wertes dar.
  * `Erfuelltheit`: Ist entweder `Verum` oder `Falsum`
  * `Zahl`: Stellt eine Zahl dar. Es gibt keine Unterscheidung zwischen Ganz- und Gleitkommazahlen
  * `Problem`: Stellt dar, dass während dem Ausführen einer Methode ein Fehler aufgetreten ist
  * `Menge`: Stellt eine indizierte Liste an `Objekt`en dar.
  * `Objekt`: Stellt eine Sammlung bzw. ein Wörterbuch dar, was einer `Zeichenkette` einen
    Wert zuweist. Jeder gespeicherte Wert in diesem Objekt kann wieder einen der Datentypen
    annehmen.
  * `Zeichenkette`: Stellt einen Text dar.
* Es existieren folgende Abbildungen, die das System anbietet:
  * `CampoFunktioniert{}`: Gibt einen zufälligen Wert des Datentyps `Erfuelltheit` zurück
  * `Zufall{min: Zahl, max: Zahl}`: Gibt eine zufällige `Zahl` zurück, die zwischen
    `min` und `max` liegt.
  * `Drucken{nachricht: Zeichenkette}`: Gibt die Nachricht in der Konsole aus.
  * `ZeichenketteLaenge{zeichenkette: Zeichenkette}`: Gibt eine `Zahl` zurück, die die Länge
    der `Zeichenkette` darstellt.
  * `Unterzeichenkette{zeichenkette: Zeichenkette, start: Zahl, ende: Zahl}`: Gibt den Ausschnitt
    aus der `Zeichenkette` zurück, der bei `start` beginnt und bis `ende` (exklusiv) geht.
* Es gibt die normalen Ausdrücke, mit denen `Zahl`enwerte verrechnet werden können:
  * `+`: Addiert die beiden Werte
  * `-`: Subtrahiert den hinteren Wert vom vorderen Wert
  * `*`: Multipliziert die beiden Werte miteinander
  * `/`: Teilt den vorderen Wert durch den hinteren
* Es gibt einen Ausdruck, mit dem `Zahl`enwerte verglichen werden können:
  * `<`: entspricht `Verum`, falls die linke `Zahl` kleiner als die rechte ist. Wenn nicht,
    entspricht es `Falsum`
* Für die Manipulation von `Zeichenkette`n gibt es folgende Möglichkeiten:
  * `+`: Werden zwei Zeichenketten addiert, so kommt am Ende eine Verkettung dieser beiden
    `Zeichenkette`n heraus
  * `Unterzeichenkette{zeichenkette: Zeichenkette, start: Zahl, ende: Zahl}`: Gibt den Ausschnitt
    aus der `Zeichenkette` zurück, der bei `start` beginnt und bis `ende` (exklusiv) geht.
* Eine neue Variable erstellt man in dem man ihr mit dem Schlüsselwort `Sei` zuerst einen Namen
  und dann einen Wert zuweisen. Hierbei ist zu beachten, dass ein Wert nur einmal im Arbeitsspeicher
  vorkommen darf, da es ja sonst ein Plagiat wäre.