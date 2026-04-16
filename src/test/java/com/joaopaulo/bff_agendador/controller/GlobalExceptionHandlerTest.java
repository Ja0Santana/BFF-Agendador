package com.joaopaulo.bff_agendador.controller;

import com.joaopaulo.bff_agendador.infrastructure.exceptions.BusinessException;
import com.joaopaulo.bff_agendador.infrastructure.exceptions.ConflictException;
import com.joaopaulo.bff_agendador.infrastructure.exceptions.ResourceNotFoundException;
import com.joaopaulo.bff_agendador.infrastructure.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ExceptionTestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Deve retornar 404 quando ResourceNotFoundException for lançada")
    void handleResourceNotFoundException() throws Exception {
        mockMvc.perform(get("/test/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Não encontrado"))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    @DisplayName("Deve retornar 409 quando ConflictException for lançada")
    void handleConflictException() throws Exception {
        mockMvc.perform(get("/test/conflict"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Conflito detectado"))
                .andExpect(jsonPath("$.error").value("Conflict"));
    }

    @Test
    @DisplayName("Deve retornar 401 quando UnauthorizedException for lançada")
    void handleUnauthorizedException() throws Exception {
        mockMvc.perform(get("/test/unauthorized"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.message").value("Acesso negado"))
                .andExpect(jsonPath("$.error").value("Unauthorized"));
    }

    @Test
    @DisplayName("Deve retornar 417 quando BusinessException for lançada")
    void handleBusinessException() throws Exception {
        mockMvc.perform(get("/test/business"))
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.status").value(417))
                .andExpect(jsonPath("$.message").value("Erro de negócio"))
                .andExpect(jsonPath("$.error").value("Expectation Failed"));
    }

    @RestController
    static class ExceptionTestController {
        @GetMapping("/test/not-found")
        public void throwNotFound() {
            throw new ResourceNotFoundException("Não encontrado");
        }

        @GetMapping("/test/conflict")
        public void throwConflict() {
            throw new ConflictException("Conflito detectado");
        }

        @GetMapping("/test/unauthorized")
        public void throwUnauthorized() {
            throw new UnauthorizedException("Acesso negado");
        }

        @GetMapping("/test/business")
        public void throwBusiness() {
            throw new BusinessException("Erro de negócio");
        }
    }
}
