package com.joaopaulo.bff_agendador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaopaulo.bff_agendador.business.UsuarioService;
import com.joaopaulo.bff_agendador.business.dto.in.EnderecoDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.LoginDTORequest;
import com.joaopaulo.bff_agendador.business.dto.in.TelefoneDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.UsuarioDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.out.CepDTOResponse;
import com.joaopaulo.bff_agendador.business.dto.out.EnderecoDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.out.TelefoneDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.out.UsuarioDTOresponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve salvar usuário via POST")
    void deveSalvarUsuario() throws Exception {
        UsuarioDTOrequest request = UsuarioDTOrequest.builder().build();
        when(usuarioService.salvarUsuario(any())).thenReturn(UsuarioDTOresponse.builder().build());

        mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve realizar login")
    void deveRealizarLogin() throws Exception {
        LoginDTORequest request = LoginDTORequest.builder().build();
        when(usuarioService.autenticarUsuario(any())).thenReturn("token");

        mockMvc.perform(post("/usuario/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("token"));
    }

    @Test
    @DisplayName("Deve buscar usuário autenticado")
    void deveBuscarAutenticado() throws Exception {
        when(usuarioService.buscarUsuarioAutenticado(any())).thenReturn(UsuarioDTOresponse.builder().build());

        mockMvc.perform(get("/usuario")
                .header("Authorization", "Bearer token"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve deletar usuário por email")
    void deveDeletarPorEmail() throws Exception {
        mockMvc.perform(delete("/usuario/test@email.com")
                .header("Authorization", "Bearer token"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve atualizar dados do usuário")
    void deveAtualizarDados() throws Exception {
        UsuarioDTOrequest request = UsuarioDTOrequest.builder().build();
        when(usuarioService.atualizaDadosUsuario(any(), any())).thenReturn(UsuarioDTOresponse.builder().build());

        mockMvc.perform(put("/usuario")
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve atualizar endereço")
    void deveAtualizarEndereco() throws Exception {
        EnderecoDTOrequest request = EnderecoDTOrequest.builder().build();
        when(usuarioService.atualizarEnderecoUsuario(any(), any(), any())).thenReturn(EnderecoDTOresponse.builder().build());

        mockMvc.perform(put("/usuario/endereco")
                .param("id", "1")
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve atualizar telefone")
    void deveAtualizarTelefone() throws Exception {
        TelefoneDTOrequest request = TelefoneDTOrequest.builder().build();
        when(usuarioService.autalizarTelefoneUsuario(any(), any(), any())).thenReturn(TelefoneDTOresponse.builder().build());

        mockMvc.perform(put("/usuario/telefone")
                .param("id", "1")
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve buscar endereço por CEP")
    void deveBuscarPorCep() throws Exception {
        CepDTOResponse response = new CepDTOResponse("12345678", "Logradouro", "Complemento", "Unidade", "Bairro", "Localidade", "UF", "Estado", "Região", "IBGE", "GIA", "DDD", "SIAFI");
        when(usuarioService.buscarDadosDeEnderecoPorCep(anyString())).thenReturn(response);

        mockMvc.perform(get("/usuario/endereco/12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep").value("12345678"));
    }

    @Test
    @DisplayName("Deve verificar e-mail via POST")
    void deveVerificarEmail() throws Exception {
        com.joaopaulo.bff_agendador.business.dto.in.VerificationDTORequest request = new com.joaopaulo.bff_agendador.business.dto.in.VerificationDTORequest("test@email.com", "123456");
        
        mockMvc.perform(post("/usuario/verificar")
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve reenviar código via POST")
    void deveReenviarCodigo() throws Exception {
        mockMvc.perform(post("/usuario/reenviar-codigo")
                .param("email", "test@email.com")
                .header("Authorization", "Bearer token"))
                .andExpect(status().isOk());
    }
}
