package model;

import model.enums.StatusSolicitacao;

import java.time.LocalDate;

public class SolicitacaoAdocao {

    private static int proximoId = 1;

    private final int id;
    private final Animal animal;
    private final Adotante adotante;
    private final LocalDate dataSolicitacao;

    private StatusSolicitacao status;

    public SolicitacaoAdocao(
            Animal animal,
            Adotante adotante) {

        if (animal == null || adotante == null) {
            throw new IllegalArgumentException(
                    "Animal e adotante são obrigatórios."
            );
        }

        this.id = proximoId++;
        this.animal = animal;
        this.adotante = adotante;
        this.dataSolicitacao = LocalDate.now();
        this.status = StatusSolicitacao.PENDENTE;
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

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void aprovar() {

        if (status != StatusSolicitacao.PENDENTE) {
            throw new IllegalStateException(
                    "Somente solicitações pendentes podem ser aprovadas."
            );
        }

        status = StatusSolicitacao.APROVADA;
    }

    public void rejeitar() {

        if (status != StatusSolicitacao.PENDENTE) {
            throw new IllegalStateException(
                    "Somente solicitações pendentes podem ser rejeitadas."
            );
        }

        status = StatusSolicitacao.REJEITADA;
    }

    public void cancelar() {

        if (status != StatusSolicitacao.PENDENTE) {
            throw new IllegalStateException(
                    "Somente solicitações pendentes podem ser canceladas."
            );
        }

        status = StatusSolicitacao.CANCELADA;
    }

    @Override
    public String toString() {

        return "ID da solicitação: " + id
                + " | Animal: " + animal.getNome()
                + " | Adotante: " + adotante.getNome()
                + " | Data: " + dataSolicitacao
                + " | Status: " + status;
    }
}