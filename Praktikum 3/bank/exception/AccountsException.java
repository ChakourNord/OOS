package bank.exception;

/**
 * Oberklasse für alle Fehler, die ein Konto betreffen.
 *
 * @author Chakour Bouraima
 * @see AccountAlreadyExistsException
 * @see AccountDoesNotExistException
 */
public abstract sealed class AccountsException extends exceptions
        permits AccountAlreadyExistsException, AccountDoesNotExistException {

    /**
     * Erzeugt eine Exception mit Fehlermeldung.
     *
     * @param msg die Fehlermeldung
     */
    protected AccountsException(String msg) {
        super(msg);
    }

}
