
package model;

import model.enums.StatusParticipacao;
import java.time.LocalDate;

public class ParticipacaoAtividade {

    private static int proximoId = 1;

    private final int id;
    private final Atividade atividade;
    private final Voluntario voluntario;
    private final LocalDate dataInscricao;
    private StatusParticipacao status;

    public ParticipacaoAtividade(
            Atividade atividade,
            Voluntario voluntario
    ) {
        if (atividade == null || voluntario == null) {
            throw new IllegalArgumentException(
                    "Atividade e voluntário são obrigatórios."
            );
        }

        this.id = proximoId++;
        this.atividade = atividade;
        this.voluntario = voluntario;
        this.dataInscricao = LocalDate.now();
        this.status = StatusParticipacao.INSCRITO;
    }

    public int getId() {
        return id;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public LocalDate getDataInscricao() {
        return dataInscricao;
    }

    public StatusParticipacao getStatus() {
        return status;
    }

    public void registrarPresenca() {
        if (status != StatusParticipacao.INSCRITO) {
            throw new IllegalStateException(
                    "Somente inscrições ativas podem receber presença."
            );
        }

        status = StatusParticipacao.PRESENTE;
    }

    public void registrarAusencia() {
        if (status != StatusParticipacao.INSCRITO) {
            throw new IllegalStateException(
                    "Somente inscrições ativas podem receber ausência."
            );
        }

        status = StatusParticipacao.AUSENTE;
    }

    public void cancelar() {
        if (status != StatusParticipacao.INSCRITO) {
            throw new IllegalStateException(
                    "Somente inscrições ativas podem ser canceladas."
            );
        }

        status = StatusParticipacao.CANCELADO;
    }

    @Override
    public String toString() {
        return "Participação: " + id
                + " | Atividade: " + atividade.getNome()
                + " | Voluntário: " + voluntario.getNome()
                + " | Inscrição: " + dataInscricao
                + " | Status: " + status;
    }
}
