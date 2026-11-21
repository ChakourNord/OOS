package bank.exception;

/**
 * Oberklasse für alle Fehler, die eine Transaktion betreffen.
 *
 * @author Chakour Bouraima
 * @see TransactionAlreadyExistException
 * @see TransactionAttributeException
 * @see TransactionDoesNotExistException
 */
public abstract sealed class TransactionsException extends exceptions
        permits TransactionAlreadyExistException, TransactionAttributeException, TransactionDoesNotExistException {

    /**
     * Erzeugt eine Exception mit Fehlermeldung.
     *
     * @param msg die Fehlermeldung
     */
    public TransactionsException(String msg) {
        super(msg);
    }
}
