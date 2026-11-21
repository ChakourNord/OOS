package bank.exception;

/**
 * Wird geworfen, wenn eine Transaktion entfernt werden soll, die im Konto
 * nicht vorhanden ist.
 *
 * @author Chakour Bouraima
 * @see TransactionsException
 */
public final class TransactionDoesNotExistException extends TransactionsException {

    /**
     * Erzeugt die Exception mit Fehlermeldung.
     *
     * @param msg die Fehlermeldung
     */
    public TransactionDoesNotExistException(String msg) {
        super(msg);
    }

}
