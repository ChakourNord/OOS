package bank.exception;

/**
 * Gemeinsame Oberklasse aller Exceptions der Bank. Die Klasse ist {@code sealed},
 * es gibt nur die beiden Zweige {@link AccountsException} (Konten) und
 * {@link TransactionsException} (Transaktionen).
 *
 * @author Chakour Bouraima
 * @see AccountsException
 * @see TransactionsException
 */
public abstract sealed class exceptions extends Exception
        permits AccountsException, TransactionsException {

    /**
     * Erzeugt eine Exception mit Fehlermeldung.
     *
     * @param msg die Fehlermeldung
     */
    protected exceptions(String msg) {
        super(msg);
    }

}
