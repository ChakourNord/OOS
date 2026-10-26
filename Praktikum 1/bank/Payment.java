package bank;

/**
 * Repräsentiert eine Ein- (amount > 0) oder Auszahlung (amount < 0).
 */
public class Payment {

    // private: Zugriff nur über Getter/Setter -> Validierung ist garantiert
    private String date;              // Format DD.MM.YYYY
    private double amount;
    private String description;
    private double incomingInterest;  // 0 bis 1
    private double outgoingInterest;  // 0 bis 1

    // 1. Konstruktor: date, amount, description
    public Payment(String date, double amount, String description) {
        setDate(date);
        setAmount(amount);
        setDescription(description);
    }

    // 2. Konstruktor: alle Attribute, nutzt Konstruktor 1 wieder
    public Payment(String date, double amount, String description,
                   double incomingInterest, double outgoingInterest) {
        this(date, amount, description);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    // 3. Copy-Konstruktor: neues Objekt mit denselben Werten wie 'other'
    public Payment(Payment other) {
        this(other.getDate(), other.getAmount(), other.getDescription(),
             other.getIncomingInterest(), other.getOutgoingInterest());
    }

    // ---------- Getter ----------
    public String getDate() { return date; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public double getIncomingInterest() { return incomingInterest; }
    public double getOutgoingInterest() { return outgoingInterest; }

    // ---------- Setter ----------
    public void setDate(String date) { this.date = date; }

    // Positiv (Einzahlung) und negativ (Auszahlung) erlaubt
    public void setAmount(double amount) { this.amount = amount; }

    public void setDescription(String description) { this.description = description; }

    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest < 0 || incomingInterest > 1) {
            System.out.println("Fehler: incomingInterest muss zwischen 0 und 1 liegen (Eingabe: "
                    + incomingInterest + ")");
            return;
        }
        this.incomingInterest = incomingInterest;
    }

    public void setOutgoingInterest(double outgoingInterest) {
        if (outgoingInterest < 0 || outgoingInterest > 1) {
            System.out.println("Fehler: outgoingInterest muss zwischen 0 und 1 liegen (Eingabe: "
                    + outgoingInterest + ")");
            return;
        }
        this.outgoingInterest = outgoingInterest;
    }

    // ---------- Ausgabe ----------
    public void printObject() {
        System.out.println("Payment:");
        System.out.println("  Datum:            " + getDate());
        System.out.println("  Betrag:           " + getAmount()
                + (getAmount() >= 0 ? " (Einzahlung)" : " (Auszahlung)"));
        System.out.println("  Beschreibung:     " + getDescription());
        System.out.println("  incomingInterest: " + getIncomingInterest());
        System.out.println("  outgoingInterest: " + getOutgoingInterest());
    }
}
