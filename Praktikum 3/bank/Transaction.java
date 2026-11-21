package bank;

/**
 * Abstrakte Oberklasse für {@link Payment} und {@link Transfer}.
 * Enthält alles, was beide gemeinsam haben (date, amount, description).
 * Die Berechnung des Endbetrags ({@link #calculate()}) kommt aus
 * {@link CalculateBill} und wird von den Unterklassen umgesetzt.
 *
 * @author Chakour Bouraima
 * @see Payment
 * @see Transfer
 * @see CalculateBill
 */
public abstract class Transaction implements CalculateBill {

    // private: Zugriff nur über Getter/Setter -> Validierung ist garantiert
    /** Datum im Format DD.MM.YYYY. */
    private String date;
    /** Betrag, unberechnet (ohne Zinsen). */
    private double amount;
    /** Beschreibung des Vorgangs. */
    private String description;

    // 1. Konstruktor: date, amount, description
    // Setter statt direkter Zuweisung -> Unterklassen können die Prüfung überschreiben
    /**
     * Erzeugt eine Transaction. Die Werte werden über die Setter gesetzt,
     * damit Prüfungen der Unterklassen (z. B. {@link Transfer#setAmount(double)}) greifen.
     *
     * @param date        Datum im Format DD.MM.YYYY
     * @param amount      Betrag
     * @param description Beschreibung
     */
    public Transaction(String date, double amount, String description) {
        setDate(date);
        setAmount(amount);
        setDescription(description);
    }

    // 2. Copy-Konstruktor: neues Objekt mit denselben Werten wie 'other'
    /**
     * Copy-Konstruktor: übernimmt alle Werte von {@code other}.
     *
     * @param other die Transaction, die kopiert wird
     */
    public Transaction(Transaction other) {
        this(other.getDate(), other.getAmount(), other.getDescription());
    }

    // ---------- Getter ----------
    /**
     * Liefert das Datum.
     *
     * @return das Datum im Format DD.MM.YYYY
     */
    public String getDate() { return date; }

    /**
     * Liefert den Betrag ohne Zinsen. Der berechnete Wert steht in {@link #calculate()}.
     *
     * @return der unberechnete Betrag
     */
    public double getAmount() { return amount; }

    /**
     * Liefert die Beschreibung.
     *
     * @return die Beschreibung
     */
    public String getDescription() { return description; }

    // ---------- Setter ----------
    /**
     * Setzt das Datum.
     *
     * @param date neues Datum im Format DD.MM.YYYY
     */
    public void setDate(String date) { this.date = date; }

    // Hier alles erlaubt, Transfer überschreibt das und lässt nur positive Werte zu
    /**
     * Setzt den Betrag. Unterklassen dürfen den Wertebereich einschränken.
     *
     * @param amount neuer Betrag
     * @see Transfer#setAmount(double)
     */
    public void setAmount(double amount) { this.amount = amount; }

    /**
     * Setzt die Beschreibung.
     *
     * @param description neue Beschreibung
     */
    public void setDescription(String description) { this.description = description; }

    // ---------- Ausgabe ----------
    // Als Betrag kommt calculate() raus (berechneter Wert), nicht das rohe amount
    // Payment und Transfer hängen ihre eigenen Attribute mit super.toString() hinten dran
    /**
     * Textdarstellung der gemeinsamen Attribute. Als Betrag wird der Wert
     * aus {@link #calculate()} ausgegeben, nicht {@link #getAmount()}.
     *
     * @return Datum, berechneter Betrag und Beschreibung als String
     */
    @Override
    public String toString() {
        return "Datum: " + date
                + ", Betrag: " + calculate()
                + ", Beschreibung: " + description;
    }

    // ---------- Vergleich ----------
    // getClass() statt instanceof -> Payment und Transfer sind nie gleich
    // Double.compare statt == bei double, Objects.equals wegen möglichem null
    /**
     * Vergleicht Klasse und alle Attribute. Objekte verschiedener Klassen
     * (z. B. Payment und Transfer) sind nie gleich.
     *
     * @param obj das Objekt, mit dem verglichen wird
     * @return {@code true}, wenn Klasse und alle Attribute gleich sind, sonst {@code false}
     * @see #hashCode()
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Transaction other = (Transaction) obj;
        return java.util.Objects.equals(date, other.date)
                && Double.compare(amount, other.amount) == 0
                && java.util.Objects.equals(description, other.description);
    }

    // Muss zu equals passen: gleiche Objekte -> gleicher Hashcode
    /**
     * Hashcode, passend zu {@link #equals(Object)}.
     *
     * @return der Hashcode
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(date, amount, description);
    }
}
