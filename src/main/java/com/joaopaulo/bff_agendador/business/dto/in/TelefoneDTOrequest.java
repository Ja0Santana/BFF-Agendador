package com.joaopaulo.bff_agendador.business.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelefoneDTOrequest {
    private String numero;
    private String ddd;
}
