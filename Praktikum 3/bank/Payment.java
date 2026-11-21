package bank;

/**
 * Repräsentiert eine Ein- ({@code amount > 0}) oder Auszahlung ({@code amount < 0}).
 * Erbt date, amount und description von {@link Transaction} und ergänzt
 * die Zinssätze für Ein- und Auszahlungen.
 *
 * @author Chakour Bouraima
 * @see Transaction
 * @see Transfer
 */
public class Payment extends Transaction {

    // date, amount, description stehen jetzt in Transaction
    /** Zinssatz bei Einzahlungen, 0 bis 1. */
    private double incomingInterest;
    /** Zinssatz bei Auszahlungen, 0 bis 1. */
    private double outgoingInterest;

    // 1. Konstruktor: date, amount, description (Zinsen bleiben 0)
    /**
     * Erzeugt eine Payment ohne Zinsen.
     *
     * @param date        Datum im Format DD.MM.YYYY
     * @param amount      Betrag (positiv = Einzahlung, negativ = Auszahlung)
     * @param description Beschreibung
     */
    public Payment(String date, double amount, String description) {
        super(date, amount, description);
    }

    // 2. Konstruktor: alle Attribute, nutzt Konstruktor 1 wieder
    /**
     * Erzeugt eine Payment mit allen Attributen.
     *
     * @param date             Datum im Format DD.MM.YYYY
     * @param amount           Betrag (positiv = Einzahlung, negativ = Auszahlung)
     * @param description      Beschreibung
     * @param incomingInterest Zinssatz für Einzahlungen (0 bis 1)
     * @param outgoingInterest Zinssatz für Auszahlungen (0 bis 1)
     */
    public Payment(String date, double amount, String description,
                   double incomingInterest, double outgoingInterest) {
        this(date, amount, description);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    // 3. Copy-Konstruktor: super(other) kopiert die gemeinsamen Attribute
    /**
     * Copy-Konstruktor.
     *
     * @param other die Payment, die kopiert wird
     */
    public Payment(Payment other) {
        super(other);
        setIncomingInterest(other.getIncomingInterest());
        setOutgoingInterest(other.getOutgoingInterest());
    }

    // ---------- Getter ----------
    /**
     * Liefert den Zinssatz für Einzahlungen.
     *
     * @return der Zinssatz für Einzahlungen (0 bis 1)
     */
    public double getIncomingInterest() { return incomingInterest; }

    /**
     * Liefert den Zinssatz für Auszahlungen.
     *
     * @return der Zinssatz für Auszahlungen (0 bis 1)
     */
    public double getOutgoingInterest() { return outgoingInterest; }

    // ---------- Setter ----------
    // Wertebereich 0 bis 1, sonst Fehlermeldung und Wert bleibt unverändert
    /**
     * Setzt den Zinssatz für Einzahlungen. Werte außerhalb von 0 bis 1 werden
     * mit einer Fehlermeldung abgelehnt, der alte Wert bleibt dann erhalten.
     *
     * @param incomingInterest neuer Zinssatz (0 bis 1)
     */
    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest < 0 || incomingInterest > 1) {
            System.out.println("Fehler: incomingInterest muss zwischen 0 und 1 liegen (Eingabe: "
                    + incomingInterest + ")");
            return;
        }
        this.incomingInterest = incomingInterest;
    }

    /**
     * Setzt den Zinssatz für Auszahlungen. Werte außerhalb von 0 bis 1 werden
     * mit einer Fehlermeldung abgelehnt, der alte Wert bleibt dann erhalten.
     *
     * @param outgoingInterest neuer Zinssatz (0 bis 1)
     */
    public void setOutgoingInterest(double outgoingInterest) {
        if (outgoingInterest < 0 || outgoingInterest > 1) {
            System.out.println("Fehler: outgoingInterest muss zwischen 0 und 1 liegen (Eingabe: "
                    + outgoingInterest + ")");
            return;
        }
        this.outgoingInterest = outgoingInterest;
    }

    // ---------- Berechnung ----------
    // Einzahlung: Zins wird abgezogen     -> 1000 * (1 - 0.05) =  950
    // Auszahlung: Zins wird aufgeschlagen -> -1000 * (1 + 0.1) = -1100
    // Alles aus Sicht des Kontos, amount bleibt unverändert
    /**
     * Berechnet den Betrag nach Zinsen, aus Sicht des Kontos.
     * <ul>
     *   <li>Einzahlung: {@code amount * (1 - incomingInterest)}, z. B. 1000 und 0.05 ergibt 950</li>
     *   <li>Auszahlung: {@code amount * (1 + outgoingInterest)}, z. B. -1000 und 0.1 ergibt -1100</li>
     * </ul>
     *
     * @return der berechnete Betrag, {@code amount} bleibt unverändert
     */
    @Override
    public double calculate() {
        if (getAmount() > 0) {
            return getAmount() * (1 - incomingInterest);
        }
        return getAmount() * (1 + outgoingInterest);
    }

    // ---------- Ausgabe ----------
    // super.toString() liefert Datum, Betrag, Beschreibung -> hier nur das Neue dazu
    /**
     * Textdarstellung inklusive beider Zinssätze.
     *
     * @return String mit allen Attributen
     * @see Transaction#toString()
     */
    @Override
    public String toString() {
        return "Payment: " + super.toString()
                + ", incomingInterest: " + incomingInterest
                + ", outgoingInterest: " + outgoingInterest;
    }

    // ---------- Vergleich ----------
    // super.equals prüft schon Klasse + gemeinsame Attribute
    // -> danach ist der Cast auf Payment sicher
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
        Payment other = (Payment) obj;
        return Double.compare(incomingInterest, other.incomingInterest) == 0
                && Double.compare(outgoingInterest, other.outgoingInterest) == 0;
    }

    /**
     * Hashcode, passend zu {@link #equals(Object)}.
     *
     * @return der Hashcode
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), incomingInterest, outgoingInterest);
    }
}
