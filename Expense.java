import java.time.LocalDateTime;

public class Expense {
    public String id, description, category;
    public double amount;
    public LocalDateTime dateTime;
    public boolean selected;

    public Expense(String id, String description, String category, double amount, LocalDateTime dateTime) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.dateTime = dateTime;
        this.selected = false;
    }

    public String toFileString() {
        return String.join(",", id, description, category, String.valueOf(amount), dateTime.toString());
    }

    public static Expense fromFileString(String line) {
        String[] parts = line.split(",", 5);
        if (parts.length == 5) {
            return new Expense(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), LocalDateTime.parse(parts[4]));
        }
        return null;
    }
}
