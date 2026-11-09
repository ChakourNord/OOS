import bank.Payment;
import bank.Transfer;

/**
 * Testet Payment und Transfer (Konstruktoren, calculate, toString, equals).
 */
public class Main {

    // Privater Konstruktor: Main ist nur der Einstiegspunkt, new Main() braucht keiner
    private Main() { }

    public static void main(String[] args) {
        // Payment: alle Konstruktoren, Ein- und Auszahlung
        Payment einzahlung = new Payment("26.10.2026", 1000.0, "Einzahlung", 0.05, 0.1);
        Payment auszahlung = new Payment("26.10.2026", -1000.0, "Auszahlung", 0.05, 0.1);
        Payment ohneZins = new Payment("26.10.2026", 500.0, "Ohne Zinsen");
        Payment kopie = new Payment(einzahlung);

        System.out.println(einzahlung);
        System.out.println(auszahlung);
        System.out.println(ohneZins);
        System.out.println(kopie);

        System.out.println("calculate Einzahlung (erwartet 950.0):   " + einzahlung.calculate());
        System.out.println("calculate Auszahlung (erwartet -1100.0): " + auszahlung.calculate());
        System.out.println("calculate ohne Zinsen (erwartet 500.0):  " + ohneZins.calculate());

        // Transfer: alle Konstruktoren
        Transfer t1 = new Transfer("26.10.2026", 50.0, "Miete", "Chakour", "Vermieter");
        Transfer t2 = new Transfer(t1);
        Transfer t3 = new Transfer("26.10.2026", 20.0, "Ohne Beteiligte");

        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
        System.out.println("calculate Transfer (erwartet 50.0): " + t1.calculate());

        // Fehlerfälle
        new Payment("26.10.2026", 100.0, "Fehlertest", 1.5, -0.2);
        new Transfer("26.10.2026", -30.0, "Fehlertest");

        // equals
        System.out.println("einzahlung.equals(kopie):       " + einzahlung.equals(kopie));
        System.out.println("einzahlung.equals(auszahlung):  " + einzahlung.equals(auszahlung));
        System.out.println("t1.equals(t2):                  " + t1.equals(t2));
        System.out.println("t1.equals(t3):                  " + t1.equals(t3));
        System.out.println("einzahlung.equals(t1):          " + einzahlung.equals(t1));
        System.out.println("einzahlung.equals(null):        " + einzahlung.equals(null));

        kopie.setAmount(-5.0);
        System.out.println("nach Änderung der Kopie:        " + einzahlung.equals(kopie));
    }
}
