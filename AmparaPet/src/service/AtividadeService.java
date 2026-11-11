
package service;

import model.*;
import model.enums.*;
import repository.BancoMemoria;
import exception.RegraNegocioException;

import java.util.List;

public class AtividadeService {

    private final NotificacaoService notificacaoService =
            new NotificacaoService();

    private void validarAdministrador(Administrador administrador) {
        if (administrador == null ||
                !BancoMemoria.getUsuarios().contains(administrador)) {
            throw new RegraNegocioException(
                    "Operação permitida somente para administradores cadastrados."
            );
        }
    }

    private void validarVoluntario(Voluntario voluntario) {
        if (voluntario == null ||
                !BancoMemoria.getUsuarios().contains(voluntario)) {
            throw new RegraNegocioException(
                    "Voluntário não cadastrado no sistema."
            );
        }
    }

    public Atividade cadastrarAtividade(
            String nome,
            String descricao,
            java.time.LocalDate data,
            String local,
            int limiteParticipantes,
            Administrador administrador
    ) {
        validarAdministrador(administrador);

        Atividade atividade = new Atividade(
                nome,
                descricao,
                data,
                local,
                limiteParticipantes,
                administrador
        );

        BancoMemoria.getAtividades().add(atividade);

        // Notifica os voluntários sobre a nova atividade.
        for (Usuario usuario : BancoMemoria.getUsuarios()) {
            if (usuario instanceof Voluntario) {
                notificacaoService.enviarNotificacao(
                        usuario,
                        "Nova atividade cadastrada: " + atividade.getNome()
                                + ". Data: " + atividade.getData()
                                + ". Local: " + atividade.getLocal(),
                        TipoNotificacao.ATIVIDADE
                );
            }
        }

        return atividade;
    }

    public void inscreverVoluntario(
            int idAtividade,
            Voluntario voluntario
    ) {
        validarVoluntario(voluntario);

        Atividade atividade = buscarPorId(idAtividade);

        if (atividade.getStatus() != StatusAtividade.AGENDADA) {
            throw new RegraNegocioException(
                    "Não é possível se inscrever nesta atividade."
            );
        }

        boolean jaInscrito = BancoMemoria.getParticipacoes().stream()
                .anyMatch(p ->
                        p.getAtividade().getId() == idAtividade
                                && p.getVoluntario().getId() == voluntario.getId()
                                && p.getStatus() != StatusParticipacao.CANCELADO
                );

        if (jaInscrito) {
            throw new RegraNegocioException(
                    "O voluntário já está inscrito nesta atividade."
            );
        }

        long inscritos = BancoMemoria.getParticipacoes().stream()
                .filter(p -> p.getAtividade().getId() == idAtividade)
                .filter(p -> p.getStatus() == StatusParticipacao.INSCRITO)
                .count();

        if (inscritos >= atividade.getLimiteParticipantes()) {
            throw new RegraNegocioException(
                    "Não há vagas disponíveis nesta atividade."
            );
        }

        ParticipacaoAtividade participacao =
                new ParticipacaoAtividade(atividade, voluntario);

        BancoMemoria.getParticipacoes().add(participacao);

        notificacaoService.enviarNotificacao(
                voluntario,
                "Sua inscrição na atividade "
                        + atividade.getNome() + " foi realizada.",
                TipoNotificacao.ATIVIDADE
        );
    }

    public void iniciarAtividade(
            int idAtividade,
            Administrador administrador
    ) {
        validarAdministrador(administrador);

        Atividade atividade = buscarPorId(idAtividade);

        atividade.iniciar();
    }

    public void concluirAtividade(
            int idAtividade,
            Administrador administrador
    ) {
        validarAdministrador(administrador);

        Atividade atividade = buscarPorId(idAtividade);

        atividade.concluir();
    }

    public void cancelarAtividade(
            int idAtividade,
            Administrador administrador
    ) {
        validarAdministrador(administrador);

        Atividade atividade = buscarPorId(idAtividade);

        atividade.cancelar();

        for (ParticipacaoAtividade participacao :
                BancoMemoria.getParticipacoes()) {

            if (participacao.getAtividade().getId() == idAtividade
                    && participacao.getStatus() == StatusParticipacao.INSCRITO) {

                participacao.cancelar();

                notificacaoService.enviarNotificacao(
                        participacao.getVoluntario(),
                        "A atividade " + atividade.getNome()
                                + " foi cancelada.",
                        TipoNotificacao.ATIVIDADE
                );
            }
        }
    }

    public void registrarPresenca(
            int idParticipacao,
            int horas,
            Administrador administrador
    ) {
        validarAdministrador(administrador);

        if (horas <= 0) {
            throw new IllegalArgumentException(
                    "As horas devem ser maiores que zero."
            );
        }

        ParticipacaoAtividade participacao =
                buscarParticipacaoPorId(idParticipacao);

        if (participacao.getAtividade().getStatus()
                != StatusAtividade.CONCLUIDA) {
            throw new RegraNegocioException(
                    "A atividade precisa estar concluída para registrar a presença."
            );
        }

        participacao.registrarPresenca();

        participacao.getVoluntario().SetHorasTrabalhadas(horas);
    }

    public void registrarAusencia(
            int idParticipacao,
            Administrador administrador
    ) {
        validarAdministrador(administrador);

        ParticipacaoAtividade participacao =
                buscarParticipacaoPorId(idParticipacao);

        if (participacao.getAtividade().getStatus()
                != StatusAtividade.CONCLUIDA) {
            throw new RegraNegocioException(
                    "A atividade precisa estar concluída para registrar a ausência."
            );
        }

        participacao.registrarAusencia();
    }

    public Atividade buscarPorId(int id) {
        return BancoMemoria.getAtividades().stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RegraNegocioException(
                        "Atividade não encontrada."
                ));
    }

    public ParticipacaoAtividade buscarParticipacaoPorId(int id) {
        return BancoMemoria.getParticipacoes().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RegraNegocioException(
                        "Participação não encontrada."
                ));
    }

    public List<Atividade> listarAtividades() {
        return BancoMemoria.getAtividades().stream().toList();
    }

    public List<Atividade> listarAtividadesAgendadas() {
        return BancoMemoria.getAtividades().stream()
                .filter(a -> a.getStatus() == StatusAtividade.AGENDADA)
                .toList();
    }

    public List<ParticipacaoAtividade> listarParticipacoesPorAtividade(
            int idAtividade
    ) {
        return BancoMemoria.getParticipacoes().stream()
                .filter(p -> p.getAtividade().getId() == idAtividade)
                .toList();
    }

    public List<ParticipacaoAtividade> listarParticipacoesPorVoluntario(
            int idVoluntario
    ) {
        return BancoMemoria.getParticipacoes().stream()
                .filter(p -> p.getVoluntario().getId() == idVoluntario)
                .toList();
    }
}
