
package model;

import model.enums.StatusTratamento;
import java.time.LocalDate;

public class Tratamento {
    private static int proximoId = 1;

    private final int id;
    private final Animal animal;
    private final Veterinario veterinario;
    private final String descricao;
    private final LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusTratamento status;

    public Tratamento(
            Animal animal,
            Veterinario veterinario,
            String descricao
    ) {
        if (animal == null || veterinario == null) {
            throw new IllegalArgumentException(
                    "Animal e veterinário são obrigatórios."
            );
        }

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException(
                    "Descrição do tratamento obrigatória."
            );
        }

        this.id = proximoId++;
        this.animal = animal;
        this.veterinario = veterinario;
        this.descricao = descricao;
        this.dataInicio = LocalDate.now();
        this.dataFim = null;
        this.status = StatusTratamento.EM_ANDAMENTO;
    }

    public int getId() {
        return id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public StatusTratamento getStatus() {
        return status;
    }

    public void concluir() {
        if (status != StatusTratamento.EM_ANDAMENTO) {
            throw new IllegalStateException(
                    "Somente tratamentos em andamento podem ser concluídos."
            );
        }

        status = StatusTratamento.CONCLUIDO;
        dataFim = LocalDate.now();
    }

    public void interromper() {
        if (status != StatusTratamento.EM_ANDAMENTO) {
            throw new IllegalStateException(
                    "Somente tratamentos em andamento podem ser interrompidos."
            );
        }

        status = StatusTratamento.INTERROMPIDO;
        dataFim = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Tratamento: " + id
                + " | Animal: " + animal.getNome()
                + " | Descrição: " + descricao
                + " | Início: " + dataInicio
                + " | Fim: " + dataFim
                + " | Status: " + status;
    }
}
