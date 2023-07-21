package nl.kooi.vehicle.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.kooi.vehicle.enums.VehicleType;

@RequiredArgsConstructor
@Getter
public class VehicleDTO {
    private final Long id;
    private final VehicleType type;
    private final String brand;
    private final String model;
}
