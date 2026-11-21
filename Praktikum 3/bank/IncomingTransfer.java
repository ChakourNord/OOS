package bank;

/**
 * Empfang einer Überweisung. Der Betrag wird dem Konto gutgeschrieben, deshalb
 * liefert {@link #calculate()} den positiven Betrag. Zusammen mit
 * {@link OutgoingTransfer} ersetzt die Klasse die Unterscheidung per
 * {@code instanceof} bei der Berechnung des Kontostands.
 *
 * @author Chakour Bouraima
 * @see Transfer
 * @see OutgoingTransfer
 */
public class IncomingTransfer extends Transfer {

    /**
     * Erzeugt einen Empfang ohne Sender und Empfänger.
     *
     * @param date        Datum im Format DD.MM.YYYY
     * @param amount      Betrag (nur positiv)
     * @param description Beschreibung
     */
    public IncomingTransfer(String date, double amount, String description) {
        super(date, amount, description);
    }

    /**
     * Erzeugt einen Empfang mit allen Attributen.
     *
     * @param date        Datum im Format DD.MM.YYYY
     * @param amount      Betrag (nur positiv)
     * @param description Beschreibung
     * @param sender      Absender
     * @param recipient   Empfänger
     */
    public IncomingTransfer(String date, double amount, String description,
                            String sender, String recipient) {
        super(date, amount, description, sender, recipient);
    }

    /**
     * Copy-Konstruktor.
     *
     * @param other der Empfang, der kopiert wird
     */
    public IncomingTransfer(IncomingTransfer other) {
        super(other);
    }

    /**
     * Empfang: der Betrag bleibt positiv und wird dem Konto hinzugefügt.
     *
     * @return der positive Betrag
     * @see Transfer#calculate()
     */
    @Override
    public double calculate() {
        return super.calculate();
    }
}
