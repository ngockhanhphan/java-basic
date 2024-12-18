package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static final AdminResource SINGLETON = new AdminResource();

    private final CustomerService customerService = CustomerService.getSINGLETON();
    private final ReservationService reservationService = ReservationService.getSINGLETON();

    public static AdminResource getSINGLETON() {
        return SINGLETON;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        rooms.forEach(reservationService::addRoom);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }


    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void printAllCustomers() {
        customerService.printAllCustomers();
    }

    public void showAllReservations() {
        reservationService.printAllReservation();
    }

    public void printAllRooms() {
        reservationService.printAllRooms();
    }
}
