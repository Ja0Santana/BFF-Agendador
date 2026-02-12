package com.joaopaulo.bff_agendador.business;


import com.joaopaulo.bff_agendador.business.dto.in.TarefaDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.out.TarefaDTOresponse;
import com.joaopaulo.bff_agendador.infrastructure.client.TarefaClient;
import com.joaopaulo.bff_agendador.infrastructure.enums.StatusNotificacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {
    private final TarefaClient tarefaClient;

    public TarefaDTOresponse gravarTarefa(TarefaDTOrequest tarefaDTOrequest, String token) {
        return tarefaClient.gravarTarefa(tarefaDTOrequest, token);
    }

    public List<TarefaDTOresponse> buscarListaTarefasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal, String token) {
        return tarefaClient.buscarListaTarefasPorPeriodo(dataInicial, dataFinal, token);
    }

    public List<TarefaDTOresponse> buscarListaTarefasPorEmail(String token) {
        return tarefaClient.buscarListaTarefasPorEmail(token);
    }
    public void deletarTarefaPorId(String id, String token) {
        tarefaClient.deletarTarefaPorId(id, token);
    }
    public TarefaDTOresponse alterarStatusDeNotificacaoTarefa(StatusNotificacao statusNotificacao, String id, String token) {
        return tarefaClient.alterarStatusDeNotificacaoTarefa(statusNotificacao, id, token);
    }

    public TarefaDTOresponse alterarTarefa(TarefaDTOrequest tarefaDTOrequest, String id, String token) {
        return tarefaClient.alterarTarefa(tarefaDTOrequest, id, token);
    }
}
