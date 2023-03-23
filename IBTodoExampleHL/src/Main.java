import java.io.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Scanner;

import java.io.File;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private static Boolean exit = false;
    private static String command = "list";
    private static final String FILENAME = "todos.txt";

    public static void main(String[] args) throws IOException {

        List<Item> todoList = new ArrayList<Item>();

        // load data from file
        BufferedReader bf = new BufferedReader(new FileReader(FILENAME));

        // read entire line as string
        String line = bf.readLine();

        // https://www.w3docs.com/snippets/java/how-to-split-a-string-in-java.html
        String REGEX = ":";
        Pattern pattern = Pattern.compile(REGEX); // example data -- 1:0:geeks is geeks is geeks

        // checking for end of file
        while (line != null) {

            if (line.trim().isEmpty()) {
                line = bf.readLine();
                continue; // skip the line
            }

            String[] result = pattern.split(line);

            int id = Integer.parseInt(result[0]);
            int status = Integer.parseInt(result[1]);
            String title = result[2];

            Item test = new Item(id, status, title);
            todoList.add(test);

            line = bf.readLine();
        } // end while

        // closing bufferreader object
        bf.close();

        while (!exit) {

            if (command == "list") {

                for (Item item : todoList) {
                    System.out.println(item.getId() + " [" + item.getStatus() + "] " + item.getTitle());
                }
                System.out.println();
            } // end list

            if (command == "add") {
                System.out.println("Adding new item");

                // Enter data using BufferReader
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("New item: ");
                String title = input.readLine().trim();

                List<String> listOfStrings = new ArrayList<String>();

                // load the data from file
                listOfStrings = Files.readAllLines(Paths.get(FILENAME));
                int count1 = listOfStrings.size(); // make sure about the id

                String last = listOfStrings.get(count1 - 1); // get final element
                String[] result = pattern.split(last);

                int id = Integer.parseInt(result[0]); // first part of last line
                id = id + 1; // increment the index

                int status = 0;

                // add item to array in memory
                Item test = new Item(id, status, title);
                todoList.add(test);

                // add item to file
                FileWriter fw = new FileWriter(FILENAME, true); // true means append. not true is rewrite
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(id + ":" + status + ":" + title + System.getProperty("line.separator"));
                bw.close();

                System.out.println();
            } // end add

            if (command == "edit") {
                System.out.println("Editing");

                // Enter data using BufferReader
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Please enter id:");
                String id = input.readLine().trim();

                int intid = Integer.parseInt(id);

                if (intid >= 0) {
                    Item[] array = todoList.toArray(new Item[0]);
                    int len = array.length;

                    Item edit = null;

                    for (int i = 0; i < len; i++) {
                        if (array[i].getId() == intid) {
                            edit = array[i];
                        }
                    }

                    if (edit != null) {
                        System.out.println("TODO: " + edit.getStatus() + " : " + edit.getTitle());

                        System.out.print("Please enter new status (empty to skip):");
                        String status = input.readLine().trim();

                        System.out.print("Please enter new title (empty to skip):");
                        String title = input.readLine().trim();

                        if (!status.isEmpty()) {
                            edit.setStatus(Integer.parseInt(status));
                        }

                        if (!title.isEmpty()) {
                            edit.setTitle(title);
                        }
                    }

                    // TODO: Must update file with new information

                    File inputFile = new File(FILENAME);
                    File tempFile = new File("myTempFile.txt");

                    FileWriter fw = new FileWriter(tempFile, false); // true means append. not true is rewrite
                    BufferedWriter bw = new BufferedWriter(fw);

                    for (int i = 0; i < len; i++) {
                        edit = array[i];
                        bw.write(edit.getId() + ":" + edit.getStatus() + ":" + edit.getTitle() + System.getProperty("line.separator"));
                    }
                    bw.close();

                    inputFile.delete();
                    boolean successful = tempFile.renameTo(inputFile);
                    System.out.println(successful);
                }
            } // end edit

            // https://stackoverflow.com/questions/5800603/delete-specific-line-from-java-text-file
            if (command == "remove") {
                System.out.println("Removing element");

                // Enter data using BufferReader
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Please enter id:");
                String id = input.readLine().trim();

                int intid = Integer.parseInt(id);

                if (intid >= 0) {
                    Item[] array = todoList.toArray(new Item[0]);
                    int len = array.length;

                    Item edit = null;

                    int index = 0;
                    for (int i = 0; i < len; i++) {
                        if (array[i].getId() == intid) {
                            index = i;
                            edit = array[i];
                        }
                    }

                    if (edit != null) {
                        System.out.println("TODO: " + edit.getId() + " : " + edit.getStatus() + " : " + edit.getTitle());

                        // TODO: Must update file with new information

                        System.out.print("Are you sure? (yes/no): ");
                        String sure = input.readLine().trim();

                        if (sure.compareTo("yes") == 0) {
                            File inputFile = new File(FILENAME);
                            File tempFile = new File("myTempFile.txt");

                            FileWriter fw = new FileWriter(tempFile, false); // true means append. not true is rewrite
                            BufferedWriter bw = new BufferedWriter(fw);

                            for (int i = 0; i < len; i++) {
                                if (array[i].getId() != intid) { // Skip the special line
                                    edit = array[i];
                                    bw.write(edit.getId() + ":" + edit.getStatus() + ":" + edit.getTitle() + System.getProperty("line.separator"));
                                }
                            }
                            bw.close();

                            inputFile.delete();
                            boolean successful = tempFile.renameTo(inputFile);
                            System.out.println(successful);

                            // https://www.geeksforgeeks.org/remove-element-arraylist-java/
                            todoList.remove(index); // TODO: Must delete item from the todolist
                        }
                    }
                }
            } // end remove

            // Show the program menu

            System.out.println("Options: list | add | edit | remove | exit");
            System.out.print("What do you want to do next?: ");

            Scanner in = new Scanner(System.in); // Can be done using scanner
            String name = in.nextLine().trim();

            // Printing the read line

            if (name.compareTo("add") == 0) {
                command = "add";
            } else if (name.compareTo("list") == 0) {
                command = "list";
            } else if (name.compareTo("edit") == 0) {
                command = "edit";
            } else if (name.compareTo("remove") == 0) {
                command = "remove";
            } else if (name.compareTo("exit") == 0) {
                exit = true;
            } else if (name.isEmpty()) {
                command = "";
            }
        } // end while

        System.out.print("Bye.");

    }
}