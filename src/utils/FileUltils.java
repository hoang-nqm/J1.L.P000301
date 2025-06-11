package utils;

import model.Guest;
import model.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileUltils {
    public static List<Room> importRoomData(String filePath) {
        List<Room> roomList = new ArrayList<>();
        Set<String> roomIDs = new HashSet<>();
        int successCount = 0;
        int failedCount = 0;
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length != 6) {
                    failedCount++;
                    continue;
                }
                String roomID = parts[0].trim();
                String roomName = parts[1].trim();
                String roomType = parts[2].trim();
                String rateStr = parts[3].trim();
                String capacityStr = parts[4].trim();
                String furniture = parts[5].trim();
                if (roomIDs.contains(roomID)) {
                    failedCount++;
                    continue;
                }

                try {
                    double dailyRate = Double.parseDouble(rateStr);
                    int capacity = Integer.parseInt(capacityStr);

                    if (dailyRate <= 0 || capacity <= 0) {
                        failedCount++;
                        continue;
                    }

                    Room room = new Room(roomID, roomName, roomType, dailyRate, capacity, furniture);
                    roomList.add(room);
                    roomIDs.add(roomID);
                    successCount++;
                } catch (NumberFormatException e) {
                    failedCount++;
                }
            }

            System.out.println(successCount + " rooms successfully loaded.");
            System.out.println(failedCount + " entries failed.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return roomList;
    }

    public static void saveGuestListToFile(List<Guest> guestList, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(guestList);
            System.out.println("Guest List saved successfully to : " + filePath);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Guest> loadGuestListFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Guest> guestList = (List<Guest>) ois.readObject();
            return guestList != null ? guestList : new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {

            return new ArrayList<>();
        }
    }

    public static void saveRoomListToFile(List<Room> roomList, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(roomList);
            System.out.println("Room List saved successfully to : " + filePath);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Room> loadRoomListFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Room> roomList = (List<Room>) ois.readObject();
            return roomList != null ? roomList : new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {

            return new ArrayList<>();
        }
    }
}
