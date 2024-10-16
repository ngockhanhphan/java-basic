package ui;

import java.util.Scanner;

public class MainMenu {
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
}
