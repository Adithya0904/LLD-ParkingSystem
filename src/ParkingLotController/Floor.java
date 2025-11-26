package ParkingLotController;

import ParkingSpots.ParkingSpot;
import VehicleFactoryPattern.Vehicle;

import java.util.List;

public class Floor {
    private final int floorNumber;
    private final List<ParkingSpot> parkingSpots;

    public Floor(int floorNumber, List<ParkingSpot> parkingSpots) {
        this.floorNumber = floorNumber;
        this.parkingSpots = parkingSpots;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    // Find an available spot on this floor for the given vehicle type
    public ParkingSpot findAvailableSpot(String vehicleType) {
        for (ParkingSpot spot : parkingSpots) {
            if (!spot.isOccupied() && spot.getSpotType().equals(vehicleType)) {
                return spot;
            }
        }
        return null;
    }

    // Park vehicle on this floor
    public ParkingSpot parkVehicle(Vehicle vehicle) {
        ParkingSpot spot = findAvailableSpot(vehicle.getVehicleType());
        if (spot != null) {
            spot.parkVehicle(vehicle);
            System.out.println(
                    "Vehicle parked on floor " + floorNumber +
                            " in spot: " + spot.getSpotNumber());
        }
        return spot;
    }

    // Vacate a spot on this floor
    public void vacateSpot(ParkingSpot spot, Vehicle vehicle) {
        if (spot != null && spot.isOccupied()
                && spot.getVehicle().equals(vehicle)) {
            spot.vacate();
            System.out.println(
                    vehicle.getVehicleType() +
                            " vacated floor " + floorNumber +
                            ", spot: " + spot.getSpotNumber());
        } else {
            System.out.println("Invalid operation on floor " + floorNumber
                    + "! Either the spot is already vacant or the vehicle does not match.");
        }
    }
}


