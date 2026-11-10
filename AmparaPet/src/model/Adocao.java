package model;

import java.time.LocalDate;

public class Adocao {

    private static int proximoId = 1;

    private final int id;
    private final Animal animal;
    private final Adotante adotante;
    private final Administrador administrador;
    private final LocalDate dataAdocao;

    private LocalDate dataDevolucao;
    private String motivoDevolucao;

    public Adocao(
            Animal animal,
            Adotante adotante,
            Administrador administrador) {

        if (animal == null || adotante == null
                || administrador == null) {

            throw new IllegalArgumentException(
                    "Os dados da adoção são obrigatórios."
            );
        }

        this.id = proximoId++;
        this.animal = animal;
        this.adotante = adotante;
        this.administrador = administrador;
        this.dataAdocao = LocalDate.now();

        this.dataDevolucao = null;
        this.motivoDevolucao = null;
    }

    public int getId() {
        return id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Adotante getAdotante() {
        return adotante;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public LocalDate getDataAdocao() {
        return dataAdocao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public String getMotivoDevolucao() {
        return motivoDevolucao;
    }

    public boolean foiDevolvido() {
        return dataDevolucao != null;
    }

    public void registrarDevolucao(String motivo) {

        if (foiDevolvido()) {
            throw new IllegalStateException(
                    "Esta adoção já possui uma devolução registrada."
            );
        }

        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException(
                    "O motivo da devolução é obrigatório."
            );
        }

        this.dataDevolucao = LocalDate.now();
        this.motivoDevolucao = motivo;
    }

    @Override
    public String toString() {

        return "ID da adoção: " + id
                + " | Animal: " + animal.getNome()
                + " | Adotante: " + adotante.getNome()
                + " | Administrador: " + administrador.getNome()
                + " | Data: " + dataAdocao
                + " | Devolvido: " + foiDevolvido()
                + (foiDevolvido()
                ? " | Motivo: " + motivoDevolucao
                  + " | Data da devolução: " + dataDevolucao
                : "");
    }
}