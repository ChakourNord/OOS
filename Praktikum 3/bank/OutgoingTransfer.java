package bank;

/**
 * Sendung einer Überweisung. Der Betrag wird vom Konto abgezogen, deshalb
 * liefert {@link #calculate()} den negativen Betrag. Zusammen mit
 * {@link IncomingTransfer} ersetzt die Klasse die Unterscheidung per
 * {@code instanceof} bei der Berechnung des Kontostands.
 *
 * @author Chakour Bouraima
 * @see Transfer
 * @see IncomingTransfer
 */
public class OutgoingTransfer extends Transfer {

    /**
     * Erzeugt eine Sendung ohne Sender und Empfänger.
     *
     * @param date        Datum im Format DD.MM.YYYY
     * @param amount      Betrag (nur positiv)
     * @param description Beschreibung
     */
    public OutgoingTransfer(String date, double amount, String description) {
        super(date, amount, description);
    }

    /**
     * Erzeugt eine Sendung mit allen Attributen.
     *
     * @param date        Datum im Format DD.MM.YYYY
     * @param amount      Betrag (nur positiv)
     * @param description Beschreibung
     * @param sender      Absender
     * @param recipient   Empfänger
     */
    public OutgoingTransfer(String date, double amount, String description,
                            String sender, String recipient) {
        super(date, amount, description, sender, recipient);
    }

    /**
     * Copy-Konstruktor.
     *
     * @param other die Sendung, die kopiert wird
     */
    public OutgoingTransfer(OutgoingTransfer other) {
        super(other);
    }

    /**
     * Sendung: der Betrag wird negativ, weil er vom Konto abgezogen wird.
     *
     * @return der negative Betrag
     * @see Transfer#calculate()
     */
    @Override
    public double calculate() {
        return -super.calculate();
    }
}
