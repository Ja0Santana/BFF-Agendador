package com.joaopaulo.bff_agendador.business.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joaopaulo.bff_agendador.infrastructure.enums.StatusNotificacao;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarefaDTOresponse {
    private String id;
    private String nomeTarefa;
    private String descricaoTarefa;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataEvento;
    private String emailUsuario;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataAlteracao;
    private StatusNotificacao statusNotificacao;
}
