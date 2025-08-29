package com.felipe.tarefas.controller;

import com.felipe.tarefas.dto.TarefaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarListarEObterPorId() throws Exception {
        TarefaRequest request = new TarefaRequest("Implementar autenticação", LocalDate.now().plusDays(2), "Maria Silva");

        String json = objectMapper.writeValueAsString(request);

        String location = mockMvc.perform(post("/api/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.matchesPattern(".*/api/tarefas/\\d+")))
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get("/api/tarefas").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.links.self").exists());
    }

    @Test
    void deveRetornar404QuandoNaoEncontrada() throws Exception {
        mockMvc.perform(get("/api/tarefas/999999"))
                .andExpect(status().isNotFound());
    }
}


