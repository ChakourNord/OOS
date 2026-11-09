import bank.Payment;
import bank.Transfer;

/**
 * Testet Payment und Transfer (Konstruktoren, calculate, toString, equals).
 * Einstiegspunkt der Tests.
 */
void main() {
    // Payment: alle Konstruktoren, Ein- und Auszahlung
    var einzahlung = new Payment("26.10.2026", 1000.0, "Einzahlung", 0.05, 0.1);
    var auszahlung = new Payment("26.10.2026", -1000.0, "Auszahlung", 0.05, 0.1);
    var ohneZins = new Payment("26.10.2026", 500.0, "Ohne Zinsen");
    var kopie = new Payment(einzahlung);

    IO.println(einzahlung);
    IO.println(auszahlung);
    IO.println(ohneZins);
    IO.println(kopie);

    IO.println("calculate Einzahlung (erwartet 950.0):   " + einzahlung.calculate());
    IO.println("calculate Auszahlung (erwartet -1100.0): " + auszahlung.calculate());
    IO.println("calculate ohne Zinsen (erwartet 500.0):  " + ohneZins.calculate());

    // Transfer: alle Konstruktoren
    var t1 = new Transfer("26.10.2026", 50.0, "Miete", "Chakour", "Vermieter");
    var t2 = new Transfer(t1);
    var t3 = new Transfer("26.10.2026", 20.0, "Ohne Beteiligte");

    IO.println(t1);
    IO.println(t2);
    IO.println(t3);
    IO.println("calculate Transfer (erwartet 50.0): " + t1.calculate());

    // Fehlerfälle
    new Payment("26.10.2026", 100.0, "Fehlertest", 1.5, -0.2);
    new Transfer("26.10.2026", -30.0, "Fehlertest");

    // equals
    IO.println("einzahlung.equals(kopie):       " + einzahlung.equals(kopie));
    IO.println("einzahlung.equals(auszahlung):  " + einzahlung.equals(auszahlung));
    IO.println("t1.equals(t2):                  " + t1.equals(t2));
    IO.println("t1.equals(t3):                  " + t1.equals(t3));
    IO.println("einzahlung.equals(t1):          " + einzahlung.equals(t1));
    IO.println("einzahlung.equals(null):        " + einzahlung.equals(null));

    kopie.setAmount(-5.0);
    IO.println("nach Änderung der Kopie:        " + einzahlung.equals(kopie));
    IO.println(t1.hashCode());
}
