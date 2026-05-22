package com.joaopaulo.bff_agendador.business.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetSenhaDTORequest {
    private String email;
    private String codigo;
    private String novaSenha;
}
