package bank.exception;

/**
 * Wird geworfen, wenn die Prüfung der Attribute einer Transaktion fehlschlägt,
 * z. B. ein Zinssatz außerhalb von 0 bis 1 bei einer Payment oder ein Betrag
 * kleiner oder gleich 0 bei einem Transfer.
 *
 * @author Chakour Bouraima
 * @see TransactionsException
 * @see bank.Transaction#valid()
 */
public final class TransactionAttributeException extends TransactionsException {

    /**
     * Erzeugt die Exception mit Fehlermeldung.
     *
     * @param msg die Fehlermeldung
     */
    public TransactionAttributeException(String msg) {
        super(msg);
    }
}
