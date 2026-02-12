package com.joaopaulo.bff_agendador.controller;

import com.joaopaulo.bff_agendador.business.dto.in.EnderecoDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.LoginDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.TelefoneDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.UsuarioDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.out.EnderecoDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.out.TelefoneDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.out.UsuarioDTOresponse;
import com.joaopaulo.bff_agendador.business.UsuarioService;
import com.joaopaulo.bff_agendador.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuario", description = "Endpoints para criação e gerenciamento de usuários")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Criar um novo usuário", description = "Endpoint para criar um novo usuário no sistema.")
    @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso")
    @ApiResponse(responseCode = "409", description = "Usuário já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<UsuarioDTOresponse> salvarUsuario(@RequestBody UsuarioDTOrequest usuarioDTOrequest) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTOrequest));
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Endpoint para autenticar um usuário e obter um token de acesso.")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public String loginUsuario(@RequestBody LoginDTOrequest loginDTOrequest) {
        return usuarioService.loginUsuario(loginDTOrequest);
    }

    @GetMapping
    @Operation(summary = "Buscar usuário por email", description = "Endpoint para buscar os dados de um usuário utilizando seu email" +
            "extraido do token de acesso.",
            security = { @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME) })
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<UsuarioDTOresponse> buscarUsuarioPorEmail(@RequestParam("email") String email,
                                                                    @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email, token));
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Deletar usuário por email", description = "Endpoint para deletar um usuário utilizando seu email " +
            "extraido do token de acesso.",
            security = { @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME) })
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email,
                                                      @RequestHeader(name = "Authorization", required = false) String token) {
        usuarioService.deletarUsuarioPorEmail(email, token);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Atualizar dados do usuário", description = "Endpoint para atualizar os dados de um usuário.",
            security = { @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME) })
    @ApiResponse(responseCode = "200", description = "Dados do usuário atualizados com sucesso")
    @ApiResponse(responseCode = "403", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<UsuarioDTOresponse> atualizarDadosUsuario(@RequestBody UsuarioDTOrequest usuarioDTOrequest,
                                                                    @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, usuarioDTOrequest));
    }

    @PutMapping("/endereco")
    @Operation(summary = "Atualizar endereço do usuário", description = "Endpoint para atualizar o endereço de um usuário.",
            security = { @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME) })
    @ApiResponse(responseCode = "200", description = "Endereço do usuário atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<EnderecoDTOresponse> autalizarEnderecoUsuario(@RequestBody EnderecoDTOrequest enderecoDTOrequest,
                                                                        @RequestParam("id") Long id,
                                                                        @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(usuarioService.atualizarEnderecoUsuario(id, enderecoDTOrequest, token));
    }

    @PutMapping("/telefone")
    @Operation(summary = "Atualizar telefone do usuário", description = "Endpoint para atualizar o telefone de um usuário.",
            security = { @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME) })
    @ApiResponse(responseCode = "200", description = "Telefone do usuário atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<TelefoneDTOresponse> autalizarTelefoneUsuario(@RequestBody TelefoneDTOrequest telefoneDTOrequest,
                                                                        @RequestParam("id") Long idTelefone,
                                                                        @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(usuarioService.autalizarTelefoneUsuario(idTelefone, telefoneDTOrequest, token));
    }

    @PostMapping("/endereco")
    @Operation(summary = "Cadastrar novo endereço para o usuário", description = "Endpoint para cadastrar um novo endereço para o usuário.",
            security = { @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME) })
    @ApiResponse(responseCode = "200", description = "Endereço cadastrado com sucesso")
    @ApiResponse(responseCode = "403", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<EnderecoDTOresponse> cadastrarEndereco(@RequestBody EnderecoDTOrequest enderecoDTOrequest,
                                                                 @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(usuarioService.cadastrarEndereco(token, enderecoDTOrequest));
    }

    @PostMapping("/telefone")
    @Operation(summary = "Cadastrar novo telefone para o usuário", description = "Endpoint para cadastrar um novo telefone para o usuário.",
            security = { @SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME) })
    @ApiResponse(responseCode = "200", description = "Telefone cadastrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "403", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<TelefoneDTOresponse> cadastrarTelefone(@RequestBody TelefoneDTOrequest telefoneDTOrequest,
                                                                 @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(usuarioService.cadastrarTelefone(token, telefoneDTOrequest));
    }
}
