package com.joaopaulo.bff_agendador.infrastructure.client.config;

import com.joaopaulo.bff_agendador.infrastructure.exceptions.BusinessException;
import com.joaopaulo.bff_agendador.infrastructure.exceptions.ConflictException;
import com.joaopaulo.bff_agendador.infrastructure.exceptions.ResourceNotFoundException;
import com.joaopaulo.bff_agendador.infrastructure.exceptions.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignError implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 401:
                return new UnauthorizedException("Erro! Usuário não autorizado");
            case 403:
                return new ResourceNotFoundException("Erro! Atributo não encontrado");
            case 404:
                return new ResourceNotFoundException("Erro! Recurso ou Usuário não encontrado");
            case 409:
                return new ConflictException("Erro! Atributo já existe");
            default:
                return new BusinessException("Erro! Ocorreu um erro inesperado: " + response.status());
        }
    }
}

