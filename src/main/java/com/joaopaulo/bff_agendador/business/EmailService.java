package com.joaopaulo.bff_agendador.business;


import com.joaopaulo.bff_agendador.business.dto.out.TarefaDTOresponse;
import com.joaopaulo.bff_agendador.infrastructure.client.NotificadorClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final NotificadorClient notificadorClient;

    public void enviarEmail(TarefaDTOresponse tarefaDTOresponse, String token) {
        notificadorClient.enviarEmail(tarefaDTOresponse, token);
    }
}
