package com.joaopaulo.bff_agendador.infrastructure.client.config;

import com.joaopaulo.bff_agendador.infrastructure.exceptions.BusinessException;
import com.joaopaulo.bff_agendador.infrastructure.exceptions.ConflictException;
import com.joaopaulo.bff_agendador.infrastructure.exceptions.IllegalArgumentException;
import com.joaopaulo.bff_agendador.infrastructure.exceptions.ResourceNotFoundException;
import com.joaopaulo.bff_agendador.infrastructure.exceptions.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FeignError implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        String mensagemErro = mensagemErro(response);
        return switch (response.status()) {
            case 401 -> new UnauthorizedException("Erro: " + mensagemErro);
            case 403 -> new ResourceNotFoundException("Erro: " + mensagemErro);
            case 409 -> new ConflictException("Erro: " + mensagemErro);
            case 400 -> new IllegalArgumentException("Erro: " + mensagemErro);
            default -> new BusinessException("Erro: " + mensagemErro);
        };
    }
    
    public String mensagemErro(Response response) {
        try {
            if (response.body() == null) {
                return "";
            }
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

