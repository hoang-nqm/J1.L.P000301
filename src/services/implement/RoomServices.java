package services.implement;

import model.Guest;
import model.Room;
import services.interfaces.IRoomService;
import utils.FileUltils;
import utils.Validations;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class RoomServices implements IRoomService {
    private List<Room> listRooms;
    public static Scanner sc = new Scanner(System.in);

    public final String roomFilePath = "src/resources/Active_Room_List.txt";
    public final String roomListFilePath = "src/resources/roomList.dat";


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

        System.out.printf("%-6s | %-18s | %-8s | %-6s | %-8s | %-30s\n", "RoomID", "RoomName", "Type", "Rate", "Capacity", "Furniture");
        System.out.println("----------------------------------------------------------------------");

        for (Room room : listRooms) {
            System.out.printf("%-6s | %-18s | %-8s | %-6.2f | %-8d | %-30s\n", room.getRoomID(), room.getRoomName(), room.getRoomType(), room.getDailyRate(), room.getCapacity(), room.getFurnitureDescription());
        }
    }

    @Override
    public boolean displayRoomByDate(List<Room> rooms, LocalDate startDate, int rentalDays) {
        boolean available = false;
        LocalDate checkoutDate = startDate.plusDays(rentalDays);
        System.out.println("Available Rooms from " + startDate + " to " + checkoutDate.minusDays(1) + ":");
        System.out.printf("%-6s | %-15s | %-8s | %-6s | %-8s | %s\n", "RoomID", "RoomName", "Type", "Rate", "Capacity", "Furniture");
        System.out.println("-----------------------------------------------------------------------");
        for (Room room : rooms) {
            if (Validations.isValiable(startDate, rentalDays, room)) {
                System.out.printf("%-6s | %-15s | %-8s | %-6.2f | %-8d | %s\n", room.getRoomID(), room.getRoomName(), room.getRoomType(), room.getDailyRate(), room.getCapacity(), room.getFurnitureDescription());
                available = true;
            }
        }
        if (!available) {
            System.out.println("Room not found hoac la da het phong");
        }
        return available;
    }

    @Override
    public void saveRoomToFile(List<Room> roomList) {
        FileUltils.saveRoomListToFile(roomList, roomListFilePath);
    }

    @Override
    public void generateMonthlyRevenueReport(List<Guest> listguests, List<Room> rooms) {
        System.out.println("Enter month (1-12): ");
        int month = Integer.parseInt(sc.nextLine());
        System.out.println("Enter year: ");
        int year = Integer.parseInt(sc.nextLine());

        double totalRevenue = 0;
        int count = 0;

        System.out.println("\n Monthly Revenue Report (COMPLETED BOOKINGS) for " + String.format("%02d/%d", month, year));
        System.out.println("------------------------------------------------------------");

        for (Guest guest : listguests) {
            LocalDate checkInDate = guest.getCheckInDate();
            LocalDate checkOutDate = guest.getCheckOutDate();

            if (checkInDate == null || checkOutDate == null) continue;

            if (checkOutDate.isAfter(LocalDate.now())) continue;

            if (checkOutDate.getMonthValue() == month && checkOutDate.getYear() == year) {
                Room room = listRooms.stream()
                        .filter(r -> r.getRoomID().equalsIgnoreCase(guest.getRoomID()))
                        .findFirst()
                        .orElse(null);
                if (room != null) {
                    double amount = room.getDailyRate() * guest.getRentalDays();
                    totalRevenue += amount;
                    count++;
                    System.out.printf("Guest: %-20s | Room: %-6s | %s → %s | %d days | $%.2f\n",
                            guest.getFullName(), guest.getRoomID(),
                            guest.getCheckInDate(), guest.getCheckOutDate(),
                            guest.getRentalDays(), amount);
                }
            }
        }
        if (count == 0) {
            System.out.println("No completed bookings found for this month.");
        }
        System.out.println("------------------------------------------------------------");
        System.out.printf(" Completed bookings: %d\n", count);
        System.out.printf(" Total revenue      : $%.2f\n", totalRevenue);
        System.out.println("------------------------------------------------------------");
    }
}
