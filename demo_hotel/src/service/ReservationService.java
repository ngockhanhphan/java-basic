package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ReservationService {
    private static final ReservationService SINGLETON = new ReservationService();
    private final List<IRoom> rooms = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();

    public static ReservationService getSINGLETON() {
        return SINGLETON;
    }

    public void addRoom(IRoom room) {
        if (rooms.contains(room)) {
            System.out.println("Room number is existing.");
        } else {
            rooms.add(room);
            System.out.println("Add room successfully.");
        }
    }
    public IRoom getARoom(String roomId) {
        for (IRoom room: rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }
    public  Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        final Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }
    public List<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        final ArrayList<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : rooms) {
            boolean isAvailableRoom = true;
            for (Reservation reservation : reservations) {
                if (reservation.getRoom().equals(room)) {
                    if (!(checkOutDate.before(reservation.getCheckInDate()) || checkInDate.after(reservation.getCheckOutDate()))) {
                        isAvailableRoom = false;
                        break;
                    }
                }
            }
            if (isAvailableRoom) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public List<Reservation> getCustomerReservations(Customer customer) {
        final ArrayList<Reservation> customerReservation = new ArrayList<>();

        for (Reservation reservation: reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservation.add(reservation);
            }
        }
        return customerReservation;
    }

    public Collection<IRoom> getAllRooms() {
        return rooms;
    }
    public void printAllReservation() {
        if (reservations.isEmpty()) {
            System.out.println("No reservation!");
        } else {
            for(Reservation reservation : reservations) {
                System.out.println(reservation + "\n");
            }
        }
    }

    public void printAllRooms() {
        if (rooms.isEmpty()) {
            System.out.println("No room!");
        } else {
            rooms.forEach(System.out::println);
        }
    }
}
