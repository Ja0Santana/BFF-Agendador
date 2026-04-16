package com.joaopaulo.bff_agendador.infrastructure.client.config;

import com.joaopaulo.bff_agendador.infrastructure.exceptions.*;
import com.joaopaulo.bff_agendador.infrastructure.exceptions.IllegalArgumentException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FeignErrorTest {

    private final FeignError feignError = new FeignError();

    @Test
    @DisplayName("Deve decodificar erro 401")
    void deveDecodificar401() {
        Response response = createResponse(401, "Acesso Negado");
        Exception result = feignError.decode("method", response);
        assertThat(result).isInstanceOf(UnauthorizedException.class);
        assertThat(result.getMessage()).contains("Acesso Negado");
    }

    @Test
    @DisplayName("Deve decodificar erro 404")
    void deveDecodificar404() {
        Response response = createResponse(404, "Não Encontrado");
        Exception result = feignError.decode("method", response);
        assertThat(result).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("Deve decodificar erro 409")
    void deveDecodificar409() {
        Response response = createResponse(409, "Conflito");
        Exception result = feignError.decode("method", response);
        assertThat(result).isInstanceOf(ConflictException.class);
    }

    @Test
    @DisplayName("Deve decodificar erro 400")
    void deveDecodificar400() {
        Response response = createResponse(400, "Dados Inválidos");
        Exception result = feignError.decode("method", response);
        assertThat(result).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Deve decodificar erro genérico como BusinessException")
    void deveDecodificarGeneric() {
        Response response = createResponse(500, "Erro Interno");
        Exception result = feignError.decode("method", response);
        assertThat(result).isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("Deve retornar mensagem vazia se corpo for nulo")
    void deveRetornarMsgVaziaSeCorpoNulo() {
        Response response = Response.builder()
                .status(400)
                .reason("Bad Request")
                .request(mock(Request.class))
                .body((Response.Body) null)
                .build();
        
        String msg = feignError.mensagemErro(response);
        assertThat(msg).isEmpty();
    }

    @Test
    @DisplayName("Deve lançar JsonConversionException se ocorrer erro na leitura do stream")
    void deveLancarJsonConversionExceptionEmErroDeLeitura() throws java.io.IOException {
        Response.Body body = mock(Response.Body.class);
        when(body.asInputStream()).thenThrow(new java.io.IOException("Stream error"));
        
        Response response = Response.builder()
                .status(400)
                .reason("Bad Request")
                .request(mock(Request.class))
                .body(body)
                .build();

        assertThatThrownBy(() -> feignError.mensagemErro(response))
                .isInstanceOf(JsonConversionException.class)
                .hasMessageContaining("Erro ao converter objeto de erro para JSON");
    }

    private Response createResponse(int status, String body) {
        return Response.builder()
                .status(status)
                .reason("Reason")
                .request(mock(Request.class))
                .headers(Collections.emptyMap())
                .body(body, StandardCharsets.UTF_8)
                .build();
    }
}
