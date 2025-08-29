package com.felipe.tarefas.service;

import com.felipe.tarefas.dto.TarefaRequest;
import com.felipe.tarefas.dto.TarefaResponse;
import com.felipe.tarefas.exception.NotFoundException;
import com.felipe.tarefas.model.Tarefa;
import com.felipe.tarefas.repository.TarefaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public TarefaResponse create(TarefaRequest request) {
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(request.getNome());
        tarefa.setDataEntrega(request.getDataEntrega());
        tarefa.setResponsavel(request.getResponsavel());
        Tarefa saved = tarefaRepository.save(tarefa);
        return toResponse(saved);
    }

    public Page<TarefaResponse> findAll(Pageable pageable) {
        return tarefaRepository.findAll(pageable).map(this::toResponse);
    }

    public TarefaResponse findById(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarefa id=" + id + " não encontrada"));
        return toResponse(tarefa);
    }

    public TarefaResponse update(Long id, TarefaRequest request) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarefa id=" + id + " não encontrada"));
        tarefa.setNome(request.getNome());
        tarefa.setDataEntrega(request.getDataEntrega());
        tarefa.setResponsavel(request.getResponsavel());
        Tarefa updated = tarefaRepository.save(tarefa);
        return toResponse(updated);
    }

    public void delete(Long id) {
        Optional<Tarefa> maybe = tarefaRepository.findById(id);
        if (maybe.isEmpty()) {
            throw new NotFoundException("Tarefa id=" + id + " não encontrada");
        }
        tarefaRepository.deleteById(id);
    }

    private TarefaResponse toResponse(Tarefa tarefa) {
        TarefaResponse response = new TarefaResponse(
                tarefa.getId(),
                tarefa.getNome(),
                tarefa.getDataEntrega(),
                tarefa.getResponsavel()
        );
        response.getLinks().put("self", "/api/tarefas/" + tarefa.getId());
        return response;
    }
}


