package model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomID;
    private String roomName;
    private String roomType;
    private double dailyRate;
    private int capacity;
    private String furnitureDescription;

    private List<Guest> guestsStay = new ArrayList<>();// danh sachs khach thue

    public Room(String roomID, String roomName, String roomType, double dailyRate, int capacity, String furnitureDescription) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomType = roomType;
        this.dailyRate = dailyRate;
        this.capacity = capacity;
        this.furnitureDescription = furnitureDescription;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFurnitureDescription() {
        return furnitureDescription;
    }

    public void setFurnitureDescription(String furnitureDescription) {
        this.furnitureDescription = furnitureDescription;
    }

    public List<Guest> getGuestsStay() {
        return guestsStay;
    }

    public void setGuestsStay(List<Guest> guestsStay) {
        this.guestsStay = guestsStay;
    }
}
