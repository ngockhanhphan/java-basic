package ui;

import java.util.Scanner;
import api.HotelResource;
import model.Customer;
import model.Reservation;
import model.IRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getSINGLETON();
    private static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

    Scanner scanner = new Scanner(System.in);

    public MainMenu(Scanner scanner) { this.scanner = scanner; }

    public void mainMenu() {
        showMainMenu();
    }

    public static void showMainMenu() {
        System.out.print("""
                Welcome to the Hotel Reservation Application!
                ------------------------------------
                1. Find and reserve a room.
                2. See my reservations.
                3. Create an Account.
                4. Admin.
                5. Exit.
                ------------------------------------
                Please select a number for the menu option:
                """);
    }

    private int getUserInput() {
        String input = scanner.nextLine();
        int inputNumber = Integer.parseInt(input);
        try {
            if (inputNumber < 1 || inputNumber > 5) {
                System.out.println("Invalid input. Please enter a number between the range:");
                return getUserInput();
            } else {
                return inputNumber;
            }
        } catch (NumberFormatException ex) {
            System.out.println("Empty input received.");
            return getUserInput();
        }
    }

    private void performTheInput(int input) {
        switch (input) {
            case 1 -> findAndReserveARoom();
            case 2 -> seeMyReservations();
            case 3 -> createAccount();
            case 4 -> {
                final AdminMenu adminMenu = new AdminMenu(scanner);
                adminMenu.adminMenu();
            }
            case 5 -> System.out.println("Exiting...");
            default -> System.out.println("Unknown input.");
        }
    }

    private static void findAndReserveARoom() {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Check-In Date mm/dd/yyyy, example 02/01/2020");
        Date checkIn = inputDate(scanner);

        System.out.println("Enter Check-Out Date mm/dd/yyyy, example 02/21/2020");
        Date checkOut = inputDate(scanner);

        final List<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);

        if (availableRooms.isEmpty()) {
            System.out.println("We do not have any available rooms on your chosen date range.");
        } else {
            System.out.println("There are available rooms founded:");
            availableRooms.forEach(availableRoom -> System.out.println(availableRoom));
            reserveRoom(scanner, checkIn, checkOut, availableRooms);
        }
    }

    private static Date inputDate(final Scanner scanner) {
        try {
            final Date date = new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(scanner.nextLine());
            if (date.before(new Date())) {
                System.out.println("Please enter a future date.");
                return inputDate(scanner);
            }
            return date;
        } catch (ParseException ex) {
            System.out.println("Error: Invalid date.");
            inputDate(scanner);
        }

        return null;
    }

    private static void seeMyReservations() {
        final Scanner myReservationScanner = new Scanner(System.in);

        System.out.println("Enter your Email with format: name@domain.com");
        String myEmail;

        do {
            myEmail = myReservationScanner.nextLine(); // Get the first character
            if (!isValidEmail(myEmail)) {
                System.out.println("Please input a valid email (name@domain.com)");
            }
        } while (!isValidEmail(myEmail));

        final Collection<Reservation> myReservations = hotelResource.getCustomerReservations(myEmail);

        if (hotelResource.getCustomer(myEmail) == null) {
            System.out.println("No customer found with this email.");
            System.out.println("----------------------");
            System.out.println("Back to main menu.");
            showMainMenu();
        } else {
            if (myReservations.isEmpty()) {
                System.out.println("You do not have any reservations.");
            } else {
                System.out.println("Your reservations:");
                myReservations.forEach(reservation -> System.out.println("\n" + reservation));
            }
        }
    }

    private static boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void createAccount() {
        System.out.println("Enter Email with format: name@domain.com");
        String email;
        do {
            email = scanner.nextLine();
            if (!isValidEmail(email)) {
                System.out.println("Please input valid email format (name@domain.com)");
            }
        } while (!isValidEmail(email));

        System.out.println("First name:");
        String firstName = getValidatedName();

        System.out.println("Last name:");
        String lastName = getValidatedName();

        try {
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");

            showMainMenu();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            createAccount();
        }
    }
    private String getValidatedName() {
        String name = scanner.nextLine();
        do {
            if (name == null || name.trim().isEmpty()) {
                System.out.println("Please input valid last name.");
                return getValidatedName();
            }
        } while (name == null || name.trim().isEmpty());
        return name;
    }

    private static void reserveRoom(Scanner scanner, Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) {
        System.out.println("Would you like to book? y/n");

        final String inputtedBookRoom = scanner.nextLine();

        try {
            do {
                if (!inputtedBookRoom.equals('y') && !inputtedBookRoom.equals('n')) {
                    System.out.println("Invalid input. Please enter 'Y (Yes)' or 'N (No)'.");
                }
            } while (!inputtedBookRoom.equals('y') && !inputtedBookRoom.equals('n'));

            if (inputtedBookRoom.equals('y')) {
                System.out.println("Do you have an account with us (y/n)?");
                final String haveAccount = scanner.nextLine();
                do {
                    if (!haveAccount.equals('y') && !haveAccount.equals('n')) {
                        System.out.println("Invalid input. Please enter 'Y (Yes)' or 'N (No)'.");
                    }
                } while (!haveAccount.equals('y') && !haveAccount.equals('n'));

                if (haveAccount.equals('y')) {
                    System.out.println("Enter your email:");
                    String email;
                    Customer customer;
                    do {
                        email = scanner.nextLine(); // Get the first character
                        if (!isValidEmail(email)) {
                            System.out.println("Please input a valid email. Example: name@domain.com");
                        }
                        customer = hotelResource.getCustomer(email);
                        if (customer == null) {
                            System.out.println("Customer does not exist. Please input another email.");
                        }
                    } while (!isValidEmail(email) || customer == null);

                    System.out.println("What room number would you like to reserve?");

                    IRoom bookedRoom = null;
                    do {
                        String roomNumber = scanner.nextLine();
                        for (IRoom room : rooms) {
                            if (room.getRoomNumber().equals(roomNumber.toUpperCase())) {
                                bookedRoom = room;
                            }
                        }
                        if (bookedRoom == null) {
                            System.out.println("Please enter available room number:");
                        }
                    } while (bookedRoom == null);

                    Reservation reservation = hotelResource.bookARoom(customer.getEmail(), bookedRoom, checkInDate, checkOutDate);
                    System.out.println("Congratulations! Room booked successfully!");
                    System.out.println(reservation.toString());
                    showMainMenu();
                } else {
                    System.out.println("Please create an account to continue.");
                    showMainMenu();
                }
            } else {
                showMainMenu();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
