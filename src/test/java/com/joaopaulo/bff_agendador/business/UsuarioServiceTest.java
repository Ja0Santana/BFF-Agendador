package com.joaopaulo.bff_agendador.business;

import com.joaopaulo.bff_agendador.business.dto.in.EnderecoDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.LoginDTORequest;
import com.joaopaulo.bff_agendador.business.dto.in.TelefoneDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.UsuarioDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.out.CepDTOResponse;
import com.joaopaulo.bff_agendador.business.dto.out.UsuarioDTOresponse;
import com.joaopaulo.bff_agendador.infrastructure.client.UsuarioClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private UsuarioService usuarioService;

    private final String token = "Bearer token";

    @Test
    @DisplayName("Deve delegar busca de usuário autenticado")
    void deveDelegarBuscarUsuarioAutenticado() {
        UsuarioDTOresponse response = UsuarioDTOresponse.builder().email("test@email.com").build();
        when(usuarioClient.buscarUsuarioAutenticado(token)).thenReturn(response);

        UsuarioDTOresponse result = usuarioService.buscarUsuarioAutenticado(token);

        assertThat(result.getEmail()).isEqualTo("test@email.com");
        verify(usuarioClient).buscarUsuarioAutenticado(token);
    }

    @Test
    @DisplayName("Deve delegar busca por email")
    void deveDelegarBuscarUsuarioPorEmail() {
        usuarioService.buscarUsuarioPorEmail("test@email.com", token);
        verify(usuarioClient).buscarUsuarioPorEmail("test@email.com", token);
    }

    @Test
    @DisplayName("Deve delegar salvar usuário")
    void deveDelegarSalvarUsuario() {
        UsuarioDTOrequest request = UsuarioDTOrequest.builder().build();
        usuarioService.salvarUsuario(request);
        verify(usuarioClient).salvarUsuario(request);
    }

    @Test
    @DisplayName("Deve delegar login")
    void deveDelegarLogin() {
        LoginDTORequest request = LoginDTORequest.builder().build();
        when(usuarioClient.autenticarUsuario(request)).thenReturn("token");
        
        String result = usuarioService.autenticarUsuario(request);
        
        assertThat(result).isEqualTo("token");
        verify(usuarioClient).autenticarUsuario(request);
    }

    @Test
    @DisplayName("Deve delegar deleção por email")
    void deveDelegarDelecao() {
        usuarioService.deletarUsuarioPorEmail("test@email.com", token);
        verify(usuarioClient).deletarUsuarioPorEmail("test@email.com", token);
    }

    @Test
    @DisplayName("Deve delegar atualização de dados")
    void deveDelegarAtualizacaoDados() {
        UsuarioDTOrequest request = UsuarioDTOrequest.builder().build();
        usuarioService.atualizaDadosUsuario(token, request);
        verify(usuarioClient).atualizarDadosUsuario(request, token);
    }

    @Test
    @DisplayName("Deve delegar atualização de endereço")
    void deveDelegarAtualizacaoEndereco() {
        EnderecoDTOrequest request = EnderecoDTOrequest.builder().build();
        usuarioService.atualizarEnderecoUsuario(1L, request, token);
        verify(usuarioClient).autalizarEnderecoUsuario(request, 1L, token);
    }

    @Test
    @DisplayName("Deve delegar atualização de telefone")
    void deveDelegarAtualizacaoTelefone() {
        TelefoneDTOrequest request = TelefoneDTOrequest.builder().build();
        usuarioService.autalizarTelefoneUsuario(1L, request, token);
        verify(usuarioClient).autalizarTelefoneUsuario(request, 1L, token);
    }

    @Test
    @DisplayName("Deve delegar cadastro de endereço")
    void deveDelegarCadastroEndereco() {
        EnderecoDTOrequest request = EnderecoDTOrequest.builder().build();
        usuarioService.cadastrarEndereco(token, request);
        verify(usuarioClient).cadastrarEndereco(request, token);
    }

    @Test
    @DisplayName("Deve delegar cadastro de telefone")
    void deveDelegarCadastroTelefone() {
        TelefoneDTOrequest request = TelefoneDTOrequest.builder().build();
        usuarioService.cadastrarTelefone(token, request);
        verify(usuarioClient).cadastrarTelefone(request, token);
    }

    @Test
    @DisplayName("Deve delegar busca por CEP")
    void deveDelegarBuscaCep() {
        when(usuarioClient.buscarDadosDeEnderecoPorCep("12345678")).thenReturn(new CepDTOResponse("12345678", "Logradouro", "Complemento", "Unidade", "Bairro", "Localidade", "UF", "Estado", "Região", "IBGE", "GIA", "DDD", "SIAFI"));
        usuarioService.buscarDadosDeEnderecoPorCep("12345678");
        verify(usuarioClient).buscarDadosDeEnderecoPorCep("12345678");
    }

    @Test
    @DisplayName("Deve delegar verificação de e-mail")
    void deveDelegarVerificacaoEmail() {
        com.joaopaulo.bff_agendador.business.dto.in.VerificationDTORequest request = new com.joaopaulo.bff_agendador.business.dto.in.VerificationDTORequest("test@email.com", "123456");
        usuarioService.verificarEmail(request, token);
        verify(usuarioClient).verificarEmail(request, token);
    }

    @Test
    @DisplayName("Deve delegar reenvio de código")
    void deveDelegarReenvioCodigo() {
        usuarioService.reenviarCodigo("test@email.com", token);
        verify(usuarioClient).reenviarCodigo("test@email.com", token);
    }
}
