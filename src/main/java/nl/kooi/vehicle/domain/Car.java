package nl.kooi.vehicle.domain;

import lombok.Getter;
import nl.kooi.vehicle.enums.BodyStyle;
import nl.kooi.vehicle.enums.VehicleType;

@Getter

public class Car extends Vehicle {
    private int numberOfDoors;
    private BodyStyle bodyStyle;

    public Car(Long id, VehicleType type, String brand, String model, int numberOfDoors, BodyStyle bodyStyle) {
        super(id, type, brand, model);
        this.numberOfDoors = numberOfDoors;
        this.bodyStyle = bodyStyle;
    }
}
