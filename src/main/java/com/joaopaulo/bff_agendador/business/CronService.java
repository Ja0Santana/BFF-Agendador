package com.joaopaulo.bff_agendador.business;

import com.joaopaulo.bff_agendador.business.dto.in.LoginDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.out.TarefaDTOresponse;
import com.joaopaulo.bff_agendador.infrastructure.enums.StatusNotificacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CronService {
    private final TarefaService tarefaService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    @Value("${usuario.email}")
    private String usuarioEmail;
    @Value("${usuario.senha}")
    private String usuarioSenha;

    @Scheduled(cron = "${cron.horario}")
    public void buscarTarefasProximaHora() {
        String token = loginUsuario(converterUsuarioDTOrequest());
        log.info("Iniciada a busca de tarefas");
        LocalDateTime horaFutura = LocalDateTime.now().plusHours(1);
        LocalDateTime horaFuturaMais5Minutos = horaFutura.plusMinutes(5);
        List<TarefaDTOresponse> listaDeTarefas = tarefaService.buscarListaTarefasPorPeriodo(horaFutura, horaFuturaMais5Minutos, token);
        log.info("Tarefas encontradas: {}", listaDeTarefas);

        listaDeTarefas.forEach(tarefa -> {
            emailService.enviarEmail(tarefa);
            log.info("Enviado email para o usuario: {}", tarefa.getEmailUsuario());
            tarefaService.alterarStatusDeNotificacaoTarefa(StatusNotificacao.NOTIFICADA, tarefa.getId(), token);
        });
    }

    @Scheduled(cron = "0 0/15 * * * *") // Roda a cada 15 minutos
    public void verificarTarefasVencidas() {
        String token = loginUsuario(converterUsuarioDTOrequest());
        log.info("Verificando tarefas vencidas...");
        
        // Buscamos tarefas que deveriam ter ocorrido no passado (até agora)
        // e que ainda estão PENDENTES ou NOTIFICADAS
        LocalDateTime passadoDistante = LocalDateTime.now().minusDays(30);
        LocalDateTime agora = LocalDateTime.now();
        
        List<TarefaDTOresponse> tarefasVencidas = tarefaService.buscarListaTarefasPorPeriodo(passadoDistante, agora, token);
        
        tarefasVencidas.forEach(tarefa -> {
            if (tarefa.getStatusNotificacao() == StatusNotificacao.PENDENTE || 
                tarefa.getStatusNotificacao() == StatusNotificacao.NOTIFICADA) {
                
                log.info("Marcando tarefa como VENCIDA: {}", tarefa.getNomeTarefa());
                
                // Primeiro atualizamos o status para VENCIDA para garantir que o email vá com o template correto
                tarefa.setStatusNotificacao(StatusNotificacao.VENCIDA);
                tarefaService.alterarStatusDeNotificacaoTarefa(StatusNotificacao.VENCIDA, tarefa.getId(), token);
                
                // Enviamos o e-mail de aviso
                emailService.enviarEmail(tarefa);
            }
        });
    }

    private String loginUsuario(LoginDTOrequest loginDTOrequest) {
        return usuarioService.loginUsuario(loginDTOrequest);
    }

    public LoginDTOrequest converterUsuarioDTOrequest() {
        return LoginDTOrequest.builder()
                .email(usuarioEmail)
                .senha(usuarioSenha)
                .build();
    }
}
