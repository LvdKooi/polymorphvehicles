package nl.kooi.vehicle.api.dto;

import nl.kooi.vehicle.enums.VehicleType;

public record VehicleDTO(Long id,
                         VehicleType type,
                         String brand,
                         String model) {
}
