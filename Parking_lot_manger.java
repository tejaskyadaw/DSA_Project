
import java.util.*;

class Vehicle {
    String number;
    String owner;

    Vehicle(String number, String owner) {
        this.number = number;
        this.owner = owner;
    }

    public String toString() {
        return "Vehicle: " + number + " | Owner: " + owner;
    }
}

public class Parking_lot_manger {

    static Stack<Vehicle> parking = new Stack<>();
    static Queue<Vehicle> waiting = new LinkedList<>();

    static HashMap<String, Vehicle> vehicleMap = new HashMap<>();

    static int capacity = 3;

    // Add vehicle
    static void addVehicle(String number, String owner) {

        if (vehicleMap.containsKey(number)) {
            System.out.println("Vehicle already registered!");
            return;
        }

        Vehicle v = new Vehicle(number, owner);
        vehicleMap.put(number, v);

        if (parking.size() < capacity) {
            parking.push(v);
            System.out.println("Vehicle parked successfully!");
        } else {
            waiting.add(v);
            System.out.println("Parking full! Vehicle added to waiting queue.");
        }
    }

    // Remove vehicle
    static void removeVehicle(String number) {

        if (!vehicleMap.containsKey(number)) {
            System.out.println("Vehicle not found!");
            return;
        }

        if (!parking.contains(vehicleMap.get(number))) {
            System.out.println("Vehicle is in waiting queue, not parked.");
            return;
        }

        Stack<Vehicle> temp = new Stack<>();
        boolean found = false;

        while (!parking.isEmpty()) {
            Vehicle v = parking.pop();

            if (v.number.equals(number)) {
                found = true;
                System.out.println("Vehicle removed: " + v.number);
            } else {
                temp.push(v);
            }
        }

        while (!temp.isEmpty()) {
            parking.push(temp.pop());
        }

        if (found) {
            vehicleMap.remove(number);

            // Move first waiting vehicle into parking
            if (!waiting.isEmpty()) {
                Vehicle next = waiting.poll();
                parking.push(next);
                System.out.println("Waiting vehicle parked: " + next.number);
            }
        }
    }

    // Search vehicle
    static void searchVehicle(String number) {

        Vehicle v = vehicleMap.get(number);

        if (v != null) {
            System.out.println("Vehicle Found!");
            System.out.println(v);
            System.out.println(
                parking.contains(v) ? "Status: Parked" : "Status: Waiting"
            );
        } else {
            System.out.println("Vehicle not found!");
        }
    }

    // Display parked vehicles
    static void displayParking() {

        System.out.println("\n--- Parked Vehicles ---");

        if (parking.isEmpty()) {
            System.out.println("No vehicles parked.");
            return;
        }

        for (Vehicle v : parking) {
            System.out.println(v);
        }
    }

    // Display waiting vehicles
    static void displayWaiting() {

        System.out.println("\n--- Waiting Vehicles ---");

        if (waiting.isEmpty()) {
            System.out.println("No waiting vehicles.");
            return;
        }

        for (Vehicle v : waiting) {
            System.out.println(v);
        }
    }

    // Main menu
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== PARKING LOT MANAGER =====");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Search Vehicle");
            System.out.println("4. Display Parking");
            System.out.println("5. Display Waiting Queue");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter vehicle number: ");
                    String number = sc.nextLine();

                    System.out.print("Enter owner name: ");
                    String owner = sc.nextLine();

                    addVehicle(number, owner);
                    break;

                case 2:
                    System.out.print("Enter vehicle number to remove: ");
                    String remove = sc.nextLine();

                    removeVehicle(remove);
                    break;

                case 3:
                    System.out.print("Enter vehicle number to search: ");
                    String search = sc.nextLine();

                    searchVehicle(search);
                    break;

                case 4:
                    displayParking();
                    break;

                case 5:
                    displayWaiting();
                    break;

                case 6:
                    System.out.println("Thank you for using Parking Lot Manager!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
