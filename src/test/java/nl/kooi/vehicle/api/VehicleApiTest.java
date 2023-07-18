package nl.kooi.vehicle.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.kooi.vehicle.api.dto.VehicleDTO;
import nl.kooi.vehicle.domain.Vehicle;
import nl.kooi.vehicle.domain.service.VehicleService;
import nl.kooi.vehicle.exception.NotFoundException;
import nl.kooi.vehicle.mapper.MapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static nl.kooi.vehicle.enums.VehicleType.BUS;
import static nl.kooi.vehicle.enums.VehicleType.CAR;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({VehicleApi.class, ExceptionHandler.class})
@Import({MapperImpl.class})
class VehicleApiTest {

    @MockBean
    private VehicleService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetEndpoint() throws Exception {
        when(vehicleService.getAllVehicles())
                .thenReturn(List.of(new Vehicle(1L, CAR, "Volkswagen", "Golf"),
                        new Vehicle(2L, BUS, "DAF", "GTX760")));


        var result = mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var typeRef = new TypeReference<List<VehicleDTO>>() {
        };

        var resultAsDto = objectMapper.readValue(result, typeRef);

        assertThat(resultAsDto).isNotNull();
        assertThat(resultAsDto).hasSize(2);
        assertThat(resultAsDto.get(0).id()).isEqualTo(1L);
        assertThat(resultAsDto.get(0).brand()).isEqualTo("Volkswagen");
        assertThat(resultAsDto.get(0).model()).isEqualTo("Golf");
        assertThat(resultAsDto.get(0).type()).isEqualTo(CAR);
        assertThat(resultAsDto.get(1).id()).isEqualTo(2L);
        assertThat(resultAsDto.get(1).brand()).isEqualTo("DAF");
        assertThat(resultAsDto.get(1).model()).isEqualTo("GTX760");
        assertThat(resultAsDto.get(1).type()).isEqualTo(BUS);

        verify(vehicleService, times(1)).getAllVehicles();
    }

    @Test
    void testGetByIdEndpoint() throws Exception {
        when(vehicleService.findVehicleById(1L))
                .thenReturn(new Vehicle(1L, CAR, "Volkswagen", "Golf"));


        var result = mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var resultAsDto = objectMapper.readValue(result, VehicleDTO.class);

        assertThat(resultAsDto).isNotNull();
        assertThat(resultAsDto.id()).isEqualTo(1L);
        assertThat(resultAsDto.brand()).isEqualTo("Volkswagen");
        assertThat(resultAsDto.model()).isEqualTo("Golf");
        assertThat(resultAsDto.type()).isEqualTo(CAR);

        verify(vehicleService, times(1)).findVehicleById(1L);
    }

    @Test
    void testGetByIdEndpoint_NotFound() throws Exception {
        when(vehicleService.findVehicleById(1L))
                .thenThrow(new NotFoundException("Vehicle not found"));


        var result = mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        var resultAsDto = objectMapper.readValue(result, ProblemDetail.class);

        assertThat(resultAsDto).isNotNull();
        assertThat(resultAsDto.getDetail()).isEqualTo("Vehicle not found");
        assertThat(resultAsDto.getTitle()).isEqualTo("Not Found");
        assertThat(resultAsDto.getStatus()).isEqualTo(404);
    }


    @Test
    void testPutEndpoint() throws Exception {
        when(vehicleService.updateVehicle(any(Vehicle.class)))
                .thenReturn(new Vehicle(1L, CAR, "Volkswagen", "Golf"));

        var dto = new VehicleDTO(1L, CAR, "Volkswagen", "Golf");

        var result = mockMvc.perform(put("/vehicles/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var resultAsDto = objectMapper.readValue(result, VehicleDTO.class);

        assertThat(resultAsDto).isNotNull();
        assertThat(resultAsDto.id()).isEqualTo(1L);
        assertThat(resultAsDto.brand()).isEqualTo("Volkswagen");
        assertThat(resultAsDto.model()).isEqualTo("Golf");
        assertThat(resultAsDto.type()).isEqualTo(CAR);

        verify(vehicleService, times(1)).updateVehicle(any(Vehicle.class));
    }

    @Test
    void testPostEndpoint() throws Exception {
        when(vehicleService.saveVehicle(any(Vehicle.class)))
                .thenReturn(new Vehicle(1L, CAR, "Volkswagen", "Golf"));

        var dto = new VehicleDTO(null, CAR, "Volkswagen", "Golf");

        var result = mockMvc.perform(post("/vehicles").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var resultAsDto = objectMapper.readValue(result, VehicleDTO.class);

        assertThat(resultAsDto).isNotNull();
        assertThat(resultAsDto.id()).isEqualTo(1L);
        assertThat(resultAsDto.brand()).isEqualTo("Volkswagen");
        assertThat(resultAsDto.model()).isEqualTo("Golf");
        assertThat(resultAsDto.type()).isEqualTo(CAR);

        verify(vehicleService, times(1)).saveVehicle(any(Vehicle.class));
    }

    @Test
    void testDeleteEndpoint() throws Exception {
        doNothing().when(vehicleService).deleteVehicleById(anyLong());

        mockMvc.perform(delete("/vehicles/1"))
                .andExpect(status().isOk());

        verify(vehicleService, times(1)).deleteVehicleById(anyLong());
    }
}