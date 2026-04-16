package com.joaopaulo.bff_agendador.infrastructure.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SecurityBffTest {

    @Test
    @DisplayName("Deve garantir que a classe de configuração tenha a constante correta")
    void deveValidarConstante() {
        assertThat(SecurityConfig.SECURITY_SCHEME).isEqualTo("BearerAuth");
    }

    @Test
    @DisplayName("Deve validar que o construtor é privado e lança erro")
    void deveTestarConstrutorPrivado() throws Exception {
        Constructor<SecurityConfig> constructor = SecurityConfig.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertThat(exception.getCause()).isInstanceOf(AssertionError.class);
        assertThat(exception.getCause().getMessage()).isEqualTo("Utility class");
    }
}
