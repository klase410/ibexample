import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NoteManager noteManager = new NoteManager();

        while (true) {
            System.out.println("Enter a command (add, edit, delete, view, exit): ");
            String command = scanner.nextLine();

            switch (command) {
                case "add":
                    System.out.println("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.println("Enter content: ");
                    String content = scanner.nextLine();
                    noteManager.addNote(new Note(title, content));
                    break;
                case "edit":
                    System.out.println("Enter index: ");
                    int editIndex = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter title: ");
                    String editTitle = scanner.nextLine();
                    System.out.println("Enter content: ");
                    String editContent = scanner.nextLine();
                    noteManager.editNote(editIndex, new Note(editTitle, editContent));
                    break;
                case "delete":
                    System.out.println("Enter index: ");
                    int deleteIndex = Integer.parseInt(scanner.nextLine());
                    noteManager.deleteNote(deleteIndex);
                    break;
                case "view":
                    for (Note note : noteManager.getAllNotes()) {
                        System.out.println("Title: " + note.getTitle());
                        System.out.println("Content: " + note.getContent());
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
