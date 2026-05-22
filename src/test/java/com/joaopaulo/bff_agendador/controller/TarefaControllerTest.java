package com.joaopaulo.bff_agendador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaopaulo.bff_agendador.business.TarefaService;
import com.joaopaulo.bff_agendador.business.dto.in.TarefaDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.out.TarefaDTOresponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TarefaController.class)
@SuppressWarnings("null")
class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TarefaService tarefaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve gravar tarefa via POST")
    void deveGravarTarefa() throws Exception {
        TarefaDTOrequest request = TarefaDTOrequest.builder().nomeTarefa("Teste").build();
        TarefaDTOresponse response = TarefaDTOresponse.builder().id("1").build();

        when(tarefaService.gravarTarefa(any(), any())).thenReturn(response);

        mockMvc.perform(post("/tarefas")
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @DisplayName("Deve buscar tarefas por período")
    void deveBuscarPorPeriodo() throws Exception {
        when(tarefaService.buscarListaTarefasPorPeriodo(any(), any(), any()))
                .thenReturn(List.of(TarefaDTOresponse.builder().id("1").build()));

        mockMvc.perform(get("/tarefas/eventos")
                .param("dataInicial", LocalDateTime.now().toString())
                .param("dataFinal", LocalDateTime.now().plusHours(1).toString())
                .header("Authorization", "Bearer token"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve buscar tarefas por email")
    void deveBuscarPorEmail() throws Exception {
        when(tarefaService.buscarListaTarefasPorEmail(any()))
                .thenReturn(List.of(TarefaDTOresponse.builder().id("1").build()));

        mockMvc.perform(get("/tarefas")
                .header("Authorization", "Bearer token"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve deletar tarefa por ID")
    void deveDeletarTarefa() throws Exception {
        mockMvc.perform(delete("/tarefas")
                .param("id", "1")
                .header("Authorization", "Bearer token"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve alterar status de notificação")
    void deveAlterarStatus() throws Exception {
        when(tarefaService.alterarStatusDeNotificacaoTarefa(any(), any(), any()))
                .thenReturn(TarefaDTOresponse.builder().id("1").build());

        mockMvc.perform(patch("/tarefas")
                .param("status", "VENCIDA")
                .param("id", "1")
                .header("Authorization", "Bearer token"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve alterar tarefa via PUT")
    void deveAlterarTarefa() throws Exception {
        TarefaDTOrequest request = new TarefaDTOrequest();
        when(tarefaService.alterarTarefa(any(), any(), any()))
                .thenReturn(TarefaDTOresponse.builder().id("1").build());

        mockMvc.perform(put("/tarefas")
                .param("id", "1")
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}


