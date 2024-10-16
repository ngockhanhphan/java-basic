package api;

public class HotelResource {
    public static final HotelResource SINGLETON = new HotelResource();

    public static HotelResource getSingleton() {
        return SINGLETON;
    }
}
