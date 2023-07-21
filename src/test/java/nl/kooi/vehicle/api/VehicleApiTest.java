package nl.kooi.vehicle.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.kooi.vehicle.api.dto.BusDTO;
import nl.kooi.vehicle.api.dto.CarDTO;
import nl.kooi.vehicle.api.dto.VehicleDTO;
import nl.kooi.vehicle.domain.Bus;
import nl.kooi.vehicle.domain.Car;
import nl.kooi.vehicle.domain.Vehicle;
import nl.kooi.vehicle.domain.service.VehicleService;
import nl.kooi.vehicle.exception.NotFoundException;
import nl.kooi.vehicle.mapper.MapperImpl;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static nl.kooi.vehicle.enums.BodyStyle.CONVERTIBLE;
import static nl.kooi.vehicle.enums.BusType.CITY;
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
    void testGetEndpoint_withoutQueryParam() throws Exception {
        when(vehicleService.getAllVehicles())
                .thenReturn(List.of(new Car(1L, CAR, "Volkswagen", "Golf", 4, CONVERTIBLE),
                        new Bus(2L, BUS, "DAF", "GTX760", CITY, 10000)));


        var result = mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var typeRef = new TypeReference<List<VehicleDTO>>() {
        };

        var resultAsDto = objectMapper.readValue(result, typeRef);

        assertThat(resultAsDto).isNotNull();
        assertThat(resultAsDto).hasSize(2);
        AssertionsForClassTypes.assertThat(resultAsDto.get(0)).isInstanceOf(CarDTO.class);
        assertThat(resultAsDto.get(0).getId()).isEqualTo(1L);
        assertThat(resultAsDto.get(0).getBrand()).isEqualTo("Volkswagen");
        assertThat(resultAsDto.get(0).getModel()).isEqualTo("Golf");
        assertThat(resultAsDto.get(0).getType()).isEqualTo(CAR);
        assertThat(((CarDTO) resultAsDto.get(0)).getBodyStyle()).isEqualTo(CONVERTIBLE);
        assertThat(((CarDTO) resultAsDto.get(0)).getNumberOfDoors()).isEqualTo(4);

        AssertionsForClassTypes.assertThat(resultAsDto.get(1)).isInstanceOf(BusDTO.class);
        assertThat(resultAsDto.get(1).getId()).isEqualTo(2L);
        assertThat(resultAsDto.get(1).getBrand()).isEqualTo("DAF");
        assertThat(resultAsDto.get(1).getModel()).isEqualTo("GTX760");
        assertThat(resultAsDto.get(1).getType()).isEqualTo(BUS);
        assertThat(((BusDTO) resultAsDto.get(1)).getBusType()).isEqualTo(CITY);
        assertThat(((BusDTO) resultAsDto.get(1)).getLitersLuggageCapacity()).isEqualTo(10000);

        verify(vehicleService, times(1)).getAllVehicles();
    }

    @Test
    void testGetEndpoint_withQueryParam() throws Exception {
        when(vehicleService.getAllVehiclesByType(CAR))
                .thenReturn(List.of(new Car(1L, CAR, "Volkswagen", "Golf", 4, CONVERTIBLE)));


        var result = mockMvc.perform(get("/vehicles?type=CAR"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var typeRef = new TypeReference<List<CarDTO>>() {
        };

        var resultAsDto = objectMapper.readValue(result, typeRef);

        assertThat(resultAsDto).isNotNull();
        assertThat(resultAsDto).hasSize(1);
        assertThat(resultAsDto.get(0).getId()).isEqualTo(1L);
        assertThat(resultAsDto.get(0).getBrand()).isEqualTo("Volkswagen");
        assertThat(resultAsDto.get(0).getModel()).isEqualTo("Golf");
        assertThat(resultAsDto.get(0).getType()).isEqualTo(CAR);
        assertThat(resultAsDto.get(0).getBodyStyle()).isEqualTo(CONVERTIBLE);
        assertThat(resultAsDto.get(0).getNumberOfDoors()).isEqualTo(4);


        verify(vehicleService, times(1)).getAllVehiclesByType(eq(CAR));
    }

    @Test
    void testGetByIdEndpoint() throws Exception {
        when(vehicleService.findVehicleById(1L))
                .thenReturn(new Car(1L, CAR, "Volkswagen", "Golf", 4, CONVERTIBLE));


        var result = mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var resultAsDto = objectMapper.readValue(result, CarDTO.class);

        assertThat(resultAsDto).isNotNull();
        assertThat(resultAsDto.getId()).isEqualTo(1L);
        assertThat(resultAsDto.getBrand()).isEqualTo("Volkswagen");
        assertThat(resultAsDto.getModel()).isEqualTo("Golf");
        assertThat(resultAsDto.getType()).isEqualTo(CAR);
        assertThat(resultAsDto.getNumberOfDoors()).isEqualTo(4);
        assertThat(resultAsDto.getBodyStyle()).isEqualTo(CONVERTIBLE);

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
                .thenReturn(new Bus(1L, BUS, "Brand", "Model", CITY, 10000));

        var dto = new BusDTO(1L, BUS, "Brand", "Model", CITY, 10000);

        var result = mockMvc.perform(put("/vehicles/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var resultAsDto = objectMapper.readValue(result, BusDTO.class);

        assertThat(resultAsDto).isNotNull();
        assertThat(resultAsDto.getId()).isEqualTo(1L);
        assertThat(resultAsDto.getBrand()).isEqualTo("Brand");
        assertThat(resultAsDto.getModel()).isEqualTo("Model");
        assertThat(resultAsDto.getType()).isEqualTo(BUS);
        assertThat(resultAsDto.getBusType()).isEqualTo(CITY);
        assertThat(resultAsDto.getLitersLuggageCapacity()).isEqualTo(10000);

        verify(vehicleService, times(1)).updateVehicle(any(Vehicle.class));
    }

    @Test
    void testPostEndpoint() throws Exception {
        when(vehicleService.saveVehicle(any(Vehicle.class)))
                .thenReturn(new Car(1L, CAR, "Volkswagen", "Golf", 4, CONVERTIBLE));

        var dto = new CarDTO(null, CAR, "Volkswagen", "Golf", 4, CONVERTIBLE);

        var result = mockMvc.perform(post("/vehicles").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var resultAsDto = objectMapper.readValue(result, CarDTO.class);

        assertThat(resultAsDto).isNotNull();
        assertThat(resultAsDto.getId()).isEqualTo(1L);
        assertThat(resultAsDto.getBrand()).isEqualTo("Volkswagen");
        assertThat(resultAsDto.getModel()).isEqualTo("Golf");
        assertThat(resultAsDto.getType()).isEqualTo(CAR);
        assertThat(resultAsDto.getBodyStyle()).isEqualTo(CONVERTIBLE);
        assertThat(resultAsDto.getNumberOfDoors()).isEqualTo(4);

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