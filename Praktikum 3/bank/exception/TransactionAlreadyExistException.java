package bank.exception;

/**
 * Wird geworfen, wenn eine Transaktion hinzugefügt werden soll, die es im
 * Konto schon gibt, oder wenn eine übergebene Liste Duplikate enthält.
 *
 * @author Chakour Bouraima
 * @see TransactionsException
 */
public final class TransactionAlreadyExistException extends TransactionsException {

    /**
     * Erzeugt die Exception mit Fehlermeldung.
     *
     * @param msg die Fehlermeldung
     */
    public TransactionAlreadyExistException(String msg) {
        super(msg);
    }

}
