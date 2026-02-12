package com.joaopaulo.bff_agendador.infrastructure.client;

import com.joaopaulo.bff_agendador.business.dto.in.TarefaDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.out.TarefaDTOresponse;
import com.joaopaulo.bff_agendador.infrastructure.enums.StatusNotificacao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "agendador-tarefas", url = "${agendador-tarefa.url}")
public interface TarefaClient {
    @PostMapping
    TarefaDTOresponse gravarTarefa(@RequestBody TarefaDTOrequest tarefaDTOrequest, @RequestHeader("Authorization") String token);

    @GetMapping("/eventos")
    List<TarefaDTOresponse> buscarListaTarefasPorPeriodo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
                                                         @RequestHeader("Authorization") String token);

    @GetMapping
    List<TarefaDTOresponse> buscarListaTarefasPorEmail(@RequestHeader("Authorization") String token);

    @DeleteMapping
    void deletarTarefaPorId(@RequestParam("id") String id, @RequestHeader("Authorization") String token);

    @PatchMapping
    TarefaDTOresponse alterarStatusDeNotificacaoTarefa(@RequestParam("status") StatusNotificacao statusNotificacao, @RequestParam("id") String id,
                                                       @RequestHeader("Authorization") String token);

    @PutMapping
    TarefaDTOresponse alterarTarefa(@RequestBody TarefaDTOrequest tarefaDTOrequest, @RequestParam("id") String id,
                                    @RequestHeader("Authorization") String token);
}
