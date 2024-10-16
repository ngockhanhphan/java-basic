package api;

public class AdminResource {
    private static final AdminResource SINGLETON = new AdminResource();

    public static AdminResource getSINGLETON() {
        return SINGLETON;
    }
}
