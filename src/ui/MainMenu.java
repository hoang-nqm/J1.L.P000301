package ui;


import services.implement.GuestService;
import services.implement.RoomServices;
import services.interfaces.IGuestService;
import services.interfaces.IRoomService;
import utils.Input;

public class MainMenu {

    IRoomService roomService;
    IGuestService guestService;

    public MainMenu() {
        roomService = new RoomServices();
        guestService = new GuestService();
    }


    public void mainMenu() {
        int choice;
        do {
            System.out.println("Welcome to the main menu");
            System.out.println("Please select one of the following options:");
            System.out.println("1. Import Room Data from Text File");
            System.out.println("2. Display Available Room List");
            System.out.println("3. Enter Guest Information");
            System.out.println("4. Update Guest Stay Information");
            System.out.println("5. Search Guest by National ID");
            System.out.println("6. Delete Guest Reservation Before Arrival");
            System.out.println("7. List Vacant Rooms");
            System.out.println("8. Monthly Revenue Report");
            System.out.println("9. Revenue Report by Room Type");
            System.out.println("10. Save Guest Information");
            System.out.println("11. Display Guest List");
            System.out.println("12. Booking");
            System.out.println("Orther. Exit");

            Input input = new Input();
            choice = input.inputMenuChoice();


            switch (choice) {
                case 1:
                   roomService.loadRoomFromFile();
                    break;
                case 2:
                    roomService.displayRoomsAvailable();
                    break;
                case 3:
                    guestService.enterInformationGuest();
                    break;
                case 4:
                    System.out.println("Enter Customer ID");
                    break;
                case 5:
                    System.out.println("Enter Customer ID");
                    break;
                case 6:
                   guestService.cancelBookingByNationalID();
                    break;
                case 7:
                    System.out.println("Enter Customer ID");
                    break;
                case 8:
                    guestService.monthlyRevenueReport();
                    break;
                case 9:
                    System.out.println("Enter Customer ID");
                    break;
                case 10:
                   guestService.saveGuestListToFile();
                    break;
                case 11:
                   guestService.displayGuestList();
                    break;
                case 12:
                   guestService.bookingByNationalID();
                    break;

                default:
                    System.out.println("Goodbye!!!");
            }
        } while (choice >= 1 && choice <= 12);

    }
}
