
package model;

import model.enums.TipoMedicamento;
import java.time.LocalDate;

public class Medicamento {
    private static int proximoId = 1;

    private final int id;
    private final Animal animal;
    private final Veterinario veterinario;
    private final String nome;
    private final TipoMedicamento tipo;
    private final String dosagem;
    private final int duracaoDias;
    private final LocalDate dataPrescricao;

    public Medicamento(
            Animal animal,
            Veterinario veterinario,
            String nome,
            TipoMedicamento tipo,
            String dosagem,
            int duracaoDias
    ) {
        if (animal == null || veterinario == null || tipo == null) {
            throw new IllegalArgumentException(
                    "Animal, veterinário e tipo são obrigatórios."
            );
        }

        if (nome == null || nome.isBlank()
                || dosagem == null || dosagem.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome e dosagem são obrigatórios."
            );
        }

        if (duracaoDias <= 0) {
            throw new IllegalArgumentException(
                    "A duração deve ser maior que zero."
            );
        }

        this.id = proximoId++;
        this.animal = animal;
        this.veterinario = veterinario;
        this.nome = nome;
        this.tipo = tipo;
        this.dosagem = dosagem;
        this.duracaoDias = duracaoDias;
        this.dataPrescricao = LocalDate.now();
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

    public TipoMedicamento getTipo() {
        return tipo;
    }

    public String getDosagem() {
        return dosagem;
    }

    public int getDuracaoDias() {
        return duracaoDias;
    }

    public LocalDate getDataPrescricao() {
        return dataPrescricao;
    }

    @Override
    public String toString() {
        return "Medicamento: " + nome
                + " | Animal: " + animal.getNome()
                + " | Tipo: " + tipo
                + " | Dosagem: " + dosagem
                + " | Duração: " + duracaoDias + " dias"
                + " | Veterinário: " + veterinario.getNome();
    }
}
