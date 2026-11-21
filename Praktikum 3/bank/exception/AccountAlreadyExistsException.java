package bank.exception;

/**
 * Wird geworfen, wenn ein Konto angelegt werden soll, das schon existiert.
 *
 * @author Chakour Bouraima
 * @see AccountsException
 */
public final class AccountAlreadyExistsException extends AccountsException {

    /**
     * Erzeugt die Exception mit Fehlermeldung.
     *
     * @param msg die Fehlermeldung
     */
    public AccountAlreadyExistsException(String msg) {
        super(msg);
    }

}

    