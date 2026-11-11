
package model;

import model.enums.StatusAtividade;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Atividade {

    private static int proximoId = 1;

    private final int id;
    private String nome;
    private String descricao;
    private LocalDate data;
    private String local;
    private int limiteParticipantes;
    private StatusAtividade status;
    private final Administrador administrador;

    public Atividade(
            String nome,
            String descricao,
            LocalDate data,
            String local,
            int limiteParticipantes,
            Administrador administrador
    ) {
        if (administrador == null) {
            throw new IllegalArgumentException(
                    "Administrador responsável obrigatório."
            );
        }

        this.id = proximoId++;

        setNome(nome);
        setDescricao(descricao);
        setData(data);
        setLocal(local);
        setLimiteParticipantes(limiteParticipantes);

        this.administrador = administrador;
        this.status = StatusAtividade.AGENDADA;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome da atividade obrigatório."
            );
        }

        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException(
                    "Descrição obrigatória."
            );
        }

        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        if (data == null || data.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "A data da atividade não pode estar no passado."
            );
        }

        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        if (local == null || local.isBlank()) {
            throw new IllegalArgumentException(
                    "Local da atividade obrigatório."
            );
        }

        this.local = local;
    }

    public int getLimiteParticipantes() {
        return limiteParticipantes;
    }

    public void setLimiteParticipantes(int limiteParticipantes) {
        if (limiteParticipantes <= 0) {
            throw new IllegalArgumentException(
                    "O limite de participantes deve ser maior que zero."
            );
        }

        this.limiteParticipantes = limiteParticipantes;
    }

    public StatusAtividade getStatus() {
        return status;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void iniciar() {
        if (status != StatusAtividade.AGENDADA) {
            throw new IllegalStateException(
                    "Somente atividades agendadas podem ser iniciadas."
            );
        }

        status = StatusAtividade.EM_ANDAMENTO;
    }

    public void concluir() {
        if (status != StatusAtividade.EM_ANDAMENTO) {
            throw new IllegalStateException(
                    "Somente atividades em andamento podem ser concluídas."
            );
        }

        status = StatusAtividade.CONCLUIDA;
    }

    public void cancelar() {
        if (status == StatusAtividade.CONCLUIDA
                || status == StatusAtividade.CANCELADA) {
            throw new IllegalStateException(
                    "Esta atividade não pode mais ser cancelada."
            );
        }

        status = StatusAtividade.CANCELADA;
    }

    @Override
    public String toString() {
        return "Atividade: " + nome
                + " | ID: " + id
                + " | Data: " + data
                + " | Local: " + local
                + " | Limite: " + limiteParticipantes
                + " | Status: " + status
                + " | Administrador: " + administrador.getNome();
    }
}
