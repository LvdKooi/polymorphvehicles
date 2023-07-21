package nl.kooi.vehicle.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.kooi.vehicle.api.dto.VehicleDTO;
import nl.kooi.vehicle.domain.service.VehicleService;
import nl.kooi.vehicle.enums.VehicleType;
import nl.kooi.vehicle.mapper.Mapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/vehicles")
public class VehicleApi {

    private final VehicleService service;
    private final Mapper mapper;

    @GetMapping
    public List<VehicleDTO> getAllVehicles(@RequestParam(required = false) VehicleType type) {
        return Optional.ofNullable(type)
                .map(service::getAllVehiclesByType)
                .orElseGet(service::getAllVehicles)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @PostMapping
    public VehicleDTO saveVehicle(@RequestBody VehicleDTO dto) {
        return mapper.map(service.saveVehicle(mapper.map(dto)));
    }

    @GetMapping("/{id}")
    public VehicleDTO getVehicleById(@PathVariable("id") Long id) {
        log.info("Received get request by id: {}", id);
        return mapper.map(service.findVehicleById(id));
    }

    @PutMapping("/{id}")
    public VehicleDTO updateVehicle(@PathVariable("id") Long id, @RequestBody VehicleDTO dto) {
        log.info("Received update for id {} and vehicle {}", id, dto);
        return mapper.map(service.updateVehicle(mapper.map(dto)));
    }

    @DeleteMapping("/{id}")
    public void deleteBydId(@PathVariable("id") Long id) {
        service.deleteVehicleById(id);
    }
}
