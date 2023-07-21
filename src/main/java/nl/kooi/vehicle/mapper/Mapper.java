package nl.kooi.vehicle.mapper;

import nl.kooi.vehicle.api.dto.BusDTO;
import nl.kooi.vehicle.api.dto.CarDTO;
import nl.kooi.vehicle.api.dto.VehicleDTO;
import nl.kooi.vehicle.domain.Bus;
import nl.kooi.vehicle.domain.Car;
import nl.kooi.vehicle.domain.Vehicle;
import nl.kooi.vehicle.infrastructure.entity.BusEntity;
import nl.kooi.vehicle.infrastructure.entity.CarEntity;
import nl.kooi.vehicle.infrastructure.entity.VehicleEntity;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface Mapper {

    default VehicleDTO map(Vehicle model) {

        if (model instanceof Bus b) {
            return mapBus(b);
        }

        if (model instanceof Car c) {
            return mapCar(c);
        }

        return null;
    }

    BusDTO mapBus(Bus vehicle);

    CarDTO mapCar(Car vehicle);

    default Vehicle map(VehicleDTO dto) {
        if (dto instanceof BusDTO b) {
            return mapBus(b);
        }

        if (dto instanceof CarDTO c) {
            return mapCar(c);
        }

        return null;
    }

    Car mapCar(CarDTO vehicle);

    Bus mapBus(BusDTO vehicle);


    default Vehicle map(VehicleEntity entity) {
        if (entity instanceof BusEntity b) {
            return mapBus(b);
        }

        if (entity instanceof CarEntity c) {
            return mapCar(c);
        }

        return null;
    }

    default VehicleEntity mapToVehicleEntity(Vehicle model) {
        if (model instanceof Bus b) {
            return mapBusToEntity(b);
        }

        if (model instanceof Car c) {
            return mapCarToEntity(c);
        }

        return null;
    }


    @Mapping(target = "type", source = "vehicleType")
    Car mapCar(CarEntity entity);

    @Mapping(target = "vehicleType", source = "type")
    CarEntity mapCarToEntity(Car model);

    @Mapping(target = "type", source = "vehicleType")
    Bus mapBus(BusEntity entity);

    @Mapping(target = "vehicleType", source = "type")
    BusEntity mapBusToEntity(Bus model);
}
