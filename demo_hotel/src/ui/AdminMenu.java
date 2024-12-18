package ui;
import api.AdminResource;
import model.*;

import java.util.Collections;
import java.util.Scanner;

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getSINGLETON();
    Scanner scanner;

    public AdminMenu(Scanner scanner) {
        this.scanner = scanner;
    }
    public void adminMenu() {
        showAdminMenu();

        int input = getUserInput();
        performTheInput(input);
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
            case 1 -> showAllCustomers();
            case 2 -> showAllRooms();
            case 3 -> showAllReservations();
            case 4 -> addARoom();
            case 5 -> backToMainMenu();
            default -> System.out.println("Unknown input.");
        }
    }

    private void backToMainMenu() {
        final MainMenu mainMenu = new MainMenu(scanner);
        mainMenu.mainMenu();
    }

    private  void showAdminMenu() {
        System.out.print("""
                Admin Menu
                ------------------------------------
                1. See all Customers.
                2. See all Rooms.
                3. See all Reservations.
                4. Add a Room.
                5. Back to Main Menu.
                ------------------------------------
                Please select a number for the menu option:
                """);
    }

    private void showAllCustomers() {
        adminResource.printAllCustomers();
    }

    private void showAllRooms() {
        adminResource.printAllRooms();
    }

    private void showAllReservations() {
        adminResource.showAllReservations();
    }

    private void addARoom() {
        try {
            final Scanner addRoomScanner = new Scanner(System.in);

            System.out.println("Enter room number:");
            final String roomNumber = addRoomScanner.nextLine();

            System.out.println("Enter room type: 1 for single bed, 2 for double bed:");
            final RoomType roomType = enterRoomType(addRoomScanner);

            IRoom room;

            if (checkForFreeRoom()) {
                room = new FreeRoom(roomNumber, roomType);
            } else {
                System.out.println("Enter price per night:");
                final double roomPrice = enterRoomPrice(addRoomScanner);
                room = new Room(roomNumber, roomPrice,roomType);
            }
            adminResource.addRoom(Collections.singletonList(room));
            System.out.println("Room added successfully!");

            // Option to add other room
            addOtherRoom();
        } catch (Exception ex) {
            System.out.println("Invalid input. Please try again.");
            addOtherRoom();
        }
    }

    private double enterRoomPrice(Scanner scanner) {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid room price! Please, enter a valid double number. \n" +
                    "Decimals should be separated by point (.)");
            return enterRoomPrice(scanner);
        }
    }

    private static RoomType enterRoomType(Scanner scanner) {
        try {
            int roomType = scanner.nextInt();
            return switch (roomType) {
                case 1:
                    yield RoomType.SINGLE;
                case 2:
                    yield RoomType.DOUBLE;
                default:
                    System.out.println("Invalid room type choice. Setting room type to SINGLE.");
                    yield enterRoomType(scanner);
            };
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid room type! Please, enter a valid number.");
            return  enterRoomType(scanner);
        }
    }

    private void addOtherRoom() {
        System.out.println("Would like to add another room? y/n");

        try {
            char otherRoom = scanner.nextLine().charAt(0);

            if (otherRoom == 'y') {
                addARoom();
            } else if (otherRoom == 'n') {
                showAdminMenu();
            } else {
                System.out.println("Please enter y (Yes) or n (No)");
                addOtherRoom();
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            addOtherRoom();
        }
    }

    private static boolean checkForFreeRoom() {
        System.out.println("Is this room free? y/n:");

        final Scanner scanner = new Scanner(System.in);

        try {
            char isFreeRoom = scanner.nextLine().charAt(0);
            if (isFreeRoom == 'y') {
                return true;
            } else if (isFreeRoom == 'n') {
                return false;
            } else {
                System.out.println("Please enter y (Yes) or n (No)");
                return checkForFreeRoom();
            }

        }  catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            return checkForFreeRoom();
        }
    }
}