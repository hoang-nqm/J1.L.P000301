package services.interfaces;

import model.Room;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {

    void loadRoomFromFile();
    void displayRoomsAvailable();
    void displayRoomByDate(List<Room> rooms, LocalDate startDate, int rentalDays);

}
