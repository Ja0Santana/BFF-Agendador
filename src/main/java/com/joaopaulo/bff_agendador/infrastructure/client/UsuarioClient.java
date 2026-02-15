package com.joaopaulo.bff_agendador.infrastructure.client;

import com.joaopaulo.bff_agendador.business.dto.in.EnderecoDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.LoginDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.TelefoneDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.UsuarioDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.out.EnderecoDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.out.TelefoneDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.out.UsuarioDTOresponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {
    @GetMapping
    UsuarioDTOresponse buscarUsuarioPorEmail(@RequestParam("email") String email,
                                             @RequestHeader("Authorization") String token);

    @PostMapping
    UsuarioDTOresponse salvarUsuario(@RequestBody UsuarioDTOrequest usuarioDTOrequest);

    @PostMapping("/login")
    String loginUsuario(@RequestBody LoginDTOrequest loginDTOrequest);

    @DeleteMapping("/{email}")
    Void deletarUsuarioPorEmail(@PathVariable String email,
                                @RequestHeader("Authorization") String token);

    @PutMapping
    UsuarioDTOresponse atualizarDadosUsuario(@RequestBody UsuarioDTOrequest usuarioDTOrequest,
                                             @RequestHeader("Authorization") String token);

    @PutMapping("/endereco")
    EnderecoDTOresponse autalizarEnderecoUsuario(@RequestBody EnderecoDTOrequest enderecoDTOrequest,
                                                 @RequestParam("id") Long id,
                                                 @RequestHeader("Authorization") String token);

    @PutMapping("/telefone")
    TelefoneDTOresponse autalizarTelefoneUsuario(@RequestBody TelefoneDTOrequest telefoneDTOrequest,
                                                 @RequestParam("id") Long idTelefone,
                                                 @RequestHeader("Authorization") String token);

    @PostMapping("/endereco")
    EnderecoDTOresponse cadastrarEndereco(@RequestBody EnderecoDTOrequest enderecoDTOrequest,
                                          @RequestHeader("Authorization") String token);

    @PostMapping("/telefone")
    TelefoneDTOresponse cadastrarTelefone(@RequestBody TelefoneDTOrequest telefoneDTOrequest,
                                          @RequestHeader("Authorization") String token);
}
