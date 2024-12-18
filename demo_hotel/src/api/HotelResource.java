package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Date;
import java.util.List;

public class HotelResource {
    private static final HotelResource SINGLETON = new HotelResource();
    private final ReservationService reservationService = ReservationService.getSINGLETON();
    private final CustomerService customerService = CustomerService.getSINGLETON();

    public static HotelResource getSINGLETON() {
        return SINGLETON;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public List<Reservation> getCustomerReservations(String customerEmail) {
        return reservationService.getCustomerReservations(getCustomer(customerEmail));
    }

    public List<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }
}
