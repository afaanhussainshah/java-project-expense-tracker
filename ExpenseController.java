import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class ExpenseController {
    private final ExpenseModel model;
    private final ExpenseTrackerView view;

    public ExpenseController(String username) {
        model = new ExpenseModel(username);
        view = new ExpenseTrackerView(username);

        view.addButton.addActionListener(e -> addExpense());
        view.selectAllButton.addActionListener(e -> {
            model.getExpenses().forEach(exp -> exp.selected = true);
            updateTable();
        });
        view.deselectAllButton.addActionListener(e -> {
            model.getExpenses().forEach(exp -> exp.selected = false);
            updateTable();
        });
        view.removeSelectedButton.addActionListener(e -> {
            model.removeSelected();
            updateTable();
        });
        view.clearAllButton.addActionListener(e -> {
            model.clearAll();
            updateTable();
        });
        view.sortChoice.addItemListener(e -> sortExpenses());

        // Restore selection toggle on click
        view.tableArea.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                toggleSelectedFromClick();
            }
        });

        updateTable();
    }

    private void addExpense() {
        String desc = view.descField.getText().trim();
        String amtText = view.amountField.getText().trim();
        String category = view.categoryChoice.getSelectedItem();

        if (desc.isEmpty() || amtText.isEmpty()) return;
        try {
            double amount = Double.parseDouble(amtText);
            String id = "EXP-" + System.nanoTime();
            Expense exp = new Expense(id, desc, category, amount, LocalDateTime.now());
            model.addExpense(exp);
            view.descField.setText("");
            view.amountField.setText("");
            updateTable();
        } catch (NumberFormatException ex) {
            System.err.println("Invalid amount");
        }
    }

    private void updateTable() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-5s %-8s %-20s %-15s %-10s %-8s\n", "✔", "ID", "Description", "Category", "Amount", "Time"));
        sb.append("-------------------------------------------------------------------------------------\n");

        List<Expense> expenses = model.getExpenses();
        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            String checkbox = e.selected ? "[x]" : "[ ]";
            sb.append(String.format("%-5s %-8s %-20s %-15s $%8.2f %-8s\n",
                    checkbox,
                    e.id.substring(e.id.length() - 6),
                    truncate(e.description, 20),
                    e.category,
                    e.amount,
                    e.dateTime.toLocalTime().withNano(0)));
        }

        view.tableArea.setText(sb.toString());
        view.totalLabel.setText("Total: $" + String.format("%,.2f", model.getTotal()));
    }

    private String truncate(String s, int len) {
        return s.length() > len ? s.substring(0, len - 1) + "…" : s;
    }

    private void sortExpenses() {
        switch (view.sortChoice.getSelectedItem()) {
            case "Amount ↑":
                model.getExpenses().sort(Comparator.comparingDouble(e -> e.amount));
                break;
            case "Amount ↓":
                model.getExpenses().sort((a, b) -> Double.compare(b.amount, a.amount));
                break;
            case "Category A-Z":
                model.getExpenses().sort(Comparator.comparing(e -> e.category.toLowerCase()));
                break;
            case "Category Z-A":
                model.getExpenses().sort((a, b) -> b.category.compareToIgnoreCase(a.category));
                break;
            case "Newest First":
                model.getExpenses().sort((a, b) -> b.dateTime.compareTo(a.dateTime));
                break;
            case "Oldest First":
                model.getExpenses().sort(Comparator.comparing(e -> e.dateTime));
                break;
        }
        updateTable();
    }

    private void toggleSelectedFromClick() {
        int caret = view.tableArea.getCaretPosition();
        String[] lines = view.tableArea.getText().split("\n");
        int charCount = 0;
        int index = -1;

        for (int i = 0; i < lines.length; i++) {
            charCount += lines[i].length() + 1;
            if (charCount > caret) {
                index = i - 2; // offset for header
                break;
            }
        }

        if (index >= 0 && index < model.getExpenses().size()) {
            Expense e = model.getExpenses().get(index);
            e.selected = !e.selected;
            updateTable();
        }
    }
}
