package nl.kooi.vehicle.api.dto;

import lombok.Getter;
import nl.kooi.vehicle.enums.BusType;
import nl.kooi.vehicle.enums.VehicleType;


@Getter
public class BusDTO extends VehicleDTO {

    private BusType busType;
    private int litersLuggageCapacity;

    public BusDTO(Long id, VehicleType type, String brand, String model, BusType busType, int litersLuggageCapacity) {
        super(id, type, brand, model);
        this.busType = busType;
        this.litersLuggageCapacity = litersLuggageCapacity;
    }
}
