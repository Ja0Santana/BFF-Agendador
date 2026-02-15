package com.joaopaulo.bff_agendador.business;


import com.joaopaulo.bff_agendador.business.dto.in.EnderecoDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.LoginDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.TelefoneDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.in.UsuarioDTOrequest;
import com.joaopaulo.bff_agendador.business.dto.out.EnderecoDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.out.TelefoneDTOresponse;
import com.joaopaulo.bff_agendador.business.dto.out.UsuarioDTOresponse;
import com.joaopaulo.bff_agendador.infrastructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioClient usuarioClient;

    public UsuarioDTOresponse buscarUsuarioPorEmail(String email, String token) {
        return usuarioClient.buscarUsuarioPorEmail(email, token);
    }

    public UsuarioDTOresponse salvarUsuario(UsuarioDTOrequest usuarioDTOrequest) {
        return usuarioClient.salvarUsuario(usuarioDTOrequest);
    }

    public String loginUsuario(LoginDTOrequest loginDTOrequest) {
        return usuarioClient.loginUsuario(loginDTOrequest);
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
}
