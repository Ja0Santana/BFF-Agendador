package com.joaopaulo.bff_agendador.infrastructure.client.config;

import com.joaopaulo.bff_agendador.infrastructure.exceptions.*;
import com.joaopaulo.bff_agendador.infrastructure.exceptions.IllegalArgumentException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FeignError implements ErrorDecoder {
    private static final String ERROR = "Erro: ";

    @Override
    public Exception decode(String s, Response response) {
        String mensagemErro = mensagemErro(response);
        return switch (response.status()) {
            case 401 -> new UnauthorizedException(ERROR + mensagemErro);
            case 404 -> new ResourceNotFoundException(ERROR + mensagemErro);
            case 409 -> new ConflictException(ERROR + mensagemErro);
            case 400 -> new IllegalArgumentException(ERROR + mensagemErro);
            default -> new BusinessException(ERROR + mensagemErro);
        };
    }
    
    public String mensagemErro(Response response) {
        try {
            if (response.body() == null) {
                return "";
            }
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new JsonConversionException("Erro ao converter objeto de erro para JSON", e.getCause());
        }
    }
}

