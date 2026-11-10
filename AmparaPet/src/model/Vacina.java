
package model;

import java.time.LocalDate;

public class Vacina {
    private static int proximoId = 1;

    private final int id;
    private final Animal animal;
    private final Veterinario veterinario;
    private final String nome;
    private final LocalDate dataAplicacao;
    private final LocalDate dataValidade;

    public Vacina(
            Animal animal,
            Veterinario veterinario,
            String nome,
            LocalDate dataAplicacao,
            LocalDate dataValidade
    ) {
        if (animal == null || veterinario == null) {
            throw new IllegalArgumentException(
                    "Animal e veterinário são obrigatórios."
            );
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da vacina obrigatório.");
        }

        if (dataAplicacao == null || dataValidade == null) {
            throw new IllegalArgumentException("As datas são obrigatórias.");
        }

        if (dataValidade.isBefore(dataAplicacao)) {
            throw new IllegalArgumentException(
                    "A validade não pode ser anterior à aplicação."
            );
        }

        if (dataAplicacao.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "A data de aplicação não pode estar no futuro."
            );
        }

        this.id = proximoId++;
        this.animal = animal;
        this.veterinario = veterinario;
        this.nome = nome;
        this.dataAplicacao = dataAplicacao;
        this.dataValidade = dataValidade;
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

    public String getNome() {
        return nome;
    }

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public boolean estaVencida() {
        return dataValidade.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Vacina: " + nome
                + " | Animal: " + animal.getNome()
                + " | Veterinário: " + veterinario.getNome()
                + " | Aplicação: " + dataAplicacao
                + " | Validade: " + dataValidade
                + " | Vencida: " + estaVencida();
    }
}
