import java.util.List;

import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Payment;
import bank.PrivateBank;
import bank.PrivateBankAlt;
import bank.Bank;
import bank.Transaction;
import bank.Transfer;
import bank.exception.AccountAlreadyExistsException;
import bank.exception.AccountDoesNotExistException;
import bank.exception.TransactionAlreadyExistException;
import bank.exception.TransactionAttributeException;
import bank.exception.TransactionDoesNotExistException;

/**
 * Testet die Bank (PrivateBank und PrivateBankAlt): alle Methoden des Interfaces
 * Bank, die Fehlerfälle mit Exceptions und PrivateBank#equals.
 * Einstiegspunkt der Tests.
 */

/** Eine Aktion, die eine Exception werfen darf. */
interface Aktion {
    void run() throws Exception;
}

int bestanden = 0;
int fehlgeschlagen = 0;

void main() throws Exception {
    // Beide Banken mit Zinssätzen größer 0
    var bank = new PrivateBank("Sparkasse", 0.05, 0.1);
    var bankAlt = new PrivateBankAlt("Sparkasse Alt", 0.05, 0.1);

    IO.println("===== PrivateBank (Variante 1: Vererbung) =====");
    testeBank(bank, true);

    IO.println("\n===== PrivateBankAlt (Variante 2: instanceof) =====");
    testeBank(bankAlt, false);

    IO.println("\n===== Fehlerfälle PrivateBank =====");
    testeFehlerfaelle(new PrivateBank("Fehlerbank", 0.05, 0.1));

    IO.println("\n===== Fehlerfälle PrivateBankAlt =====");
    testeFehlerfaelle(new PrivateBankAlt("Fehlerbank", 0.05, 0.1));

    IO.println("\n===== PrivateBank#equals und Copy-Konstruktor =====");
    testeEquals();

    IO.println("\nBestanden: " + bestanden + ", fehlgeschlagen: " + fehlgeschlagen);
}

// ---------- Hilfsmethoden ----------

/** Prüft, ob erwartet und tatsächlich gleich sind, und zählt das Ergebnis. */
void pruefe(String beschreibung, Object erwartet, Object tatsaechlich) {
    boolean ok = java.util.Objects.equals(erwartet, tatsaechlich);
    if (ok) {
        bestanden++;
    } else {
        fehlgeschlagen++;
    }
    IO.println((ok ? "[OK]   " : "[FEHLER] ") + beschreibung
            + " (erwartet: " + erwartet + ", tatsächlich: " + tatsaechlich + ")");
}

/** Prüft, ob die Aktion genau die erwartete Exception wirft. */
void erwarteException(String beschreibung, Class<? extends Exception> erwartet, Aktion aktion) {
    try {
        aktion.run();
        fehlgeschlagen++;
        IO.println("[FEHLER] " + beschreibung + ": keine Exception, erwartet "
                + erwartet.getSimpleName());
    } catch (Exception e) {
        boolean ok = erwartet.isInstance(e);
        if (ok) {
            bestanden++;
        } else {
            fehlgeschlagen++;
        }
        IO.println((ok ? "[OK]   " : "[FEHLER] ") + beschreibung + ": "
                + e.getClass().getSimpleName() + " (\"" + e.getMessage() + "\")");
    }
}

/**
 * Erzeugt eine Überweisung von sender an recipient, je nach Variante als
 * Sendung/Empfang (Variante 1) oder als normaler Transfer (Variante 2).
 */
Transfer ueberweisung(boolean variante1, boolean ausSichtDesSenders, double betrag,
                      String sender, String recipient) {
    if (!variante1) {
        return new Transfer("01.11.2026", betrag, "Überweisung", sender, recipient);
    }
    return ausSichtDesSenders
            ? new OutgoingTransfer("01.11.2026", betrag, "Überweisung", sender, recipient)
            : new IncomingTransfer("01.11.2026", betrag, "Überweisung", sender, recipient);
}

// ---------- Tests der Interface-Methoden ----------

void testeBank(Bank bank, boolean variante1) throws Exception {
    bank.createAccount("Adam");
    bank.createAccount("Eva");

    // Payments: die Bank überschreibt die Zinsen mit 0.05 / 0.1
    var einzahlung = new Payment("01.11.2026", 1000.0, "Einzahlung");
    var auszahlung = new Payment("02.11.2026", -500.0, "Auszahlung");
    bank.addTransaction("Adam", einzahlung);
    bank.addTransaction("Adam", auszahlung);

    // Überweisung Adam -> Eva über 100
    var sendung = ueberweisung(variante1, true, 100.0, "Adam", "Eva");
    var empfang = ueberweisung(variante1, false, 100.0, "Adam", "Eva");
    bank.addTransaction("Adam", sendung);
    bank.addTransaction("Eva", empfang);

    pruefe("Zins der Einzahlung von der Bank übernommen", 0.05, einzahlung.getIncomingInterest());
    pruefe("Zins der Auszahlung von der Bank übernommen", 0.1, auszahlung.getOutgoingInterest());

    // 1000*0.95 = 950, -500*1.1 = -550, -100  ->  300
    pruefe("getAccountBalance Adam", 300.0, bank.getAccountBalance("Adam"));
    pruefe("getAccountBalance Eva", 100.0, bank.getAccountBalance("Eva"));

    pruefe("getTransactions Adam (Anzahl)", 3, bank.getTransactions("Adam").size());
    pruefe("getTransactions Eva (Anzahl)", 1, bank.getTransactions("Eva").size());

    List<Transaction> auf = bank.getTransactionsSorted("Adam", true);
    pruefe("getTransactionsSorted aufsteigend, kleinster Betrag", -550.0, auf.getFirst().calculate());
    pruefe("getTransactionsSorted aufsteigend, größter Betrag", 950.0, auf.getLast().calculate());
    List<Transaction> ab = bank.getTransactionsSorted("Adam", false);
    pruefe("getTransactionsSorted absteigend, größter Betrag", 950.0, ab.getFirst().calculate());
    pruefe("getTransactionsSorted absteigend, kleinster Betrag", -550.0, ab.getLast().calculate());

    // Variante 2: Die Sendung ist ein normaler Transfer, calculate() bleibt positiv.
    // Nur getAccountBalance unterscheidet dort nach dem Sender (so verlangt es die Aufgabe).
    pruefe("getTransactionsByType positiv (Anzahl)", variante1 ? 1 : 2,
            bank.getTransactionsByType("Adam", true).size());
    pruefe("getTransactionsByType negativ (Anzahl)", variante1 ? 2 : 1,
            bank.getTransactionsByType("Adam", false).size());

    pruefe("containsTransaction vorhanden", true, bank.containsTransaction("Adam", einzahlung));
    pruefe("containsTransaction nicht vorhanden", false, bank.containsTransaction("Eva", einzahlung));

    bank.removeTransaction("Adam", auszahlung);
    pruefe("nach removeTransaction nicht mehr enthalten", false, bank.containsTransaction("Adam", auszahlung));
    pruefe("Kontostand Adam nach removeTransaction", 850.0, bank.getAccountBalance("Adam"));

    // Konto mit Transaktionsliste anlegen
    var liste = List.<Transaction>of(
            new Payment("03.11.2026", 200.0, "Gehalt", 0.05, 0.1),
            new Payment("04.11.2026", -50.0, "Einkauf", 0.05, 0.1));
    bank.createAccount("Max", liste);
    pruefe("createAccount mit Liste (Anzahl)", 2, bank.getTransactions("Max").size());
    pruefe("Kontostand Max (200*0.95 + -50*1.1)", 135.0, bank.getAccountBalance("Max"));
}

// ---------- Fehlerfälle ----------

void testeFehlerfaelle(Bank bank) throws Exception {
    bank.createAccount("Adam");
    var payment = new Payment("01.11.2026", 100.0, "Test");
    bank.addTransaction("Adam", payment);

    erwarteException("createAccount: Konto existiert schon",
            AccountAlreadyExistsException.class, () -> bank.createAccount("Adam"));

    erwarteException("createAccount mit Liste: Konto existiert schon",
            AccountAlreadyExistsException.class, () -> bank.createAccount("Adam", List.of()));

    erwarteException("createAccount mit Liste: Duplikate in der Liste",
            TransactionAlreadyExistException.class, () -> bank.createAccount("Neu",
                    List.of(new Payment("01.11.2026", 10.0, "A"), new Payment("01.11.2026", 10.0, "A"))));

    // Transfer mit negativem Betrag: der Setter lehnt den Wert ab, amount bleibt 0 -> ungültig
    erwarteException("createAccount mit Liste: ungültige Transaktion",
            TransactionAttributeException.class, () -> bank.createAccount("Neu2",
                    List.of(new Transfer("01.11.2026", -30.0, "ungültig", "A", "B"))));

    pruefe("Konto 'Neu2' wurde bei einem Fehler nicht angelegt", 0, bank.getTransactions("Neu2").size());

    erwarteException("addTransaction: Konto existiert nicht",
            AccountDoesNotExistException.class, () -> bank.addTransaction("Unbekannt", payment));

    erwarteException("addTransaction: Transaktion existiert schon",
            TransactionAlreadyExistException.class, () -> bank.addTransaction("Adam", payment));

    erwarteException("addTransaction: ungültige Transaktion (Betrag <= 0)",
            TransactionAttributeException.class,
            () -> bank.addTransaction("Adam", new Transfer("01.11.2026", -30.0, "ungültig", "A", "B")));

    erwarteException("removeTransaction: Konto existiert nicht",
            AccountDoesNotExistException.class, () -> bank.removeTransaction("Unbekannt", payment));

    erwarteException("removeTransaction: Transaktion existiert nicht",
            TransactionDoesNotExistException.class,
            () -> bank.removeTransaction("Adam", new Payment("09.09.2026", 1.0, "gibt es nicht")));
}

// ---------- equals ----------

void testeEquals() throws Exception {
    var a = new PrivateBank("Sparkasse", 0.05, 0.1);
    var b = new PrivateBank("Sparkasse", 0.05, 0.1);
    pruefe("gleiche Attribute, keine Konten", true, a.equals(b));

    a.createAccount("Adam");
    pruefe("a hat ein Konto mehr", false, a.equals(b));

    b.createAccount("Adam");
    pruefe("beide haben das Konto Adam", true, a.equals(b));

    a.addTransaction("Adam", new Payment("01.11.2026", 100.0, "Test"));
    pruefe("a hat eine Transaktion mehr", false, a.equals(b));

    b.addTransaction("Adam", new Payment("01.11.2026", 100.0, "Test"));
    pruefe("beide haben dieselbe Transaktion", true, a.equals(b));

    pruefe("anderer Name", false, a.equals(new PrivateBank("Volksbank", 0.05, 0.1)));
    pruefe("anderer Zinssatz", false, new PrivateBank("S", 0.05, 0.1).equals(new PrivateBank("S", 0.06, 0.1)));
    pruefe("equals(null)", false, a.equals(null));
    pruefe("equals mit anderer Klasse", false, a.equals("Sparkasse"));

    var kopie = new PrivateBank(a);
    pruefe("Copy-Konstruktor: Name kopiert", a.getName(), kopie.getName());
    pruefe("Copy-Konstruktor: Konten werden nicht kopiert", false, a.equals(kopie));
}
