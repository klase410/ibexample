import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContactsManager contactsManager = new ContactsManager();

        while (true) {
            System.out.println("Enter a command (add, edit, delete, view, exit): ");
            String command = scanner.nextLine();

            switch (command) {
                case "add":
                    System.out.println("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.println("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.println("Enter email: ");
                    String email = scanner.nextLine();
                    contactsManager.addContact(new Contact(name, phoneNumber, email));
                    break;
                case "edit":
                    System.out.println("Enter index: ");
                    int editIndex = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter name: ");
                    String editName = scanner.nextLine();
                    System.out.println("Enter phone number: ");
                    String editPhoneNumber = scanner.nextLine();
                    System.out.println("Enter email: ");
                    String editEmail = scanner.nextLine();
                    contactsManager.editContact(editIndex, new Contact(editName, editPhoneNumber, editEmail));
                    break;
                case "delete":
                    System.out.println("Enter index: ");
                    int deleteIndex = Integer.parseInt(scanner.nextLine());
                    contactsManager.deleteContact(deleteIndex);
                    break;
                case "view":
                    for (Contact contact : contactsManager.getAllContacts()) {
                        System.out.println("Name: " + contact.getName());
                        System.out.println("Phone number: " + contact.getPhoneNumber());
                        System.out.println("Email: " + contact.getEmail());
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
