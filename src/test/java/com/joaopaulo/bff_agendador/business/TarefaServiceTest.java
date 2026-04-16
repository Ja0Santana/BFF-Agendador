package com.joaopaulo.bff_agendador.business;

import com.joaopaulo.bff_agendador.business.dto.in.TarefaDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.out.TarefaDTOresponse;
import com.joaopaulo.bff_agendador.infrastructure.client.TarefaClient;
import com.joaopaulo.bff_agendador.infrastructure.enums.StatusNotificacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaClient tarefaClient;

    @InjectMocks
    private TarefaService tarefaService;

    private final String token = "Bearer token";

    @Test
    @DisplayName("Deve gravar tarefa e alterar status para DESATIVADA se notificar for falso")
    void deveGravarTarefaComStatusDesativada() {
        TarefaDTOrequest request = TarefaDTOrequest.builder().notificar(false).build();
        TarefaDTOresponse response = TarefaDTOresponse.builder().id("123").build();

        when(tarefaClient.gravarTarefa(any(), anyString())).thenReturn(response);
        when(tarefaClient.alterarStatusDeNotificacaoTarefa(StatusNotificacao.DESATIVADA, "123", token))
                .thenReturn(response);

        TarefaDTOresponse result = tarefaService.gravarTarefa(request, token);

        assertThat(result).isNotNull();
        verify(tarefaClient).alterarStatusDeNotificacaoTarefa(StatusNotificacao.DESATIVADA, "123", token);
    }

    @Test
    @DisplayName("Deve gravar tarefa normalmente se notificar for verdadeiro")
    void deveGravarTarefaNormalmente() {
        TarefaDTOrequest request = TarefaDTOrequest.builder().notificar(true).build();
        TarefaDTOresponse response = TarefaDTOresponse.builder().id("123").build();

        when(tarefaClient.gravarTarefa(any(), anyString())).thenReturn(response);

        TarefaDTOresponse result = tarefaService.gravarTarefa(request, token);

        assertThat(result).isNotNull();
        verify(tarefaClient, never()).alterarStatusDeNotificacaoTarefa(any(), any(), any());
    }

    @Test
    @DisplayName("Deve delegar busca por período para o client")
    void deveDelegarBuscaPorPeriodo() {
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = LocalDateTime.now().plusHours(1);
        
        tarefaService.buscarListaTarefasPorPeriodo(inicio, fim, token);
        
        verify(tarefaClient).buscarListaTarefasPorPeriodo(inicio, fim, token);
    }

    @Test
    @DisplayName("Deve delegar busca por email para o client")
    void deveDelegarBuscaPorEmail() {
        tarefaService.buscarListaTarefasPorEmail(token);
        verify(tarefaClient).buscarListaTarefasPorEmail(token);
    }

    @Test
    @DisplayName("Deve delegar deleção para o client")
    void deveDelegarDelecao() {
        tarefaService.deletarTarefaPorId("1", token);
        verify(tarefaClient).deletarTarefaPorId("1", token);
    }

    @Test
    @DisplayName("Deve delegar alteração para o client")
    void deveDelegarAlteracao() {
        TarefaDTOrequest request = new TarefaDTOrequest();
        tarefaService.alterarTarefa(request, "1", token);
        verify(tarefaClient).alterarTarefa(request, "1", token);
    }
}
