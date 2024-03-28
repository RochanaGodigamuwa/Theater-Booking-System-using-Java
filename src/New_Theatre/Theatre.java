package New_Theatre;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Theatre {
    static int seat;
    static int Choice;
    static int[][] row = new int[3][];
    static ArrayList<Ticket> tickets = new ArrayList<>();

    static   Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        row[0] = new int[12];  //w3schools
        row[1] = new int[16];
        row[2] = new int[20];
        Arrays.fill(row[0], 0);
        Arrays.fill(row[0], 0);
        Arrays.fill(row[0], 0);
        System.out.println("Welcome to the New Theatre");


        System.out.println("-".repeat(120));
        System.out.println("Please select an option:");
        System.out.println("1) Buy a ticket");
        System.out.println("2) Print seating area");
        System.out.println("3) Cancel ticket  ");
        System.out.println("4) List available seats");
        System.out.println("5) Save to file");
        System.out.println("6) Load file");
        System.out.println("7) Print ticket information and total price");
        System.out.println("8) Sort tickets by price");
        System.out.println("0) Quit");
        System.out.println("-".repeat(120));
        selection();


    }
    public static void selection(){ //Option selection of the program
        while (true){
        System.out.print("Enter Option: ");
        while (!input.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number between 0 and 8.");
            input.next();
            System.out.print("Enter option : ");
        }
        Choice = input.nextInt();
        if (Choice < 0 || Choice > 8) {
            System.out.println("Invalid input. Please enter a number between 0 and 8.");}
        else
        {
            break;}

    }
        switch(Choice){
            case 1:
                buy_ticket(row);
                break;
            case 2:
                Seating_areas(row);
                break;
            case 3:
                cancel_ticket(row) ;
                break;
            case 4:
                show_available(row);
                break;
            case 5:
                save(row);
                break;
            case 6:
                load(row);
                break;
            case 7:
                show_tickets_info();
                break;
            case 8:
                List<Ticket> sortedTickets = sort_tickets(tickets);
                for (Ticket ticket : sortedTickets) {
                    ticket.print();
                    selection();}
            case 0:
                System.exit(0);


        }
    }


    public static int rowSeatValidation(String message, int lowerValue,int upperValue){

        while(true){
            try{
                System.out.print(message);
                int userInput = input.nextInt();
                if(userInput>=lowerValue&&userInput<=upperValue){
                    return userInput;
                }else{
                    System.out.println("Input is out of range!");
                }

            }catch(Exception e){
                System.out.println("Enter a integer value!");
                input.next();
            }
        }
    }

    public static void buy_ticket(int[][] row) {  //Option 1
        Scanner input = new Scanner(System.in);
        //Scanner input = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = input.next();
        System.out.print("Enter your surname: ");
        String surname = input.next();
        System.out.print("Enter your email: ");
        String email = input.next();
        Person person = new Person(name, surname, email);


        int row_num = rowSeatValidation("Enter row number (1-3): ",1,3);

        int seat_num =rowSeatValidation("Enter seat number (1-" + row[row_num-1].length + "): ",1,row[row_num-1].length);

        // Check if the seat is available
        if (row[row_num-1][seat_num-1] == 1) {
            System.out.println("This seat is already taken.");
            System.out.println();
            selection();
        }

        // Reserve the seat
        row[row_num-1][seat_num-1] = 1;

        // Confirm of the reservation of the seat
        System.out.print("Enter price: ");
        int price = input.nextInt();
        System.out.println("Seat " + seat_num + " in row " + row_num + " is now reserved.");
        System.out.println();
        Ticket newTicket = new Ticket(row_num, seat_num, price, person);
        tickets.add(newTicket);

        selection();
    }
    public static void Seating_areas(int[][] row) {  //Option 2 Displaying seating area
        System.out.println("    *********** ");
        System.out.println("    *  STAGE  * ");
        System.out.println("    *********** ");

        for (int i = 0; i < row.length; i++) {
            if(i==0){
                System.out.print("    ");
            }
            else if (i==1){
                System.out.print("  ");
            }
            for (int j = 0; j < row[i].length; j++) {
                if((i==0&&j==6) || (i==1&&j==8) || (i==2&&j==10)){
                    System.out.print(" ");
                }
                if (row[i][j] == 0) {
                    System.out.print("O");
                } else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }
        System.out.println();
        selection();
    }

    public static void cancel_ticket(int[][] seats) {  // Option 3 Cancelling Ticket
        Scanner input = new Scanner(System.in);
        int row_c = rowSeatValidation("Enter row number (1-3): ",1,3)-1;

        int seat_c =rowSeatValidation("Enter seat number (1-" + row[row_c].length + "): ",1,row[row_c].length)-1;


        if (row_c < 0 || row_c >= seats.length || seat_c < 0 || seat_c >= seats[row_c].length) {
            System.out.println("Invalid row or seat number.");
            return;
        }
        if (seats[row_c][seat_c] == 0) {
            System.out.println("This seat is already available.");
            System.out.println();
            selection();
        }
        seats[row_c][seat_c] = 0;
        System.out.println("The ticket has been cancelled.");
        System.out.println();

        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            if (ticket.getRow() == row_c + 1 && ticket.getSeat() == seat_c + 1) {
                tickets.remove(i);
                System.out.println("Ticket cancelled successfully.");
                System.out.println();
                selection();
            }
        }

        selection();
    }

    public static void show_available(int[][] seats) { //Option 4 Listing available seats in numbers
        for (int i = 0; i < seats.length; i++) {
            System.out.print("Seats available in row " + (i+1) + ": ");
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 0) {
                    System.out.print((j+1) + ", ");
                }
            }
            System.out.println();
        }
        System.out.println();
        selection();
    }
    public static void save(int[][] row) {  // Option 5 Saving tickets to a file
        try {
            FileWriter writer = new FileWriter("C:\\Users\\User\\Desktop\\Seats.txt");
            for (int i = 0; i < row.length; i++) {
                for (int j = 0; j < row[i].length; j++) {
                    writer.write(row[i][j] + " ");
                }
                writer.write("\n");
            }
            writer.close();
            System.out.println("Data saved to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving data to file.");
            e.printStackTrace();
        }
        System.out.println();
        selection();
    }


    public static void load(int[][] row) { //Option 6 Loading file to the program to see seats purchased
        try {
            // Open the file input stream and scanner
            FileInputStream fileIn = new FileInputStream("C:\\Users\\Rochana Godigamuwa\\Desktop\\Seats.txt");
            Scanner scanner = new Scanner(fileIn);

            // Read the integers from the file and store them in the array
            for (int i = 0; i < row.length; i++) {
                for (int j = 0; j < row[i].length; j++) {
                    if (scanner.hasNextInt()) {
                        row[i][j] = scanner.nextInt();
                    }
                }
            }

            // Close the input streams
            scanner.close();
            fileIn.close();

            System.out.println("Seats loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading seats: " + e.getMessage());
        }
        System.out.println();
        selection();
    }
    public static void show_tickets_info() { //Option 7 showing ticket inf and total price
        double total = 0.0;
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            ticket.print();
            total += ticket.getPrice();
        }
        System.out.println("Total price of all tickets: Rs:" + total);
        System.out.println();
        selection();
    }
    public static List<Ticket> sort_tickets(List<Ticket> tickets) { //Option 8 Sorting tickets
        Collections.sort(tickets,Comparator.comparing(Ticket::getPrice));
        show_tickets_info();
        return tickets;


    }


}















