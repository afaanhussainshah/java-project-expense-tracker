import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseModel {
    private final List<Expense> expenses = new ArrayList<>();
    private final String dataFile;

    public ExpenseModel(String username) {
        this.dataFile = "expenses_" + username + ".txt";
        loadExpenses();
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense e) {
        expenses.add(e);
        saveExpenses();
    }

    public void removeSelected() {
        expenses.removeIf(e -> e.selected);
        saveExpenses();
    }

    public void clearAll() {
        expenses.clear();
        saveExpenses();
    }

    public double getTotal() {
        return expenses.stream().mapToDouble(e -> e.amount).sum();
    }

    public void saveExpenses() {
        try (PrintWriter out = new PrintWriter(new FileWriter(dataFile))) {
            for (Expense e : expenses) out.println(e.toFileString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadExpenses() {
        expenses.clear();
        try {
            if (Files.exists(Paths.get(dataFile))) {
                List<String> lines = Files.readAllLines(Paths.get(dataFile));
                for (String line : lines) {
                    Expense e = Expense.fromFileString(line);
                    if (e != null) expenses.add(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
