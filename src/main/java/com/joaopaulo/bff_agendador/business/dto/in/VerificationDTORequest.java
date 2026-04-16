package com.joaopaulo.bff_agendador.business.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationDTORequest {
    private String email;
    private String codigo;
}
