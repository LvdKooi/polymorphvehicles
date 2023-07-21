package nl.kooi.vehicle.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.kooi.vehicle.enums.BodyStyle;
import nl.kooi.vehicle.enums.VehicleType;

@Entity
@Getter
@Setter
public class CarEntity extends VehicleEntity {
    private int numberOfDoors;
    @Enumerated(EnumType.STRING)
    private BodyStyle bodyStyle;

}
