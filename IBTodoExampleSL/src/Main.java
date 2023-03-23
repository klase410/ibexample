import java.util.Scanner;

// To do app example, SL option
// Use as a reference
// Must bring the code of your own

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        int menu = 0;

        // items storage array
        String[] items = {"Test item", "", "", "", "", "", "", "", "", ""};

        while (menu != 999) {
            System.out.println("Please select an option:");
            System.out.println("1 - list");
            System.out.println("2 - insert");
            System.out.println("3 - update");
            System.out.println("4 - remove");
            System.out.println("999 - exit");

            Scanner in = new Scanner(System.in);
            menu = in.nextInt();

            // example "menu" code using it then else statements
//            if(menu == 1) {
//                // listing item
//            } else if (menu == 2) {
//                // insert an item
//            } else {
//                // exit
//            }

            // example "menu" code using switch case statements
            switch(menu) {
                case 1: {
                    System.out.println("Listings items");

                    // 10 - length of items array, defined at the top
                    for(int i = 0; i < 10; i++) {
                        System.out.println((i + 1) + " " + "Task: " + items[i]);
                    }

                    break;
                }
                case 2: {
                    System.out.println("Insert item");

                    // select proper item place in the list to insert -- find empty value index
                    int index = -1;

                    for(int i = 0; i < 10; i++) {
                        if(items[i].compareTo("") == 0) {
                            index = i;
                            break; // check no more
                        }
                    }

                    if(index < 0) {
                        System.out.println("There is no space left in the list");
                    } else {
                        // read smth from console
                        // prepare scanner
                        System.out.println("Please enter new value");
                        Scanner sc_title = new Scanner(System.in);
                        String new_title = sc_title.nextLine();

                        // update the master array
                        items[index] = new_title;

                        // say it's done
                        System.out.println("Done.");
                    }

                    break;
                }
                case 3: {
                    System.out.println("Update");

                    // select proper item from the list to edit
                    // prepare scanner x 2
                    System.out.println("Please enter item # to update"); // fix 1
                    Scanner sc_item = new Scanner(System.in);
                    int index = sc_item.nextInt();

                    // read smth from console
                    System.out.println("Please enter new value"); // fix 2
                    Scanner sc_title = new Scanner(System.in);
                    String new_title = sc_title.nextLine();

                    // update the copy value
                    String item_to_update = new_title.trim();

                    // update the master array
                    items[index-1] = item_to_update;

                    // say it's done
                    System.out.println("Done.");

                    break;
                }
                case 4: {
                    System.out.println("Delete");

                    // select proper item from the list to edit
                    // prepare scanner x 2
                    System.out.println("Please enter item # to delete"); // fix 1
                    Scanner sc_item = new Scanner(System.in);
                    int index = sc_item.nextInt();

                    // update the master array
                    items[index-1] = "";

                    // say it's done
                    System.out.println("Done.");

                    break;
                }
                case 999: {
                    System.out.println("Bye");
                    break;
                }
                default: {
                    System.out.println("Wrong option");
                }
            }
        } // end while
    }
}