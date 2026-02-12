package com.joaopaulo.bff_agendador.business.dto.in;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTOrequest {
    private String email;
    private String senha;
}
