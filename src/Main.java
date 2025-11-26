import CommonEnum.DurationType;
import FareStrategyPattern.ConcreteStrategies.BasicHourlyRateStrategy;
import FareStrategyPattern.ConcreteStrategies.PremiumRateStrategy;
import FareStrategyPattern.ParkingFeeStrategy;
import ParkingLotController.Floor;
import ParkingLotController.MultiFloorParkingLot;
import ParkingSpots.ConcreteParkingSpots.BikeParkingSpot;
import ParkingSpots.ConcreteParkingSpots.CarParkingSpot;
import ParkingSpots.ParkingSpot;
import PaymentStrategyPattern.ConcretePaymentStrategies.CashPayment;
import PaymentStrategyPattern.ConcretePaymentStrategies.CreditCardPayment;
import PaymentStrategyPattern.PaymentStrategy;
import VehicleFactoryPattern.Vehicle;
import VehicleFactoryPattern.VehicleFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize parking spots for Floor 1
        List<ParkingSpot> floor1Spots = new ArrayList<>();
        floor1Spots.add(new CarParkingSpot(1, "Car"));
        floor1Spots.add(new BikeParkingSpot(2, "Bike"));

        // Initialize parking spots for Floor 2
        List<ParkingSpot> floor2Spots = new ArrayList<>();
        floor2Spots.add(new CarParkingSpot(3, "Car"));
        floor2Spots.add(new BikeParkingSpot(4, "Bike"));

        // Create floors
        Floor floor1 = new Floor(1, floor1Spots);
        Floor floor2 = new Floor(2, floor2Spots);

        List<Floor> floors = new ArrayList<>();
        floors.add(floor1);
        floors.add(floor2);

        // Initialize multi-floor parking lot (Composite pattern)
        MultiFloorParkingLot multiFloorParkingLot = new MultiFloorParkingLot(floors);
        // Create fee strategies
        ParkingFeeStrategy basicHourlyRateStrategy = new BasicHourlyRateStrategy();
        ParkingFeeStrategy premiumRateStrategy = new PremiumRateStrategy();
        // Create vehicles using Factory Pattern with fee strategies
        Vehicle car1 = VehicleFactory.createVehicle("Car", "CAR123", basicHourlyRateStrategy);
        Vehicle car2 = VehicleFactory.createVehicle("Car", "CAR345", basicHourlyRateStrategy);

        Vehicle bike1 = VehicleFactory.createVehicle("Bike", "BIKE456", premiumRateStrategy);
        Vehicle bike2 = VehicleFactory.createVehicle("Bike", "BIKE123", premiumRateStrategy);


        // Park vehicles
        ParkingSpot carSpot = multiFloorParkingLot.parkVehicle(car1);
        ParkingSpot bikeSpot = multiFloorParkingLot.parkVehicle(bike1);

        ParkingSpot carSpot2 = multiFloorParkingLot.parkVehicle(car2);
        ParkingSpot bikeSpot2 = multiFloorParkingLot.parkVehicle(bike2);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select payment method for your vehicle:");
        System.out.println("1. Credit Card");
        System.out.println("2. Cash");
        int paymentMethod = scanner.nextInt();
        // Process payments using Strategy Patterns
        if (carSpot != null) {
            // Calculate fee using the specific strategy for the vehicle
            double carFee = car1.calculateFee(2, DurationType.HOURS);
            PaymentStrategy carPaymentStrategy =
                    getPaymentStrategy(paymentMethod);
            carPaymentStrategy.processPayment(carFee);
            multiFloorParkingLot.vacateSpot(carSpot, car1);
        }
        if (bikeSpot != null) {
            // Calculate fee using the specific strategy for the vehicle
            double bikeFee = bike1.calculateFee(3, DurationType.HOURS);
            PaymentStrategy bikePaymentStrategy =
                    getPaymentStrategy(paymentMethod);
            bikePaymentStrategy.processPayment(bikeFee);
            multiFloorParkingLot.vacateSpot(bikeSpot, bike1);
        }

        scanner.close();
    }
    private static PaymentStrategy getPaymentStrategy(
            int paymentMethod) {
        switch (paymentMethod) {
            case 1:
                return new CreditCardPayment();
            case 2:
                return new CashPayment();
            default:
                System.out.println("Invalid choice! Default to Credit card payment.");
                return new CreditCardPayment();
        }
    }
    }

    /*

Output :

Vehicle parked successfully in spot: 1
Vehicle parked successfully in spot: 3
Vehicle parked successfully in spot: 2
Vehicle parked successfully in spot: 4
Select payment method for your vehicle:
1. Credit Card
2. Cash
1
Processing credit card payment of $20.0
Car vacated the spot: 1
Processing credit card payment of $24.0
Bike vacated the spot: 3

Process finished with exit code 0

     */