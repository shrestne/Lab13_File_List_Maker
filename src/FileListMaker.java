import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileListMaker {
    private static ArrayList<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFileName = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            displayMenu();
            choice = scanner.nextLine().toUpperCase();

            try {
                switch (choice) {
                    case "A" -> addItem(scanner);
                    case "D" -> deleteItem(scanner);
                    case "I" -> insertItem(scanner);
                    case "M" -> moveItem(scanner);
                    case "O" -> openFile(scanner);
                    case "S" -> saveFile(scanner);
                    case "C" -> clearList();
                    case "V" -> viewList();
                    case "Q" -> quitProgram(scanner);
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("File operation error: " + e.getMessage());
            }
        } while (!choice.equals("Q"));
    }

    private static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("A - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("I - Insert an item");
        System.out.println("M - Move an item");
        System.out.println("O - Open a list file");
        System.out.println("S - Save the current list");
        System.out.println("C - Clear the list");
        System.out.println("V - View the list");
        System.out.println("Q - Quit");
        System.out.print("Choose an option: ");
    }

    private static void addItem(Scanner scanner) {
        System.out.print("Enter item to add: ");
        String item = scanner.nextLine();
        list.add(item);
        needsToBeSaved = true;
    }

    private static void deleteItem(Scanner scanner) {
        viewList();
        System.out.print("Enter index to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (index >= 0 && index < list.size()) {
            list.remove(index);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void insertItem(Scanner scanner) {
        System.out.print("Enter item to insert: ");
        String item = scanner.nextLine();
        System.out.print("Enter index to insert at: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (index >= 0 && index <= list.size()) {
            list.add(index, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void moveItem(Scanner scanner) {
        viewList();
        System.out.print("Enter index of item to move: ");
        int fromIndex = scanner.nextInt();
        System.out.print("Enter new index to move to: ");
        int toIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (fromIndex >= 0 && fromIndex < list.size() && toIndex >= 0 && toIndex <= list.size()) {
            String item = list.remove(fromIndex);
            list.add(toIndex, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid indices.");
        }
    }

    private static void openFile(Scanner scanner) throws IOException {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Save before opening a new file? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                saveFile(scanner);
            }
        }
        System.out.print("Enter file name to open (without .txt): ");
        currentFileName = scanner.nextLine() + ".txt";
        list = new ArrayList<>(Files.readAllLines(Path.of(currentFileName)));
        needsToBeSaved = false;
        System.out.println("File loaded successfully.");
    }

    private static void saveFile(Scanner scanner) throws IOException {
        if (currentFileName.isEmpty()) {
            System.out.print("Enter file name to save as (without .txt): ");
            currentFileName = scanner.nextLine() + ".txt";
        }
        Files.write(Path.of(currentFileName), list);
        needsToBeSaved = false;
        System.out.println("File saved successfully.");
    }

    private static void clearList() {
        list.clear();
        needsToBeSaved = true;
        System.out.println("List cleared.");
    }

    private static void viewList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            System.out.println("Current List:");
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%d: %s%n", i, list.get(i));
            }
        }
    }

    private static void quitProgram(Scanner scanner) throws IOException {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Save before quitting? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                saveFile(scanner);
            }
        }
        System.out.println("Exiting the program. Goodbye!");
    }
}