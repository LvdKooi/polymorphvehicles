package nl.kooi.vehicle.mapper;

import nl.kooi.vehicle.api.dto.VehicleDTO;
import nl.kooi.vehicle.domain.Vehicle;
import nl.kooi.vehicle.infrastructure.entity.VehicleEntity;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface Mapper {

    VehicleDTO map(Vehicle model);

    Vehicle map(VehicleDTO model);

    @Mapping(target = "type", source = "vehicleType")
    Vehicle map(VehicleEntity entity);

    @Mapping(target = "vehicleType", source = "type")
    VehicleEntity mapToEntity(Vehicle model);
}
