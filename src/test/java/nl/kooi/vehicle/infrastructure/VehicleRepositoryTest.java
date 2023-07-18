package nl.kooi.vehicle.infrastructure;

import nl.kooi.vehicle.enums.VehicleType;
import nl.kooi.vehicle.infrastructure.entity.VehicleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository repository;

    @Test
    void findAllByVehicleType() {

        repository.saveAll(List.of(createVehicleOfType(VehicleType.CAR),
                createVehicleOfType(VehicleType.CAR),
                createVehicleOfType(VehicleType.CAR),
                createVehicleOfType(VehicleType.BUS)
        ));


        var result = repository.findAllByVehicleType(VehicleType.CAR);

        assertThat(result).hasSize(3);

        assertThat(result.stream().allMatch(entity -> entity.getVehicleType() == VehicleType.CAR)).isTrue();
    }

    private static VehicleEntity createVehicleOfType(VehicleType type) {
        return new VehicleEntity(null, type, type.name() + "brand", type.name() + "model");

    }
}