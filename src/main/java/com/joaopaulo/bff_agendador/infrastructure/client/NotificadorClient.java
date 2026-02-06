package com.joaopaulo.bff_agendador.infrastructure.client;

import com.joaopaulo.bff_agendador.business.dto.out.TarefaDTOresponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificador", url = "${notificador.url}")
public interface NotificadorClient {
    @PostMapping
    void enviarEmail(@RequestBody TarefaDTOresponse tarefaDTOresponse);
}
