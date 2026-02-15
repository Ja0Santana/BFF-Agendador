package com.joaopaulo.bff_agendador.business.dto.out;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelefoneDTOresponse {
    private Long id;
    private String numero;
    private String ddd;
}
