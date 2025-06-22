import java.awt.*;

public class ExpenseTrackerView {
    public Frame frame;
    public TextField descField, amountField;
    public TextArea tableArea;
    public Choice categoryChoice, sortChoice;
    public Button addButton, selectAllButton, deselectAllButton, removeSelectedButton, clearAllButton;
    public Label totalLabel;

    public ExpenseTrackerView(String username) {
        frame = new Frame("Fin Tracker - AWT (" + username + ")");
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        // Top
        Panel titlePanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        Label title = new Label("Fin Tracker - User: " + username);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titlePanel.add(title);

        Panel inputPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        descField = new TextField(15);
        amountField = new TextField(10);
        categoryChoice = new Choice();
        categoryChoice.add("Food");
        categoryChoice.add("Travel");
        categoryChoice.add("Shopping");
        categoryChoice.add("Other");
        addButton = new Button("Add");

        inputPanel.add(new Label("Description:"));
        inputPanel.add(descField);
        inputPanel.add(new Label("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new Label("Category:"));
        inputPanel.add(categoryChoice);
        inputPanel.add(addButton);

        Panel topPanel = new Panel(new BorderLayout());
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.SOUTH);
        frame.add(topPanel, BorderLayout.NORTH);

        // Center
        tableArea = new TextArea();
        tableArea.setEditable(false);
        tableArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        frame.add(tableArea, BorderLayout.CENTER);

        // Bottom
        Panel controlPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        selectAllButton = new Button("Select All");
        deselectAllButton = new Button("Deselect All");
        removeSelectedButton = new Button("Remove Selected");
        clearAllButton = new Button("Clear All");

        sortChoice = new Choice();
        sortChoice.add("Sort By...");
        sortChoice.add("Amount ↑");
        sortChoice.add("Amount ↓");
        sortChoice.add("Category A-Z");
        sortChoice.add("Category Z-A");
        sortChoice.add("Newest First");
        sortChoice.add("Oldest First");

        controlPanel.add(selectAllButton);
        controlPanel.add(deselectAllButton);
        controlPanel.add(removeSelectedButton);
        controlPanel.add(clearAllButton);
        controlPanel.add(sortChoice);

        totalLabel = new Label("Total: $0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        Panel totalPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.add(totalLabel);

        Panel bottomPanel = new Panel(new BorderLayout());
        bottomPanel.add(controlPanel, BorderLayout.CENTER);
        bottomPanel.add(totalPanel, BorderLayout.SOUTH);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }
}
