package com.joaopaulo.bff_agendador.controller;

import com.joaopaulo.bff_agendador.business.TarefaService;
import com.joaopaulo.bff_agendador.business.dto.in.TarefaDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.out.TarefaDTOresponse;
import com.joaopaulo.bff_agendador.infrastructure.enums.StatusNotificacao;
import com.joaopaulo.bff_agendador.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefa", description = "Endpoints para criação e gerenciamento de tarefas")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefaController {
    private final TarefaService tarefaService;

    @PostMapping
    @Operation(summary = "Criar uma nova tarefa", description = "Endpoint para criar uma nova tarefa.")
    @ApiResponse(responseCode = "200", description = "Tarefa criada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<TarefaDTOresponse> gravarTarefa(@RequestBody TarefaDTOrequest tarefa, @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.gravarTarefa(tarefa, token));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Busca uma lista de tarefas dentro de um período", description = "Endpoint para listar todas as tarefas cadastradas em que a data final está em um certo período.")
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<List<TarefaDTOresponse>> buscarListaTarefasPorPeriodo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
                                                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
                                                                                @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.buscarListaTarefasPorPeriodo(dataInicial, dataFinal, token));
    }

    @GetMapping
    @Operation(summary = "Busca uma lista de tarefas por email de usuário", description = "Endpoint para listar todas as tarefas cadastradas por um certo usuário.")
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<List<TarefaDTOresponse>> buscarListaTarefasPorEmail(@RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.buscarListaTarefasPorEmail(token));
    }

    @DeleteMapping
    @Operation(summary = "Deleta tarefas por ID", description = "Endpoint para listar todas as tarefas cadastradas em que a data final está em um certo período.")
    @ApiResponse(responseCode = "200", description = "Tarefa deletada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<Void> deletarTarefaPorId(@RequestParam("id") String id,
                                                   @RequestHeader(name = "Authorization", required = false) String token) {
        tarefaService.deletarTarefaPorId(id, token);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Operation(summary = "Atualiza o status de uma tarefa por ID", description = "Endpoint para atualizar o status de notificação de uma tarefa por ID.")
    @ApiResponse(responseCode = "200", description = "Status da tarefa atualizado com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<TarefaDTOresponse> alterarStatusDeNotificacaoTarefa(@RequestParam("status") StatusNotificacao statusNotificacao, @RequestParam("id") String id,
                                                                              @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.alterarStatusDeNotificacaoTarefa(statusNotificacao, id, token));
    }

    @PutMapping
    @Operation(summary = "Atualiza os dados de uma tarefa por ID", description = "Endpoint para atualizar os dados de uma tarefa por ID.")
    @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<TarefaDTOresponse> alterarTarefa(@RequestBody TarefaDTOrequest tarefaDTOrequest, @RequestParam("id") String id,
                                                           @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.alterarTarefa(tarefaDTOrequest, id, token));
    }
}
