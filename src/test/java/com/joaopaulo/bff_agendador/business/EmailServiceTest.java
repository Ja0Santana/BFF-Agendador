package com.joaopaulo.bff_agendador.business;

import com.joaopaulo.bff_agendador.business.dto.out.TarefaDTOresponse;
import com.joaopaulo.bff_agendador.infrastructure.client.NotificadorClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private NotificadorClient notificadorClient;

    @InjectMocks
    private EmailService emailService;

    @Test
    @DisplayName("Deve delegar o envio de e-mail para o cliente do notificador")
    void deveDelegarEnvioEmail() {
        TarefaDTOresponse tarefa = TarefaDTOresponse.builder().id("1").build();
        emailService.enviarEmail(tarefa);
        verify(notificadorClient).enviarEmail(tarefa);
    }
}
