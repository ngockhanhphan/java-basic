package ui;

import java.util.Scanner;

public class HotelReservation {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final MainMenu mainMenu = new MainMenu(scanner);
        mainMenu.mainMenu();
    }
}