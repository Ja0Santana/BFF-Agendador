package com.joaopaulo.bff_agendador.business.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoDTOrequest {
    private String rua;
    private Long numero;
    private String cidade;
    private String complemento;
    private String cep;
    private String estado;
}
