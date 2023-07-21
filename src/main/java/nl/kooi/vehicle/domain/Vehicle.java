package nl.kooi.vehicle.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.kooi.vehicle.enums.VehicleType;

@Data
@AllArgsConstructor
public abstract class Vehicle {
    private Long id;
    private VehicleType type;
    private String brand;
    private String model;
}
