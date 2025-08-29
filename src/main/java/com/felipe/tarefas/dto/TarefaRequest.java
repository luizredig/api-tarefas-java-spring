package com.felipe.tarefas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class TarefaRequest {

    @NotBlank(message = "nome é obrigatório")
    @Size(min = 3, max = 120, message = "nome deve ter entre 3 e 120 caracteres")
    private String nome;

    @NotNull(message = "dataEntrega é obrigatória")
    @FutureOrPresent(message = "dataEntrega deve ser hoje ou futura")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataEntrega;

    @NotBlank(message = "responsavel é obrigatório")
    @Size(min = 3, max = 120, message = "responsavel deve ter entre 3 e 120 caracteres")
    private String responsavel;

    public TarefaRequest() {}

    public TarefaRequest(String nome, LocalDate dataEntrega, String responsavel) {
        this.nome = nome;
        this.dataEntrega = dataEntrega;
        this.responsavel = responsavel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}


