import bank.Payment;
import bank.Transfer;

public class Main {
    public static void main(String[] args) {
        // Payment testen
        Payment p1 = new Payment("26.10.2026", 500.0, "Gehalt", 0.05, 0.1);
        p1.printObject();

        Payment p2 = new Payment(p1);      // Copy-Konstruktor
        p2.setAmount(-200.0);              // Kopie ändern -> p1 bleibt unverändert
        p2.printObject();

        new Payment("26.10.2026", 100.0, "Fehlertest", 1.5, -0.2); // 2 Fehlermeldungen

        // Transfer testen
        Transfer t1 = new Transfer("26.10.2026", 50.0, "Miete", "Chakour", "Vermieter");
        t1.printObject();

        Transfer t2 = new Transfer(t1);
        t2.printObject();

        new Transfer("26.10.2026", -30.0, "Fehlertest"); // Fehlermeldung
    }
}
