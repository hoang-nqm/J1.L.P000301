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
    public final String roomFilePath ="src/resources/Active_Room_List.txt";
    private List<Room> listRooms;



    public GuestService() {
        listGuest = new ArrayList<>();
        roomService = new RoomServices();
        listRooms = FileUltils.importRoomData(roomFilePath);
    }


    @Override
    public void enterInformationGuest() {
        String nationalID = Validations.inputNationID(listGuest);
        String fullName = Validations.inputFullName();
        String phoneNumber = Validations.inputPhoneNumber();
        LocalDate birthDay = Validations.inputBirthDay();
        String gender = Validations.inputGender();
        LocalDate startDate = Validations.inputStartDate();
        int rentalDays= Validations.inputRentalDate();

        roomService.displayRoomByDate(listRooms,startDate,rentalDays);
        Room selectedRoom = Validations.selectRoom(listRooms,startDate,rentalDays);

        String coTenAnName = Validations.inputCotentantName();

        Guest guest = new Guest(
                fullName,birthDay,gender,phoneNumber,nationalID,selectedRoom.getRoomID(),rentalDays,startDate,coTenAnName,startDate
                );
listGuest.add(guest);
selectedRoom.getGuestsStay().add(guest);
        System.out.println("khach dang ky thanh cong phong :"+selectedRoom.getRoomID());
        System.out.println("checkin: "+startDate);
        System.out.println("checkout: "+startDate.minusDays(rentalDays));

        // khach 1 thue room 1 tu ngay 5/6-8/6
        // khach 2 6/6 , danh sach hien thi cua khach hang khong hien thi thang room 1

    }



}
