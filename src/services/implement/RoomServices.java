package services.implement;

import model.Room;
import services.interfaces.IRoomService;
import utils.FileUltils;
import utils.Validations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomServices implements IRoomService {
    private List<Room> listRooms;

    public final String roomFilePath ="src/resources/Active_Room_List.txt";
    public final String roomListFilePath ="src/resources/roomList.dat";


    public RoomServices() {
        listRooms = FileUltils.importRoomData(roomFilePath);
    }

    @Override
    public void loadRoomFromFile() {
        FileUltils.importRoomData(roomFilePath);
        saveRoomToFile(listRooms);
    }

    @Override
    public void displayRoomsAvailable() {
        if (listRooms == null || listRooms.isEmpty()) {
            System.out.println("Room list is currently empty, not loaded yet.");
            return;
        }

        System.out.printf("%-6s | %-18s | %-8s | %-6s | %-8s | %-30s\n",
                "RoomID", "RoomName", "Type", "Rate", "Capacity", "Furniture");
        System.out.println("----------------------------------------------------------------------");

        for (Room room : listRooms) {
            System.out.printf("%-6s | %-18s | %-8s | %-6.2f | %-8d | %-30s\n",
                    room.getRoomID(), room.getRoomName(), room.getRoomType(),
                    room.getDailyRate(), room.getCapacity(), room.getFurnitureDescription());
        }
    }

    @Override
    public boolean displayRoomByDate(List<Room> rooms, LocalDate startDate, int rentalDays) {
        boolean available = false;
         LocalDate checkoutDate = startDate.plusDays(rentalDays);
        System.out.println("Available Rooms from " + startDate + " to " + checkoutDate.minusDays(1) + ":");
        System.out.printf("%-6s | %-15s | %-8s | %-6s | %-8s | %s\n",
                "RoomID", "RoomName", "Type", "Rate", "Capacity", "Furniture");
        System.out.println("-----------------------------------------------------------------------");
        for (Room room : rooms) {
            if(Validations.isValiable(startDate,rentalDays,room)){
                System.out.printf("%-6s | %-15s | %-8s | %-6.2f | %-8d | %s\n",
                        room.getRoomID(), room.getRoomName(), room.getRoomType(),
                        room.getDailyRate(), room.getCapacity(), room.getFurnitureDescription());
                available = true;
            }
        }
        if(!available) {
            System.out.println("Room not found hoac la da het phong");
        }
        return available;
    }

    @Override
    public void saveRoomToFile(List<Room> roomList) {
        FileUltils.saveRoomListToFile(roomList,roomListFilePath);
    }
}
