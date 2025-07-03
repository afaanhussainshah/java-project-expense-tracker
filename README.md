# Expense Tracker - Java Project

A robust Java-based expense tracking application featuring a Swing GUI, user authentication, and data persistence.

## Features
- **User Authentication:** Secure login system with user credentials stored in `users.txt`.
- **Expense Management:** Add, view, and manage daily expenses.
- **Data Persistence:** Expenses are saved to and loaded from text files (`expenses_Ash.txt`).
- **MVC Architecture:** Separation of concerns using Model-View-Controller design pattern.

## Technical Stack
- **Language:** Java
- **UI Framework:** Swing
- **Pattern:** MVC (Model-View-Controller)
- **Storage:** Flat file system (TXT)

## Project Structure
- `Expense.java`: Data model for an individual expense.
- `ExpenseModel.java`: Handles data logic and file I/O.
- `ExpenseTrackerView.java`: The graphical user interface.
- `ExpenseController.java`: Bridges the logic between Model and View.
- `LoginWindow.java`: Authentication screen.
- `Launcher.java`: Application entry point.
