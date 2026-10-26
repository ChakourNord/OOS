package bank;

/**
 * Repräsentiert eine Überweisung von sender an recipient.
 */
public class Transfer {

    private String date;        // Format DD.MM.YYYY
    private double amount;      // nur positive Werte
    private String description;
    private String sender;
    private String recipient;

    // 1. Konstruktor: date, amount, description
    public Transfer(String date, double amount, String description) {
        setDate(date);
        setAmount(amount);
        setDescription(description);
    }

    // 2. Konstruktor: alle Attribute, nutzt Konstruktor 1 wieder
    public Transfer(String date, double amount, String description,
                    String sender, String recipient) {
        this(date, amount, description);
        setSender(sender);
        setRecipient(recipient);
    }

    // 3. Copy-Konstruktor
    public Transfer(Transfer other) {
        this(other.getDate(), other.getAmount(), other.getDescription(),
             other.getSender(), other.getRecipient());
    }

    // ---------- Getter ----------
    public String getDate() { return date; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getSender() { return sender; }
    public String getRecipient() { return recipient; }

    // ---------- Setter ----------
    public void setDate(String date) { this.date = date; }

    public void setAmount(double amount) {
        if (amount <= 0) {
            System.out.println("Fehler: amount muss bei einem Transfer positiv sein (Eingabe: "
                    + amount + ")");
            return;
        }
        this.amount = amount;
    }

    public void setDescription(String description) { this.description = description; }
    public void setSender(String sender) { this.sender = sender; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    // ---------- Ausgabe ----------
    public void printObject() {
        System.out.println("Transfer:");
        System.out.println("  Datum:        " + getDate());
        System.out.println("  Betrag:       " + getAmount());
        System.out.println("  Beschreibung: " + getDescription());
        System.out.println("  Sender:       " + getSender());
        System.out.println("  Empfänger:    " + getRecipient());
    }
}
