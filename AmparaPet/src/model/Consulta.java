
package model;

import model.enums.TipoConsulta;
import java.time.LocalDate;

public class Consulta {
    private static int proximoId = 1;

    private final int id;
    private final Animal animal;
    private final Veterinario veterinario;
    private final LocalDate data;
    private TipoConsulta tipo;
    private String diagnostico;
    private String observacoes;

    public Consulta(
            Animal animal,
            Veterinario veterinario,
            TipoConsulta tipo,
            String diagnostico,
            String observacoes
    ) {
        if (animal == null || veterinario == null || tipo == null) {
            throw new IllegalArgumentException(
                    "Animal, veterinário e tipo são obrigatórios."
            );
        }

        this.id = proximoId++;
        this.animal = animal;
        this.veterinario = veterinario;
        this.data = LocalDate.now();

        setTipo(tipo);
        setDiagnostico(diagnostico);
        setObservacoes(observacoes);
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

    public LocalDate getData() {
        return data;
    }

    public TipoConsulta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConsulta tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo obrigatório.");
        }

        this.tipo = tipo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        if (diagnostico == null || diagnostico.isBlank()) {
            throw new IllegalArgumentException("Diagnóstico obrigatório.");
        }

        this.diagnostico = diagnostico;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes == null ? "" : observacoes;
    }

    @Override
    public String toString() {
        return "Consulta: " + id
                + " | Animal: " + animal.getNome()
                + " | Veterinário: " + veterinario.getNome()
                + " | Data: " + data
                + " | Tipo: " + tipo
                + " | Diagnóstico: " + diagnostico
                + " | Observações: " + observacoes;
    }
}
