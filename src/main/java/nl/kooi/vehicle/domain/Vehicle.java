package nl.kooi.vehicle.domain;

import nl.kooi.vehicle.enums.VehicleType;

public record Vehicle(Long id,
                      VehicleType type,
                      String brand,
                      String model) {

}
