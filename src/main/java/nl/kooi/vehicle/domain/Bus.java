package nl.kooi.vehicle.domain;

import lombok.Getter;
import nl.kooi.vehicle.enums.BusType;
import nl.kooi.vehicle.enums.VehicleType;

@Getter
public class Bus extends Vehicle {
    private BusType busType;
    private int litersLuggageCapacity;


    public Bus(Long id, VehicleType type, String brand, String model, BusType busType, int litersLuggageCapacity) {
        super(id, type, brand, model);
        this.busType = busType;
        this.litersLuggageCapacity = litersLuggageCapacity;
    }
}
