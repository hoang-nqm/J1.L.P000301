package services.interfaces;

public interface IGuestService {


    void enterInformationGuest();
    void saveGuestListToFile();
    void displayGuestList();
    void bookingByNationalID();
    void cancelBookingByNationalID();
    void monthlyRevenueReport();
}
