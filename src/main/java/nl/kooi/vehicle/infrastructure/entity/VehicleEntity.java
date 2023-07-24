package nl.kooi.vehicle.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.kooi.vehicle.enums.VehicleType;
import org.hibernate.annotations.DiscriminatorFormula;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("vehicle_type")
public abstract class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private VehicleType vehicleType;

    private String brand;

    private String model;
}
