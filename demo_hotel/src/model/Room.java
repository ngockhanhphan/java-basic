package model;

public class Room implements IRoom {
    String roomNumber;
    Double price;
    RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber
                + ", Price: " + price
                + ", Type: " + enumeration;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public RoomType getRoomType() {
        return this.enumeration;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public boolean isFree() {
        return false;
    }
}
