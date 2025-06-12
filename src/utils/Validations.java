package utils;

import model.Guest;
import model.Room;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Validations {
    public static Scanner scanner = new Scanner(System.in);
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String inputNationID(List<Guest> guests) {
        while (true) {
            System.out.println("Enter Nation ID: ");
            String nationID = scanner.nextLine();
            if (!nationID.matches("\\d{12}")) {
                System.out.println("Invalid Nation ID");
                continue;
            }
            boolean exists = false;
            for (Guest guest : guests) {
                if (guest.getNationalID().equals(nationID)) {
                    exists = true;
                }
            }
            if (exists) {
                System.out.println("Invalid Nation ID");
                continue;
            }
            return nationID;
        }
    }

    public static String inputNationIDCancel() {
        while (true) {
            System.out.println("Enter Nation ID: ");
            String nationID = scanner.nextLine();
            if (!nationID.matches("\\d{12}")) {
                System.out.println("Invalid Nation ID");
                continue;
            }
            return nationID;
        }
    }

    public static Guest getGuestByNationalID(List<Guest> guests) {
        while (true) {
            System.out.println("Enter Nation ID: ");
            String nationID = scanner.nextLine();
            if (!nationID.matches("\\d{12}")) {
                System.out.println("Invalid Nation ID");
                continue;
            }
            Guest guest = guests.stream().filter(g -> g.getNationalID().equalsIgnoreCase(nationID)).findFirst().orElse(null);

            if (guest == null) {
                System.out.println("Guest with nation ID " + nationID + " not found");
            }
            return guest;
        }

    }

    public static List<Guest> getGuestsByNationalID(List<Guest> guests, String nationalID) {
        return guests.stream()
                .filter(g -> g.getNationalID().equalsIgnoreCase(nationalID)
                        && g.getCheckInDate() != null
                        && g.getCheckInDate().isAfter(LocalDate.now())).toList();
    }

    public static String inputFullName() {
        while (true) {
            System.out.println("Enter Full Name: ");
            String fullName = scanner.nextLine();
            if (fullName.isEmpty() || fullName.length() < 2 || fullName.length() > 25) {
                System.out.println("Invalid Full Name");
                continue;
            }
            return fullName;
        }
    }

    public static LocalDate inputBirthDay() {
        while (true) {
            System.out.println("Enter Birth Day: ");
            String birthDay = scanner.nextLine();
            try {
                LocalDate birthDate = LocalDate.parse(birthDay, formatter);
                LocalDate today = LocalDate.now();
                if (birthDate.isAfter(today)) {
                    System.out.println("ngay sinh khong phai la ngay trong tuong lai");
                    continue;
                }
                return birthDate;
            } catch (DateTimeException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static String inputGender() {
        String[] validGender = {"Male", "Female"};
        while (true) {
            System.out.println("Enter Gender: ");
            String gender = scanner.nextLine();
            for (String g : validGender) {
                if (g.equalsIgnoreCase(gender)) {
                    return gender;
                }
            }
            System.out.println("Invalid Gender");
        }
    }

    public static String inputPhoneNumber() {
        while (true) {
            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine().trim();

            if (!phone.matches("^(03[2-9]|07[06789]|08[1-5]|05[68]|059)\\d{7}$")) {
                System.out.println("Invalid phone number. Must be 10 digits starting with 0 (e.g., 0123456789).");
                continue;
            }

            return phone;
        }
    }

    public static LocalDate inputStartDate() {
        while (true) {
            System.out.println("Enter Start Date: ");
            String startDate = scanner.nextLine();
            try {
                LocalDate startDatefor = LocalDate.parse(startDate, formatter);
                LocalDate today = LocalDate.now();
                if (startDatefor.isBefore(today)) {
                    System.out.println("Phai nhap ngay trong tuong lai");
                    continue;
                }
                return startDatefor;
            } catch (DateTimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int inputRentalDate() {
        while (true) {
            System.out.println("Enter Rental Date: ");
            try {
                int rentalDate = Integer.parseInt(scanner.nextLine());
                if (rentalDate < 0) {
                    System.out.println("must be positive");
                    continue;
                }
                return rentalDate;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Rental Date");
            }

        }
    }

    public static String inputCotentantName() {
        while (true) {
            System.out.println("Enter Cotentant Name (Press Enter to skip): ");
            String cotentantName = scanner.nextLine();
            if (!cotentantName.isEmpty()) {
                if (cotentantName.length() < 2 || cotentantName.length() > 25) {
                    System.out.println("Invalid Cotentant Name");
                    continue;
                }
            }
            return cotentantName;
        }
    }
    // phong 1
    // khach 1 thue tu 4/5-6/5
    //khach 2 thue tu 7/5-8/5
// khach dang nhap. ngay checkin = 6/5, thue trong 8/5

    public static boolean isValiable(LocalDate checkinDate, int rentalDay, Room room) {
        LocalDate checkoutDate = checkinDate.plusDays(rentalDay);
        for (Guest g : room.getGuestsStay()) {
            LocalDate checkin = g.getCheckInDate();
            LocalDate checkout = g.getCheckOutDate();
            if (!(checkoutDate.isBefore(checkin) || checkinDate.isAfter(checkout.minusDays(1)))) {
                return false;
            }
        }
        return true;
    }


    public static Room selectRoom(List<Room> rooms, LocalDate checkinDate, int rentalDay) {
        Room selectedRoom = null;
        while (true) {
            System.out.println("Enter RoomID : ");
            String roomID = scanner.nextLine();
            if (roomID.isEmpty()) {
                System.out.println("Invalid RoomID");
                continue;
            }
            for (Room r : rooms) {
                if (r.getRoomID().equals(roomID)) {
                    selectedRoom = r;
                }
            }
            if (selectedRoom == null) {
                System.out.println("Room not found");
                continue;
            }
            if (!isValiable(checkinDate, rentalDay, selectedRoom)) {
                System.out.println("Phong nay da duoc thue, chon phong khac di ");
                continue;
            }
            break;
        }
        return selectedRoom;
    }


     public static Room getRoomByID(List<Room> rooms,String roomID) {
        return rooms.stream()
                .filter(r->r.getRoomID().equalsIgnoreCase(roomID))
                .findFirst()
                .orElse(null);
     }
}
