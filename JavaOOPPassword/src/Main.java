import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PasswordManager passwordManager = new PasswordManager();

        while (true) {
            System.out.println("Enter a command (add, edit, delete, view, exit): ");
            String command = scanner.nextLine();

            switch (command) {
                case "add":
                    System.out.println("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    passwordManager.addPassword(new Password(name, username, password));
                    break;
                case "edit":
                    System.out.println("Enter index: ");
                    int editIndex = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter name: ");
                    String editName = scanner.nextLine();
                    System.out.println("Enter username: ");
                    String editUsername = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String editPassword = scanner.nextLine();
                    passwordManager.editPassword(editIndex, new Password(editName, editUsername, editPassword));
                    break;
                case "delete":
                    System.out.println("Enter index: ");
                    int deleteIndex = Integer.parseInt(scanner.nextLine());
                    passwordManager.deletePassword(deleteIndex);
                    break;
                case "view":
                    for (Password item : passwordManager.getAllPasswords()) {
                        System.out.println("Name: " + item.getName());
                        System.out.println("Username: " + item.getUsername());
                        System.out.println("Password: " + item.getPassword());
                        System.out.println();
                    }
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Invalid command. Try again.");
            }
        }
    }
}
