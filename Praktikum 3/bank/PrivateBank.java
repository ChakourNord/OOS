package bank;

import java.util.*;

import bank.exception.AccountAlreadyExistsException;
import bank.exception.AccountDoesNotExistException;
import bank.exception.TransactionAlreadyExistException;
import bank.exception.TransactionAttributeException;
import bank.exception.TransactionDoesNotExistException;

/**
 * Eine private Bank, die Konten mit ihren Transaktionen verwaltet.
 * Die Bank hat einen Namen und zwei Zinssätze, die bei {@link Payment}s
 * als Ein- und Auszahlungszins verwendet werden.
 * Der Kontostand wird in {@link #getAccountBalance(String)} über
 * {@link Transaction#calculate()} berechnet. {@link IncomingTransfer} und
 * {@link OutgoingTransfer} liefern dabei schon das richtige Vorzeichen.
 *
 * @author Chakour Bouraima
 * @see Bank
 * @see Transaction
 * @see PrivateBankAlt
 */
public class PrivateBank implements Bank {

    /** Name der Bank. */
    private String name;
    /** Zinssatz für Einzahlungen, 0 bis 1. */
    private double incomingInterest;
    /** Zinssatz für Auszahlungen, 0 bis 1. */
    private double outcomingInterest;

    // Wird direkt bei der Definition erzeugt, nicht im Konstruktor
    /** Ordnet jedem Konto (Name) die Liste seiner Transaktionen zu. */
    private Map<String, List<Transaction>> accountsToTransaction = new HashMap<>();

    // ---------- Konstruktoren ----------
    /**
     * Erzeugt eine Bank ohne Konten.
     *
     * @param name              Name der Bank
     * @param incomingInterest  Zinssatz für Einzahlungen (0 bis 1)
     * @param outcomingInterest Zinssatz für Auszahlungen (0 bis 1)
     */
    public PrivateBank(String name, double incomingInterest, double outcomingInterest) {
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutcomingInterest(outcomingInterest);
    }

    // Kopiert nur name und die beiden Zinssätze, die Map ist neu und leer
    /**
     * Copy-Konstruktor. Kopiert Name und beide Zinssätze, die Konten werden
     * nicht übernommen. Die neue Bank startet also ohne Konten.
     *
     * @param other die Bank, die kopiert wird
     */
    public PrivateBank(PrivateBank other) {
        this(other.getName(), other.getIncomingInterest(), other.getOutcomingInterest());
    }

    // ---------- Getter ----------
    /**
     * Liefert den Namen der Bank.
     *
     * @return der Name
     */
    public String getName() {
        return name;
    }

    /**
     * Liefert den Zinssatz für Einzahlungen.
     *
     * @return der Zinssatz für Einzahlungen
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     * Liefert den Zinssatz für Auszahlungen.
     *
     * @return der Zinssatz für Auszahlungen
     */
    public double getOutcomingInterest() {
        return outcomingInterest;
    }

    // ---------- Setter ----------
    /**
     * Setzt den Namen der Bank.
     *
     * @param name neuer Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setzt den Zinssatz für Einzahlungen.
     *
     * @param incomingInterest neuer Zinssatz (0 bis 1)
     */
    public void setIncomingInterest(double incomingInterest) {
        this.incomingInterest = incomingInterest;
    }

    /**
     * Setzt den Zinssatz für Auszahlungen.
     *
     * @param outcomingInterest neuer Zinssatz (0 bis 1)
     */
    public void setOutcomingInterest(double outcomingInterest) {
        this.outcomingInterest = outcomingInterest;
    }

    // ---------- Ausgabe ----------
    /**
     * Textdarstellung mit Name, Zinssätzen und allen Konten.
     *
     * @return String mit allen Attributen
     */
    @Override
    public String toString() {
        return "PrivateBank: name: " + name
                + ", incomingInterest: " + incomingInterest
                + ", outcomingInterest: " + outcomingInterest
                + ", accountsToTransaction: " + accountsToTransaction;
    }

    // ---------- Vergleich ----------
    /**
     * Vergleicht alle Attribute, auch die Konten mit ihren Transaktionen.
     *
     * @param obj das Objekt, mit dem verglichen wird
     * @return {@code true}, wenn alle Attribute gleich sind, sonst {@code false}
     * @see #hashCode()
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PrivateBank other = (PrivateBank) obj;
        return Objects.equals(name, other.name)
                && Double.compare(incomingInterest, other.incomingInterest) == 0
                && Double.compare(outcomingInterest, other.outcomingInterest) == 0
                && Objects.equals(accountsToTransaction, other.accountsToTransaction);
    }

    /**
     * Hashcode, passend zu {@link #equals(Object)}.
     *
     * @return der Hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, incomingInterest, outcomingInterest, accountsToTransaction);
    }

    // ---------- Methoden aus dem Interface Bank ----------
    /**
     * {@inheritDoc}
     *
     * @throws AccountAlreadyExistsException wenn das Konto schon existiert
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException {
        if (accountsToTransaction.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account " + account + " Existiert bereits");
        }
        accountsToTransaction.put(account, new ArrayList<>());
    }

    /**
     * {@inheritDoc}
     * Geprüft wird in dieser Reihenfolge: Konto vorhanden, keine Duplikate in der
     * Liste, alle Transaktionen gültig. Erst danach wird das Konto angelegt, damit
     * bei einem Fehler kein halb befülltes Konto zurückbleibt. Gespeichert wird eine
     * Kopie der übergebenen Liste.
     *
     * @throws AccountAlreadyExistsException    wenn das Konto schon existiert
     * @throws TransactionAlreadyExistException wenn die Liste Duplikate enthält
     * @throws TransactionAttributeException    wenn eine Transaktion ungültig ist
     * @see Transaction#valid()
     */
    @Override
    public void createAccount(String account, List<Transaction> transactions)
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException {
        if (accountsToTransaction.containsKey(account)) {
            throw new AccountAlreadyExistsException("Account " + account + " Existiert bereits");
        }
        if (new HashSet<>(transactions).size() != transactions.size()) {
            throw new TransactionAlreadyExistException("in deiner befinden sich doplikate");
        }
        for (Transaction t : transactions) {
            if (!t.valid()) {
                throw new TransactionAttributeException(t.toString() + "ist nicht gültig");
            }

        }
        accountsToTransaction.put(account, new ArrayList<>(transactions));

    }

    /**
     * {@inheritDoc}
     * Bei einer {@link Payment} werden vor den Prüfungen die Zinssätze der Bank
     * übernommen, die Zinssätze der übergebenen Payment werden also überschrieben.
     *
     * @throws TransactionAlreadyExistException wenn die Transaktion schon existiert
     * @throws AccountDoesNotExistException     wenn das Konto nicht existiert
     * @throws TransactionAttributeException    wenn die Transaktion ungültig ist
     * @see Transaction#valid()
     */
    @Override
    public void addTransaction(String account, Transaction transaction)
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
        List<Transaction> translist = accountsToTransaction.get(account);
        if (!accountsToTransaction.containsKey(account)) {
            throw new AccountDoesNotExistException("Account " + account + " Existiert nicht");
        }

        // Bei Payments gelten die Zinssätze der Bank
        if (transaction instanceof Payment payment) {
            payment.setIncomingInterest(incomingInterest);
            payment.setOutgoingInterest(outcomingInterest);
        }

        if (translist.contains(transaction)) {
            throw new TransactionAlreadyExistException("transaction ist bereits vorhanden");
        }

        if (!transaction.valid()) {
            throw new TransactionAttributeException(transaction.toString() + " ist nicht gültig");
        }

        accountsToTransaction.get(account).add(transaction);

    }

    /**
     * {@inheritDoc}
     *
     * @throws AccountDoesNotExistException     wenn das Konto nicht existiert
     * @throws TransactionDoesNotExistException wenn die Transaktion nicht im Konto ist
     */
    @Override
    public void removeTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionDoesNotExistException {
        List<Transaction> translist = accountsToTransaction.get(account);
        if (!accountsToTransaction.containsKey(account)) {
            throw new AccountDoesNotExistException("Account " + account + " Existiert nicht");
        }
        if (!translist.contains(transaction)) {
            throw new TransactionDoesNotExistException("transaction existiert nicht");
        }

        accountsToTransaction.get(account).remove(transaction);

    }

    /**
     * {@inheritDoc}
     * Bei einem unbekannten Konto ist das Ergebnis {@code false}.
     */
    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        return getTransactions(account).contains(transaction);
    }

    // Variante 1: Incoming-/OutgoingTransfer liefern über calculate() schon das
    // richtige Vorzeichen, deshalb reicht hier eine einfache Summe
    /**
     * {@inheritDoc}
     * Summiert die berechneten Beträge aller Transaktionen des Kontos. Das
     * Vorzeichen von Überweisungen kommt aus {@link IncomingTransfer} (Empfang,
     * positiv) und {@link OutgoingTransfer} (Sendung, negativ), es ist keine
     * Unterscheidung mit {@code instanceof} nötig.
     *
     * @see Transaction#calculate()
     */
    @Override
    public double getAccountBalance(String account) {
        double balance = 0;
        for (Transaction t : getTransactions(account)) {
            balance += t.calculate();
        }
        return balance;
    }

    /**
     * {@inheritDoc}
     * Es wird eine Kopie der Liste zurückgegeben, damit die interne Liste von
     * außen nicht verändert werden kann. Bei einem unbekannten Konto ist die
     * Liste leer.
     */
    @Override
    public List<Transaction> getTransactions(String account) {
        List<Transaction> translist = accountsToTransaction.get(account);
        if (translist == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(translist);
    }

    /**
     * {@inheritDoc}
     * Sortiert wird nach dem berechneten Betrag ({@link Transaction#calculate()}),
     * die interne Liste bleibt unverändert.
     */
    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        List<Transaction> sorted = getTransactions(account);
        Comparator<Transaction> byAmount = Comparator.comparingDouble(Transaction::calculate);
        sorted.sort(asc ? byAmount : byAmount.reversed());
        return sorted;
    }

    /**
     * {@inheritDoc}
     * Maßgeblich ist das Vorzeichen des berechneten Betrags
     * ({@link Transaction#calculate()}). Ein Betrag von 0 zählt als positiv.
     */
    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : getTransactions(account)) {
            if (positive == (t.calculate() >= 0)) {
                result.add(t);
            }
        }
        return result;
    }

}
