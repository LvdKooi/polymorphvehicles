package nl.kooi.vehicle.domain.service;

import lombok.RequiredArgsConstructor;
import nl.kooi.vehicle.domain.Vehicle;
import nl.kooi.vehicle.enums.VehicleType;
import nl.kooi.vehicle.exception.NotFoundException;
import nl.kooi.vehicle.infrastructure.VehicleRepository;
import nl.kooi.vehicle.mapper.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;
    private final Mapper mapper;

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        return mapper.map(repository.save(mapper.mapToVehicleEntity(vehicle)));
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicle findVehicleById(Long id) {
        return repository.findById(id)
                .map(mapper::map)
                .orElseThrow(() -> NotFoundException.vehicleNotFound(id));
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        findVehicleById(vehicle.getId());
        return mapper.map(repository.save(mapper.mapToVehicleEntity(vehicle)));
    }

    @Override
    public void deleteVehicleById(Long id) {
        findVehicleById(1L);
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles() {
        return repository.findAll().stream().map(mapper::map).toList();
    }

    @Override
    public List<Vehicle> getAllVehiclesByType(VehicleType type) {
        return repository.findAllByVehicleType(type).stream().map(mapper::map).toList();
    }
}
