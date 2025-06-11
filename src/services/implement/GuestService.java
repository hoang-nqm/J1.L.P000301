package services.implement;

import model.Guest;
import model.Room;
import services.interfaces.IGuestService;
import services.interfaces.IRoomService;
import utils.FileUltils;
import utils.Validations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuestService implements IGuestService {
    List<Guest> listGuest;
    IRoomService roomService;
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

            Guest guest = new Guest(
                    fullName, birthDay, gender, phoneNumber, nationalID, selectedRoom.getRoomID(), rentalDays, startDate, coTenAnName, startDate
            );
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
            System.out.printf("Room ID     : %s\n", guest.getRoomID());
            System.out.printf("Check-in    : %s\n", guest.getCheckInDate());
            System.out.printf("Check-out   : %s\n", guest.getCheckOutDate());
            System.out.printf("Rental days : %d days\n", guest.getRentalDays());
            System.out.println("------------------------------------------------------------");
            System.out.println();
        }

        System.out.println("============================================================");
    }


}
