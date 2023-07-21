package nl.kooi.vehicle.api.dto;

import lombok.Getter;
import nl.kooi.vehicle.enums.BodyStyle;
import nl.kooi.vehicle.enums.VehicleType;

@Getter
public class CarDTO extends VehicleDTO {

    private int numberOfDoors;
    private BodyStyle bodyStyle;

    public CarDTO(Long id, VehicleType type, String brand, String model, int numberOfDoors, BodyStyle bodyStyle) {
        super(id, type, brand, model);
        this.numberOfDoors = numberOfDoors;
        this.bodyStyle = bodyStyle;
    }
}
