package nl.kooi.vehicle.api.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.kooi.vehicle.enums.VehicleType;

@AllArgsConstructor
@Getter
@JsonTypeInfo(
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        use = JsonTypeInfo.Id.NAME,
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BusDTO.class, name = "BUS"),
        @JsonSubTypes.Type(value = CarDTO.class, name = "CAR")
})
public abstract class VehicleDTO {
    private Long id;
    private VehicleType type;
    private String brand;
    private String model;
}
