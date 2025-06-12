package services.interfaces;

import model.Guest;
import model.Room;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {

    void loadRoomFromFile();
    void displayRoomsAvailable();
    boolean displayRoomByDate(List<Room> rooms, LocalDate startDate, int rentalDays);
    void saveRoomToFile(List<Room> roomList);
    void generateMonthlyRevenueReport(List<Guest> listguests,List<Room> rooms);
}
