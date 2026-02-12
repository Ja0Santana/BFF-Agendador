package com.joaopaulo.bff_agendador.business.dto.out;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTOresponse {
    private String nome;
    private String email;
    private String senha;
    private List<EnderecoDTOresponse> enderecos;
    private List<TelefoneDTOresponse> telefones;
}
