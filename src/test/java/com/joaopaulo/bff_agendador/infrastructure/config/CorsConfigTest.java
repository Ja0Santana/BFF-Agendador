package com.joaopaulo.bff_agendador.infrastructure.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.assertj.core.api.Assertions.assertThat;

class CorsConfigTest {

    @Test
    @DisplayName("Deve garantir que o Bean de configuração do CORS é criado")
    void deveCriarBeanCors() {
        CorsConfig corsConfig = new CorsConfig();
        WebMvcConfigurer webMvcConfigurer = corsConfig.corsConfigurer();
        
        assertThat(webMvcConfigurer).isNotNull();
    }
}
