package bank;

/**
 * Interface für alles, wo ein Endbetrag berechnet werden kann (z. B. nach Zinsen).
 * Umgesetzt wird es in {@link Transaction}, die konkrete Berechnung
 * steht in {@link Payment} und {@link Transfer}.
 *
 * @author Chakour Bouraima
 */
public interface CalculateBill {

    // Aus Sicht des Bankkontos, amount selbst wird dabei NICHT verändert
    /**
     * Berechnet den Endbetrag aus Sicht des Bankkontos.
     * Das Attribut {@code amount} wird dabei nicht verändert.
     *
     * @return der berechnete Betrag
     */
    double calculate();
}
