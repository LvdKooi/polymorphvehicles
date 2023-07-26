package nl.kooi.vehicle.infrastructure.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.kooi.vehicle.enums.BusType;
import nl.kooi.vehicle.enums.VehicleType;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("BUS")
public class BusEntity extends VehicleEntity {
    @Enumerated(EnumType.STRING)
    private BusType busType;
    private int litersLuggageCapacity;
}
