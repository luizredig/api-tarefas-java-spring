package com.felipe.tarefas.service;

import com.felipe.tarefas.dto.TarefaRequest;
import com.felipe.tarefas.dto.TarefaResponse;
import com.felipe.tarefas.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TarefaServiceTest {

    @Autowired
    private TarefaService tarefaService;

    @Test
    void deveCriarEConsultarTarefa() {
        TarefaRequest request = new TarefaRequest("Implementar autenticação", LocalDate.now().plusDays(1), "Maria Silva");
        TarefaResponse criada = tarefaService.create(request);
        assertNotNull(criada.getId());

        TarefaResponse encontrada = tarefaService.findById(criada.getId());
        assertEquals(criada.getId(), encontrada.getId());
        assertEquals("Implementar autenticação", encontrada.getNome());
    }

    @Test
    void deveRetornar404QuandoNaoEncontrada() {
        assertThrows(NotFoundException.class, () -> tarefaService.findById(99999L));
    }
}


