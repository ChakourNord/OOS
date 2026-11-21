package bank;

public abstract sealed class exceptions extends Exception
        permits AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException,
        TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException,
        AccountDoesNotExistException, TransactionDoesNotExistException {

    protected exceptions(String msg) {
        super(msg);
    }

}
