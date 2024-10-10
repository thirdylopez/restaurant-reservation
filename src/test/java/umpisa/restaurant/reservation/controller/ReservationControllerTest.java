package umpisa.restaurant.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import umpisa.restaurant.reservation.model.ReservationDTO;
import umpisa.restaurant.reservation.services.ReservationService;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ReservationService reservationService;

    @Captor
    ArgumentCaptor<ReservationDTO> reservationDTOArgumentCaptor;

    ReservationDTO reservationDTO = ReservationDTO.builder()
            .customerName("Name")
            .preferredMethod("E")
            .email("name@email.com")
            .reservationDateTime(LocalDateTime.now().plusDays(2))
            .numberOfGuests(10)
            .build();

    ReservationDTO reservationDTOResult = ReservationDTO.builder()
            .id(1L)
            .version(1)
            .customerName("Name")
            .preferredMethod("E")
            .email("name@email.com")
            .reservationDateTime(LocalDateTime.now().plusDays(2))
            .numberOfGuests(10)
            .build();

    @Test
    void testCreateNewReservation() throws Exception {
        given(reservationService.saveNewReservation(any(ReservationDTO.class))).willReturn(reservationDTOResult);

        mockMvc.perform(post(ReservationController.RESERVATION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testCreateNewReservationNullCustomerName() throws Exception {
        ReservationDTO copy = reservationDTO;
        copy.setCustomerName(null);
        given(reservationService.saveNewReservation(any(ReservationDTO.class))).willReturn(reservationDTOResult);

        mockMvc.perform(post(ReservationController.RESERVATION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(copy)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testCreateNewReservationBlankCustomerName() throws Exception {
        ReservationDTO copy = reservationDTO;
        copy.setCustomerName("");
        given(reservationService.saveNewReservation(any(ReservationDTO.class))).willReturn(reservationDTOResult);

        mockMvc.perform(post(ReservationController.RESERVATION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(copy)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testCreateNewReservationWrongEmail() throws Exception {
        ReservationDTO copy = reservationDTO;
        copy.setEmail("test");
        given(reservationService.saveNewReservation(any(ReservationDTO.class))).willReturn(reservationDTOResult);

        mockMvc.perform(post(ReservationController.RESERVATION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(copy)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testCreateNewReservationBlankPreferredMethod() throws Exception {
        ReservationDTO copy = reservationDTO;
        copy.setPreferredMethod("");
        given(reservationService.saveNewReservation(any(ReservationDTO.class))).willReturn(reservationDTOResult);

        mockMvc.perform(post(ReservationController.RESERVATION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(copy)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testCreateNewReservationPastReservationDate() throws Exception {
        ReservationDTO copy = reservationDTO;
        copy.setReservationDateTime(LocalDateTime.now().minusDays(2));
        given(reservationService.saveNewReservation(any(ReservationDTO.class))).willReturn(reservationDTOResult);

        mockMvc.perform(post(ReservationController.RESERVATION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(copy)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testUpdateReservationById() throws Exception {

        given(reservationService.updateReservationById(any(), any())).willReturn(reservationDTOResult);

        mockMvc.perform(put(ReservationController.RESERVATION_PATH_ID, reservationDTOResult.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDTO)))
                .andExpect(status().isOk());

        verify(reservationService).updateReservationById(any(Long.class), any(ReservationDTO.class));
    }

    @Test
    void testUpdateReservationByIdCustomerName() throws Exception {
        ReservationDTO copy = reservationDTO;
        copy.setCustomerName(null);
        given(reservationService.updateReservationById(any(), any(ReservationDTO.class))).willReturn(reservationDTOResult);

        mockMvc.perform(post(ReservationController.RESERVATION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(copy)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testUpdateReservationByIdBlankCustomerName() throws Exception {
        ReservationDTO copy = reservationDTO;
        copy.setCustomerName("");
        given(reservationService.updateReservationById(any(), any(ReservationDTO.class))).willReturn(reservationDTOResult);

        mockMvc.perform(post(ReservationController.RESERVATION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(copy)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testUpdateReservationByIdWrongEmail() throws Exception {
        ReservationDTO copy = reservationDTO;
        copy.setEmail("test");
        given(reservationService.updateReservationById(any(), any(ReservationDTO.class))).willReturn(reservationDTOResult);

        mockMvc.perform(post(ReservationController.RESERVATION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(copy)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testUpdateReservationByIdBlankPreferredMethod() throws Exception {
        ReservationDTO copy = reservationDTO;
        copy.setPreferredMethod("");
        given(reservationService.updateReservationById(any(), any(ReservationDTO.class))).willReturn(reservationDTOResult);

        mockMvc.perform(post(ReservationController.RESERVATION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(copy)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testUpdateReservationByIdPastReservationDate() throws Exception {
        ReservationDTO copy = reservationDTO;
        copy.setReservationDateTime(LocalDateTime.now().minusDays(2));
        given(reservationService.updateReservationById(any(), any(ReservationDTO.class))).willReturn(reservationDTOResult);

        mockMvc.perform(post(ReservationController.RESERVATION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(copy)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


}
