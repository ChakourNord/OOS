package bank.exception;

/**
 * Wird geworfen, wenn auf ein Konto zugegriffen wird, das nicht existiert.
 *
 * @author Chakour Bouraima
 * @see AccountsException
 */
public final class AccountDoesNotExistException extends AccountsException {

    /**
     * Erzeugt die Exception mit Fehlermeldung.
     *
     * @param msg die Fehlermeldung
     */
    public AccountDoesNotExistException(String msg) {
        super(msg);
    }

}

    