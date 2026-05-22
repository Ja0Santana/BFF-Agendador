package com.joaopaulo.bff_agendador.business;

import com.joaopaulo.bff_agendador.business.dto.in.LoginDTORequest;
import com.joaopaulo.bff_agendador.business.dto.out.TarefaDTOresponse;
import com.joaopaulo.bff_agendador.infrastructure.enums.StatusNotificacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class CronServiceTest {

    @Mock
    private TarefaService tarefaService;

    @Mock
    private EmailService emailService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private CronService cronService;

    private final String token = "Bearer test-token";

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(cronService, "usuarioEmail", "sistema@email.com");
        ReflectionTestUtils.setField(cronService, "usuarioSenha", "senha123");
        
        // Mock do login que acontece em quase todas as operações de cron
        lenient().when(usuarioService.autenticarUsuario(any(LoginDTORequest.class))).thenReturn(token);
    }

    @Test
    @DisplayName("Deve buscar tarefas da próxima hora e disparar e-mails de notificação")
    void deveBuscarTarefasProximaHora() {
        TarefaDTOresponse tarefa = TarefaDTOresponse.builder()
                .id("1")
                .nomeTarefa("Reunião")
                .emailUsuario("user@email.com")
                .statusNotificacao(StatusNotificacao.PENDENTE)
                .build();

        when(tarefaService.buscarListaTarefasPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), eq(token)))
                .thenReturn(List.of(tarefa));

        cronService.buscarTarefasProximaHora();

        verify(emailService).enviarEmail(tarefa);
        verify(tarefaService).alterarStatusDeNotificacaoTarefa(StatusNotificacao.NOTIFICADA, "1", token);
    }

    @Test
    @DisplayName("Deve marcar tarefas como VENCIDAS e enviar e-mail de aviso")
    void deveVerificarTarefasVencidas() {
        TarefaDTOresponse tarefaVencida = TarefaDTOresponse.builder()
                .id("99")
                .nomeTarefa("Tarefa Esquecida")
                .statusNotificacao(StatusNotificacao.PENDENTE)
                .build();

        when(tarefaService.buscarListaTarefasPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), eq(token)))
                .thenReturn(List.of(tarefaVencida));

        cronService.verificarTarefasVencidas();

        verify(tarefaService).alterarStatusDeNotificacaoTarefa(StatusNotificacao.VENCIDA, "99", token);
        verify(emailService).enviarEmail(any(TarefaDTOresponse.class));
    }

    @Test
    @DisplayName("Não deve fazer nada se a lista de tarefas estiver vazia")
    void deveIgnorarSeListaVazia() {
        when(tarefaService.buscarListaTarefasPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), eq(token)))
                .thenReturn(Collections.emptyList());

        cronService.buscarTarefasProximaHora();

        verify(emailService, never()).enviarEmail(any());
        verify(tarefaService, never()).alterarStatusDeNotificacaoTarefa(any(), anyString(), anyString());
    }

    @Test
    @DisplayName("Deve ignorar tarefas que já estão VENCIDAS ou DESATIVADAS")
    void deveIgnorarTarefasJaProcessadas() {
        TarefaDTOresponse tarefaJaVencida = TarefaDTOresponse.builder()
                .id("2")
                .statusNotificacao(StatusNotificacao.VENCIDA)
                .build();
        
        TarefaDTOresponse tarefaDesativada = TarefaDTOresponse.builder()
                .id("3")
                .statusNotificacao(StatusNotificacao.DESATIVADA)
                .build();

        when(tarefaService.buscarListaTarefasPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class), eq(token)))
                .thenReturn(List.of(tarefaJaVencida, tarefaDesativada));

        cronService.verificarTarefasVencidas();

        verify(emailService, never()).enviarEmail(any());
        verify(tarefaService, never()).alterarStatusDeNotificacaoTarefa(any(), any(), any());
    }
}


