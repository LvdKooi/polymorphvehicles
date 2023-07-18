package nl.kooi.vehicle.domain.service;

import nl.kooi.vehicle.exception.NotFoundException;
import nl.kooi.vehicle.infrastructure.VehicleRepository;
import nl.kooi.vehicle.mapper.MapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(VehicleServiceImpl.class)
@Import(MapperImpl.class)
class VehicleServiceImplTest {

    @Autowired
    private VehicleService service;

    @MockBean
    private VehicleRepository repository;

    @Test
    void findVehicleById_NotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThat(assertThrows(NotFoundException.class, () -> service.findVehicleById(1L)).getMessage()).isEqualTo("Vehicle with id 1 not found.");
    }

    @Test
    void deleteVehicleById_NotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThat(assertThrows(NotFoundException.class, () -> service.deleteVehicleById(1L)).getMessage()).isEqualTo("Vehicle with id 1 not found.");
    }
}