package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Guest extends Person implements Serializable {
    private String nationalID;
    private String roomID;
    private int rentalDays;
private LocalDate startDate;
private String coTenAntName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Guest(String fullName, LocalDate birthDate, String gender, String phoneNumber, String nationalID, String roomID, int rentalDays, LocalDate startDate, String coTenAntName,LocalDate checkInDate) {
        super(fullName, birthDate, gender, phoneNumber);
        this.nationalID = nationalID;
        this.roomID = roomID;
        this.rentalDays = rentalDays;
        this.checkInDate = checkInDate;
        this.checkOutDate= checkInDate.plusDays(rentalDays);
        this.startDate = startDate;
        this.coTenAntName = coTenAntName;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getCoTenAntName() {
        return coTenAntName;
    }

    public void setCoTenAntName(String coTenAntName) {
        this.coTenAntName = coTenAntName;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
