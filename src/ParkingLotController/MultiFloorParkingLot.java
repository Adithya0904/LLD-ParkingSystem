package ParkingLotController;

import ParkingSpots.ParkingSpot;
import VehicleFactoryPattern.Vehicle;

import java.util.List;

public class MultiFloorParkingLot {
    private final List<Floor> floors;

    // Composite over multiple floors
    public MultiFloorParkingLot(List<Floor> floors) {
        this.floors = floors;
    }

    // Simple allocation strategy: first floor with a free spot wins
    public ParkingSpot parkVehicle(Vehicle vehicle) {
        String vehicleType = vehicle.getVehicleType();
        for (Floor floor : floors) {
            ParkingSpot spot = floor.findAvailableSpot(vehicleType);
            if (spot != null) {
                spot.parkVehicle(vehicle);
                return spot;
            }
        }
        System.out.println(
                "No parking spots available for " + vehicleType + " on any floor!");
        return null;
    }

    // Vacate by searching the floors for that spot instance
    public void vacateSpot(ParkingSpot spot, Vehicle vehicle) {
        for (Floor floor : floors) {
            if (floor.getParkingSpots().contains(spot)) {
                floor.vacateSpot(spot, vehicle);
                return;
            }
        }
        System.out.println("Invalid operation! Spot not found in any floor.");
    }

    // Optional helper: get a spot by number across all floors
    public ParkingSpot getSpotByNumber(int spotNumber) {
        for (Floor floor : floors) {
            for (ParkingSpot spot : floor.getParkingSpots()) {
                if (spot.getSpotNumber() == spotNumber) {
                    return spot;
                }
            }
        }
        return null;
    }

    public List<Floor> getFloors() {
        return floors;
    }
}


