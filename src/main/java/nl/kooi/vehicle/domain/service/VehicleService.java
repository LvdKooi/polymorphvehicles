package nl.kooi.vehicle.domain.service;

import nl.kooi.vehicle.domain.Vehicle;
import nl.kooi.vehicle.enums.VehicleType;

import java.util.List;

public interface VehicleService {

    Vehicle saveVehicle(Vehicle vehicle);

    Vehicle findVehicleById(Long id);

    Vehicle updateVehicle(Vehicle vehicle);

    void deleteVehicleById(Long id);

    List<Vehicle> getAllVehicles();

    List<Vehicle> getAllVehiclesByType(VehicleType type);
}
