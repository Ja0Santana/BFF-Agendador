package com.joaopaulo.bff_agendador.infrastructure.client;

import com.joaopaulo.bff_agendador.business.dto.in.EnderecoDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.LoginDTORequest;
import com.joaopaulo.bff_agendador.business.dto.in.TelefoneDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.UsuarioDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.out.CepDTOResponse;
import com.joaopaulo.bff_agendador.business.dto.out.EnderecoDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.out.TelefoneDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.out.UsuarioDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.in.ResetSenhaDTORequest;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {
    @GetMapping("/me")
    UsuarioDTOresponse buscarUsuarioAutenticado(@RequestHeader("Authorization") String token);

    @GetMapping
    UsuarioDTOresponse buscarUsuarioPorEmail(@RequestParam("email") String email,
                                             @RequestHeader("Authorization") String token);

    @PostMapping
    UsuarioDTOresponse salvarUsuario(@RequestBody UsuarioDTOrequest usuarioDTOrequest);

    @PostMapping("/login")
    String autenticarUsuario(@RequestBody LoginDTORequest loginDTORequest);

    @PostMapping("/auth/google")
    String loginComGoogle(@RequestBody com.joaopaulo.bff_agendador.business.dto.in.GoogleLoginDTORequest googleLoginDTORequest);

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

    @GetMapping("/endereco/{cep}")
    CepDTOResponse buscarDadosDeEnderecoPorCep(@PathVariable("cep") String cep);

    @PostMapping("/verificar")
    Void verificarEmail(@RequestBody com.joaopaulo.bff_agendador.business.dto.in.VerificationDTORequest verificationDTORequest, 
                        @RequestHeader("Authorization") String token);

    @PostMapping("/reenviar-codigo")
    Void reenviarCodigo(@RequestParam("email") String email, 
                        @RequestHeader("Authorization") String token);

    @PostMapping("/recuperar-senha")
    Void solicitarRecuperacaoSenha(@RequestParam("email") String email);

    @PostMapping("/resetar-senha")
    Void resetarSenha(@RequestBody ResetSenhaDTORequest resetSenhaDTORequest);
}

