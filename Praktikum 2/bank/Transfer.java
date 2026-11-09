package bank;

/**
 * Repräsentiert eine Überweisung von {@code sender} an {@code recipient}.
 * Erlaubt sind nur positive Beträge, Zinsen fallen nicht an.
 *
 * @author Chakour Bouraima
 * @see Transaction
 * @see Payment
 */
public class Transfer extends Transaction {

    // date, amount, description stehen jetzt in Transaction
    /** Absender der Überweisung. */
    private String sender;
    /** Empfänger der Überweisung. */
    private String recipient;

    // 1. Konstruktor: date, amount, description (sender/recipient bleiben null)
    /**
     * Erzeugt einen Transfer ohne Sender und Empfänger.
     *
     * @param date        Datum im Format DD.MM.YYYY
     * @param amount      Betrag (nur positiv)
     * @param description Beschreibung
     */
    public Transfer(String date, double amount, String description) {
        super(date, amount, description);
    }

    // 2. Konstruktor: alle Attribute, nutzt Konstruktor 1 wieder
    /**
     * Erzeugt einen Transfer mit allen Attributen.
     *
     * @param date        Datum im Format DD.MM.YYYY
     * @param amount      Betrag (nur positiv)
     * @param description Beschreibung
     * @param sender      Absender
     * @param recipient   Empfänger
     */
    public Transfer(String date, double amount, String description,
                    String sender, String recipient) {
        this(date, amount, description);
        setSender(sender);
        setRecipient(recipient);
    }

    // 3. Copy-Konstruktor: super(other) kopiert die gemeinsamen Attribute
    /**
     * Copy-Konstruktor.
     *
     * @param other der Transfer, der kopiert wird
     */
    public Transfer(Transfer other) {
        super(other);
        setSender(other.getSender());
        setRecipient(other.getRecipient());
    }

    // ---------- Getter ----------
    /**
     * Liefert den Absender.
     *
     * @return der Absender
     */
    public String getSender() { return sender; }

    /**
     * Liefert den Empfänger.
     *
     * @return der Empfänger
     */
    public String getRecipient() { return recipient; }

    // ---------- Setter ----------
    /**
     * Setzt den Absender.
     *
     * @param sender neuer Absender
     */
    public void setSender(String sender) { this.sender = sender; }

    /**
     * Setzt den Empfänger.
     *
     * @param recipient neuer Empfänger
     */
    public void setRecipient(String recipient) { this.recipient = recipient; }

    // Nur positive Werte erlaubt, sonst Fehlermeldung und Wert bleibt unverändert
    // super.setAmount, weil amount in Transaction private ist
    // (ohne super. würde sich die Methode selbst aufrufen -> Endlosschleife)
    // Wirkt auch im Konstruktor, weil der setAmount() aufruft
    /**
     * Setzt den Betrag. Nur Werte größer 0 sind erlaubt, sonst gibt es eine
     * Fehlermeldung und der alte Wert bleibt erhalten.
     *
     * @param amount neuer Betrag (muss {@code > 0} sein)
     * @see Transaction#setAmount(double)
     */
    @Override
    public void setAmount(double amount) {
        if (amount <= 0) {
            System.out.println("Fehler: amount muss bei einem Transfer positiv sein (Eingabe: "
                    + amount + ")");
            return;
        }
        super.setAmount(amount);
    }

    // ---------- Berechnung ----------
    // Bei Überweisungen fallen keine Zinsen an -> Betrag unverändert
    /**
     * Bei Überweisungen fallen keine Zinsen an.
     *
     * @return der unveränderte Betrag
     */
    @Override
    public double calculate() {
        return getAmount();
    }

    // ---------- Ausgabe ----------
    // super.toString() liefert Datum, Betrag, Beschreibung -> hier nur das Neue dazu
    /**
     * Textdarstellung inklusive Sender und Empfänger.
     *
     * @return String mit allen Attributen
     * @see Transaction#toString()
     */
    @Override
    public String toString() {
        return "Transfer: " + super.toString()
                + ", Sender: " + sender
                + ", Empfänger: " + recipient;
    }

    // ---------- Vergleich ----------
    // super.equals prüft schon Klasse + gemeinsame Attribute
    // -> danach ist der Cast auf Transfer sicher
    // Objects.equals, weil sender/recipient null sein können
    /**
     * Vergleicht alle Attribute.
     *
     * @param obj das Objekt, mit dem verglichen wird
     * @return {@code true}, wenn alle Attribute gleich sind, sonst {@code false}
     * @see Transaction#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Transfer other = (Transfer) obj;
        return java.util.Objects.equals(sender, other.sender)
                && java.util.Objects.equals(recipient, other.recipient);
    }

    /**
     * Hashcode, passend zu {@link #equals(Object)}.
     *
     * @return der Hashcode
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), sender, recipient);
    }
}
