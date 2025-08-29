package com.felipe.tarefas.controller;

import com.felipe.tarefas.dto.TarefaRequest;
import com.felipe.tarefas.dto.TarefaResponse;
import com.felipe.tarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    public ResponseEntity<TarefaResponse> create(@Valid @RequestBody TarefaRequest request,
                                                 UriComponentsBuilder uriBuilder) {
        TarefaResponse response = tarefaService.create(request);
        return ResponseEntity.created(
                uriBuilder.path("/api/tarefas/{id}").buildAndExpand(response.getId()).toUri()
        ).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<TarefaResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(tarefaService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody TarefaRequest request) {
        return ResponseEntity.ok(tarefaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tarefaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


