package service;

public class ReservationService {
    public static final ReservationService SINGLETON = new ReservationService();

    public static ReservationService getSingleton() {
        return SINGLETON;
    }
}
