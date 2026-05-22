package com.joaopaulo.bff_agendador.business;


import com.joaopaulo.bff_agendador.business.dto.in.EnderecoDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.LoginDTORequest;
import com.joaopaulo.bff_agendador.business.dto.in.TelefoneDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.UsuarioDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.out.CepDTOResponse;
import com.joaopaulo.bff_agendador.business.dto.out.EnderecoDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.out.TelefoneDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.out.UsuarioDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.in.ResetSenhaDTORequest;
import com.joaopaulo.bff_agendador.infrastructure.client.UsuarioClient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioClient usuarioClient;

    public UsuarioDTOresponse buscarUsuarioAutenticado(String token) {
        return usuarioClient.buscarUsuarioAutenticado(token);
    }

    public UsuarioDTOresponse buscarUsuarioPorEmail(String email, String token) {
        return usuarioClient.buscarUsuarioPorEmail(email, token);
    }

    public UsuarioDTOresponse salvarUsuario(UsuarioDTOrequest usuarioDTOrequest) {
        return usuarioClient.salvarUsuario(usuarioDTOrequest);
    }

    public String autenticarUsuario(LoginDTORequest loginDTORequest) {
        return usuarioClient.autenticarUsuario(loginDTORequest);
    }

    public String loginComGoogle(com.joaopaulo.bff_agendador.business.dto.in.GoogleLoginDTORequest googleLoginDTORequest) {
        return usuarioClient.loginComGoogle(googleLoginDTORequest);
    }

    public void deletarUsuarioPorEmail(String email, String token) {
        usuarioClient.deletarUsuarioPorEmail(email, token);
    }

    public UsuarioDTOresponse atualizaDadosUsuario(String token, UsuarioDTOrequest usuarioDTOrequest) {
        return usuarioClient.atualizarDadosUsuario(usuarioDTOrequest, token);
    }

    public EnderecoDTOresponse atualizarEnderecoUsuario(Long idEndereco, EnderecoDTOrequest enderecoDTOrequest, String token) {
        return usuarioClient.autalizarEnderecoUsuario(enderecoDTOrequest, idEndereco, token);
    }

    public TelefoneDTOresponse autalizarTelefoneUsuario(Long idTelefone, TelefoneDTOrequest telefoneDTOrequest, String token) {
        return usuarioClient.autalizarTelefoneUsuario(telefoneDTOrequest, idTelefone, token);
    }

    public EnderecoDTOresponse cadastrarEndereco(String token, EnderecoDTOrequest enderecoDTOrequest) {
        return usuarioClient.cadastrarEndereco(enderecoDTOrequest, token);
    }

    public TelefoneDTOresponse cadastrarTelefone(String token, TelefoneDTOrequest telefoneDTOrequest) {
        return usuarioClient.cadastrarTelefone(telefoneDTOrequest, token);
    }

    public CepDTOResponse buscarDadosDeEnderecoPorCep(String cep) {
        return usuarioClient.buscarDadosDeEnderecoPorCep(cep);
    }

    public void verificarEmail(com.joaopaulo.bff_agendador.business.dto.in.VerificationDTORequest verificationDTORequest, String token) {
        usuarioClient.verificarEmail(verificationDTORequest, token);
    }

    public void reenviarCodigo(String email, String token) {
        usuarioClient.reenviarCodigo(email, token);
    }

    public void solicitarRecuperacaoSenha(String email) {
        usuarioClient.solicitarRecuperacaoSenha(email);
    }

    public void resetarSenha(ResetSenhaDTORequest resetSenhaDTORequest) {
        usuarioClient.resetarSenha(resetSenhaDTORequest);
    }
}

