package nl.kooi.vehicle.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.kooi.vehicle.enums.VehicleType;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class VehicleEntity extends BaseEntity {
    @Enumerated(value = EnumType.STRING)
    private VehicleType vehicleType;

    private String brand;

    private String model;
}
