# Einleitung

Dies ist eine kleine Kommandozeilen-Anwendung, die es einem ermöglicht mit intergalaktischen Zahlbegriffen zu rechnen
und Gütern für den intergalaktischen Handel Werte zuzuweisen.

Kurioserweise haben sich innerhalb unserer Galaxie römische Zahlen trotz ihrer bekannten Unzulänglichkeiten als das
Standard-Zahlsystem etabliert. Allerdings haben die römischen Zahlzeichen wie wir sie kennen überall unterschiedliche Namen.  
Bspw. könnte römisch `C` auf Pluto `urr` genannt werden und auf Neptun `grsin` heißen.  
Römisch `V` könnte auf Jupiter `glimb` und auf Proxima Centauri `drömdrl` heißen.

Um sich das alles nicht merken zu müssen, und um mit diesen Begriffen als römische Zahlen rechnen zu können,
wurde dieses Programm entwickelt. 

Die Anwendung wurde testgetrieben entwickelt, d.h. als erstes wurden die Tests geschrieben und anschließend wurde die Funktionaliät implementiert, sodass die Tests erfolgreich durchlaufen. Später folgten Refactorings.

Das Programm wurde so entwickelt, dass es komplett selbsterklärend sein sollte – sowohl
hinsichtlich des Codes als auch während der Benutzung über den Chat.  
Was der Chatbot leistet, wird im nächsten Abschnitt erläutert.

# Definitionen und Queries

Der Chatbot versteht insgesamt vier Formen von Nutzereingaben: Zwei Arten von Definitionen sowie zwei verschiedene Queries. Teile in `()` sind optional und können weggelassen werden.

1. Definitionen:

    1. Intergalaktische Begriffe (*alienTerm* oder *term*) für römische Zahlzeichen definiert man in der Form  
	`[term] is [Roman numeral]`
	
    2. Gütern können Werte in Form einer Anzahl Credits zugewiesen werden.  
	Dabei können diese Credits einer Anzahl dieses Guts zugewiesen werden, die als durch Leerzeichen getrennte Sequenz
       von intergalaktischen Begriffen angegeben wird.  
	Intern merkt sich der Converter immer die Anzahl Credits für 1 Einheit dieses Guts.  
	`([previously defined term1] [p. d. term2] [...]) [good] (is) [Arabic number of] (credit(s))`

2. Queries:

    1. Nummer-Konvertierungs-Query. Eine Sequenz von intergalaktischen Begriffen wird als römische Zahl interpretiert. Diese wird anschließend
       in eine arabische Zahl umgerechnet.  
	`How much is [p. d. term1] [term2] [...] (?)`

    2. Abfrage zuvor definierter Werte von Gütern. Das Ergebnis wird in Credits
       angegeben.  
	`How many Credits is ([p. d. term1] [term2] [...]) [good] (?)`
	

## Beispiele

glops is V  
pirf is C  
dim is X  
how much is pirf dim glops ?
> pirf dim glops is 115

pirf glops Iridium is 1365 Credits  
how many Credits is glops Iridium?
> glops Iridium is 65 Credits

how many Credits is Iridium?
> Iridium is 13 Credits

# Annahmen und Designentscheidungen

Für die Konvertierung zwischen römischen und arabischen Zahlen hatte ich zunächst eine externe Library verwendet, da in der Aufgabenstellung steht, dass man - wie bei echter Softwareentwicklung - Libraries nutzen darf. Gleichzeitig wird in der Aufgabe darum gebeten, nicht zu schummeln und eine eigene Lösung zu schreiben. Da ein Großteil der
Aufgabenstellung Details zu römischen Zahlen und deren Regeln beinhaltet, ist mir später
bewusst geworden, dass ich diese Funktionalität sicher selbst programmieren soll, was ich
dann auch getan habe.

Da in der Aufgabenstellung die Rede von “common metals and dirt” ist, habe ich mich
entschieden zu verallgemeinern, und so behandelt das Programm nun allgemein *goods*
(Güter/Waren).

Queries mit Zuweisungen von Credits zu Gütern werden intern umgerechnet in die Anzahl
Credits für 1 Einheit dieses Guts.

Ich habe 30 römische Zahlen definiert. Ich wäre auch mit nur 14 ausgekommen (I bis X; L,
C, D, M), hätte dann aber immer die Basiszahlen 10 und 5 skalieren müssen (I → X → C →
M; V → L → D). Bsp.: 90 = 9 x 10, also alle Zahlzeichen in 9 (I,X) um Faktor 10 hochskaliert:  
I x 10 = 10 = X; X x 10 = 100 = C. Somit ist IX x 10 = XC.  
Stattdessen habe ich mich für die Definition 16 zusätzlicher Zahlen entschieden. So können
alle möglichen Summanden einer römischen Zahl direkt nachgeschlagen werden.

# Projektstruktur

## Class RomanNumerals

Enthält Funktionalität für die Umrechnung zwischen arabischen und römischen Zahlen

## Class Converter

- Enthält den Chatbot bzw. die Benutzerschnittstelle des Umrechners
- Einstiegspunkt ist die `main` function. Von dort werden im Normalfall Queries des
  Benutzers mit `Converter.submitQuery()` empfangen und verarbeitet, und
  anschließend wird ggf. eine Response zurückgegeben und ausgegeben.
- Falls der Query die Definition eines Begriffs für ein römisches Zahlzeichen oder eine
  Definition des Wertes (Credits) einer Ware war, so wird keine Response erzeugt.

## Tests

Aufteilung der Tests für `RomanNumerals`:
- `RomanNumeralsTest_Requirements` testet die Regeln und Einschränkungen
  beim Format von und dem Rechnen mit römischen Zahlen, die in der
  Aufgabenstellung beschrieben sind.
- `RomanNumeralsTest_Methods` testet die Methoden, die ich dafür
  geschrieben habe
    - Der umfangreiche Test `testConversionBackForth()` konvertiert
      alle ganzen Zahlen, die als römische Zahl ausgedrückt werden
      können (1 bis 3999) in eine römische Zahl und anschließend zurück,
      was wieder die ursprüngliche Zahl ergeben muss.

`ConverterTest` enthält Tests für die 2 Frage-Queries. Für die zwei Definitions-Queries gibt es keinen direkten Test. Die Korrektheit der Definitionen ist durch die Tests der beiden Frage-Queries sichergestellt, deren erster Schritt die Definitions-Queries sind.

# Tools

Das Projekt wurde mit folgenden Tools entwickelt:
- IDE: IntelliJ IDEA 2023.1 (Community Edition)
- Build tool: Gradle Version 8.0
- Java JDK: Corretto 19.0.2

# Dependencies
- Guava für die BiMap / HashBiMap `RomanNumerals.Numbers`
- JUnit 5 / JUnit Jupiter für die Unit Tests

# Build-Anleitung

Mit Gradle bzw. dem Wrapper kann per  
`.\gradlew jar`  
eine ausführbare JAR erzeugt werden. Dies führt den in build.gradle angepassten Gradle Task `jar` aus.

Mit dem Gradle Wrapper können diverse Tasks ausgeführt werden. Eine Übersicht erhält man per  
`.\gradlew tasks`  

# Schwachstellen und Verbesserungspotenzial

- Der `Converter` rechnet immer entlang des Pfades
  _Arabische Zahlen ↔ römische Zahlen ↔ intergalaktische Begriffe_
  um
- Das ist umständlich / aufwendig. Wahrscheinlich lohnt es sich von einem geeigneten
  bestehenden Typ, z.B. `NumberFormat`, zu erben, um direkt mit römischen Zahlen
  rechnen zu können.
- Das sollte die Umrechnungen _Arabische Zahlen ↔ römische Zahlen_
  vereinfachen und die Wartbarkeit und Leserlichkeit des Codes erhöhen.
- Persistierung der definierten Zahl-Begriffe und Güter nach Programmende 
- Für die Verarbeitung der eingegebenen Queries habe ich versucht Regexes und
  Matching groups einzusetzen, hatte jedoch Schwierigkeiten mit den gematchten
  Groups und habe sie dann wieder entfernt. Die Eingabe-Strings werden nun ohne
  Regexes sequenziell durchgearbeitet und die interessanten Komponenten extrahiert.
- Regexes hätten den Vorteil, dass sie sehr kompakt sind und zwei Probleme auf
  einmal lösen: Sie würden sicherstellen, dass Query-Strings dem gewünschten
  Format entsprechen, und m.H.v. Matching Groups sollte es möglich sein, gleich alle
  Komponenten zu erhalten.  
- Einbau eines Query, der eine Liste der definierten Begriffe und der entsprechenden
  römischen Zahlzeichen ausgibt
- Einbau eines Query, der alle Güter und ihren Wert in Credits ausgibt
- Verbesserung der Lesbarkeit und Wartbarkeit durch Extraktion von Methoden aus
  längeren Methoden wie `Converter.generateNumberConversionQueryResponse()`,
  `.generateCreditsPerGoodsQueryResponse()`
