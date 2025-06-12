package services.implement;

import model.Guest;
import model.Room;
import services.interfaces.IGuestService;
import services.interfaces.IRoomService;
import utils.FileUltils;
import utils.Validations;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GuestService implements IGuestService {
    List<Guest> listGuest;
    IRoomService roomService;
    public static Scanner sc = new Scanner(System.in);
    private List<Room> listRooms;
    public final String roomFilePath = "src/resources/roomList.dat";
    public final String guestFilePath = "src/resources/guestInfo.dat";


    public GuestService() {
        listGuest = FileUltils.loadGuestListFromFile(guestFilePath);
        roomService = new RoomServices();
        listRooms = FileUltils.loadRoomListFromFile(roomFilePath);
    }

    private void ensureRoomLoaded() {
        if (listRooms == null || listRooms.isEmpty()) {
            listRooms = FileUltils.loadRoomListFromFile(roomFilePath);
        }
    }

    @Override
    public void enterInformationGuest() {
        ensureRoomLoaded();
        String nationalID = Validations.inputNationID(listGuest);
        String fullName = Validations.inputFullName();
        String phoneNumber = Validations.inputPhoneNumber();
        LocalDate birthDay = Validations.inputBirthDay();
        String gender = Validations.inputGender();
        LocalDate startDate = Validations.inputStartDate();
        int rentalDays = Validations.inputRentalDate();
        boolean check = roomService.displayRoomByDate(listRooms, startDate, rentalDays);


        if (check) {
            Room selectedRoom = Validations.selectRoom(listRooms, startDate, rentalDays);

            String coTenAnName = Validations.inputCotentantName();

            Guest guest = new Guest(fullName, birthDay, gender, phoneNumber, nationalID, selectedRoom.getRoomID(), rentalDays, startDate, coTenAnName, startDate);
            listGuest.add(guest);
            selectedRoom.getGuestsStay().add(guest);
            System.out.println("khach dang ky thanh cong phong :" + selectedRoom.getRoomID());
            System.out.println("checkin: " + startDate);
            System.out.println("checkout: " + startDate.minusDays(rentalDays));
            roomService.saveRoomToFile(listRooms);
        }

        // khach 1 thue room 1 tu ngay 5/6-8/6
        // khach 2 6/6 , danh sach hien thi cua khach hang khong hien thi thang room 1

    }

    @Override
    public void saveGuestListToFile() {
        FileUltils.saveGuestListToFile(listGuest, guestFilePath);
    }

    @Override
    public void displayGuestList() {
        if (listGuest.isEmpty()) {
            System.out.println("No guests found in the system.");
            return;
        }

        System.out.println("============================================================");
        System.out.println("                    GUEST LIST REPORT                      ");
        System.out.println("============================================================");
        System.out.printf("Total guests: %d\n\n", listGuest.size());

        int index = 1;
        for (Guest guest : listGuest) {
            System.out.println("------------------------------------------------------------");
            System.out.printf("[%d] Guest Information [National ID: %s]\n", index++, guest.getNationalID());
            System.out.println("------------------------------------------------------------");
            System.out.printf("Full name   : %s\n", guest.getFullName());
            System.out.printf("Phone number: %s\n", guest.getPhoneNumber());
            System.out.printf("Birth date  : %s\n", guest.getBirthDate());
            System.out.printf("Gender      : %s\n", guest.getGender());
            System.out.printf("Co-tenant   : %s\n", guest.getCoTenAntName() != null ? guest.getCoTenAntName() : "None");
            System.out.println("------------------------------------------------------------");
            String status = (guest.getCheckInDate() == null || guest.getCheckOutDate()==null) ? "CANCELLED" :"ACTIVE";
            System.out.printf("Status      : %s\n", status);

           if(!status.equalsIgnoreCase("CANCELLED")) {
               System.out.printf("Room ID     : %s\n", guest.getRoomID());
               System.out.printf("Check-in    : %s\n", guest.getCheckInDate());
               System.out.printf("Check-out   : %s\n", guest.getCheckOutDate());
               System.out.printf("Rental days : %d days\n", guest.getRentalDays());
               System.out.println("------------------------------------------------------------");
           }else{
               System.out.println("Booking cancelled");
           }
        }

        System.out.println("============================================================");
    }

    @Override
    public void bookingByNationalID() {
        Guest selectedGuest = Validations.getGuestByNationalID(listGuest);
        LocalDate startDate = Validations.inputStartDate();
        int rentalDays = Validations.inputRentalDate();
        boolean check = roomService.displayRoomByDate(listRooms, startDate, rentalDays);


        if (check) {
            Room selectedRoom = Validations.selectRoom(listRooms, startDate, rentalDays);
            String coTenAnName = Validations.inputCotentantName();
            Guest guest = new Guest(selectedGuest.getFullName(), selectedGuest.getBirthDate(), selectedGuest.getGender(), selectedGuest.getPhoneNumber(), selectedGuest.getNationalID(), selectedRoom.getRoomID(), rentalDays, startDate, coTenAnName, startDate);
            listGuest.add(guest);
            selectedRoom.getGuestsStay().add(guest);
            System.out.println("khach dang ky thanh cong phong :" + selectedRoom.getRoomID());
            System.out.println("checkin: " + startDate);
            System.out.println("checkout: " + startDate.minusDays(rentalDays));
            roomService.saveRoomToFile(listRooms);
        }

    }

    @Override
    public void cancelBookingByNationalID() {
        String nationIDCancel = Validations.inputNationIDCancel();
        boolean exists = false;
        for (Guest guest : listGuest) {
            if (guest.getNationalID().equals(nationIDCancel)) {
                exists = true;
            }
        }
        if (exists) {
            List<Guest> bookings = Validations.getGuestsByNationalID(listGuest, nationIDCancel);
            if (bookings.isEmpty() || bookings == null) {
                System.out.println("Khach hang khong co phong de cancel");
                return;
            }
            System.out.println("Upcoming bookings: ");
            for (int i = 0; i < bookings.size(); i++) {
                Guest guest = bookings.get(i);
                System.out.printf("[%d] Room: %s | Checkin: %s | Days: %d\n ",
                        i + 1, guest.getRoomID(), guest.getCheckInDate(), guest.getRentalDays());
            }
            int choice = -1;
            while (choice < 1 || choice > bookings.size()) {
                System.out.println("Select booking to cancel [1-" + bookings.size() + "]: ");
                choice = Integer.parseInt(sc.nextLine());
                Guest selectedGuest = bookings.get(choice - 1);
                System.out.println("------------------------------------------------------------");
                System.out.printf("Guest information [National ID: %s]\n", selectedGuest.getNationalID());
                System.out.println("------------------------------------------------------------");
                System.out.printf("Full name   : %s\n", selectedGuest.getFullName());
                System.out.printf("Phone number: %s\n", selectedGuest.getPhoneNumber());
                System.out.printf("Birth day   : %s\n", selectedGuest.getBirthDate());
                System.out.printf("Gender      : %s\n", selectedGuest.getGender());
                System.out.println("------------------------------------------------------------");
                System.out.printf("Rental room : %s\n", selectedGuest.getRoomID());
                System.out.printf("Check in    : %s\n", selectedGuest.getCheckInDate());
                System.out.printf("Rental days : %d\n", selectedGuest.getRentalDays());
                System.out.printf("Check out   : %s\n", selectedGuest.getCheckOutDate());
                System.out.println("------------------------------------------------------------");
                Room selectedRoom = Validations.getRoomByID(listRooms, selectedGuest.getRoomID());
                System.out.println("Room information:");
                System.out.printf("+ ID       : %s\n", selectedRoom.getRoomID());
                System.out.printf("+ Room     : %s\n", selectedRoom.getRoomName());
                System.out.printf("+ Type     : %s\n", selectedRoom.getRoomType());
                System.out.println();
                System.out.printf("+ Daily rate: %.0f$\n", selectedRoom.getDailyRate());
                System.out.printf("+ Capacity  : %d\n", selectedRoom.getCapacity());
                System.out.printf("+ Funiture  : %s\n", selectedRoom.getFurnitureDescription());
                System.out.println("------------------------------------------------------------");
                System.out.println("Do you want ro really want to cancel this room? (Y/N)");
                String confirm = sc.nextLine();
                if(!confirm.equalsIgnoreCase("Y")) {
                    System.out.println("Dung cancel");
                    return;
                }
                for(Room room : listRooms) {
                    if(room.getRoomID().equalsIgnoreCase(selectedRoom.getRoomID())) {
                        room.getGuestsStay().removeIf(guest -> guest.getNationalID().equalsIgnoreCase(nationIDCancel));
                        selectedGuest.setRoomID(null);
                        selectedGuest.setCheckInDate(null);
                        selectedGuest.setCheckOutDate(null);
                        System.out.println("The booking associated with ID " + selectedGuest.getNationalID() + " has been successfully canceled.");
                        return;
                    }
                }
            }
        } else {
            System.out.println("Booking with ID " + nationIDCancel + " not found in the system.");
        }

    }

    @Override
    public void monthlyRevenueReport() {
        roomService.generateMonthlyRevenueReport(listGuest,listRooms);
    }


}
