package service;

import exception.RegraNegocioException;
import exception.SolicitacaoNaoEncontradaException;

import model.Animal;
import model.Adocao;
import model.Adotante;
import model.Administrador;
import model.SolicitacaoAdocao;

import model.enums.EstadoSaude;
import model.enums.StatusAdocao;
import model.enums.StatusSolicitacao;

import repository.BancoMemoria;

import java.util.ArrayList;
import java.util.List;

public class AdocaoService {

    // Solicitar adoção
    public void solicitarAdocao(Animal animal, Adotante adotante) {
        if (animal == null || adotante == null) {
            throw new RegraNegocioException(
                    "Animal e adotante são obrigatórios."
            );
        }

        if (!BancoMemoria.getAnimais().contains(animal)) {
            throw new RegraNegocioException(
                    "Animal não cadastrado no sistema."
            );
        }

        if (!BancoMemoria.getUsuarios().contains(adotante)) {
            throw new RegraNegocioException(
                    "Adotante não cadastrado no sistema."
            );
        }

        if (animal.getEstadoSaude() == EstadoSaude.CRITICO) {
            throw new RegraNegocioException(
                    "Animais em estado crítico não podem receber solicitações."
            );
        }

        if (animal.getStatusAdocao() != StatusAdocao.DISPONIVEL
                && animal.getStatusAdocao() != StatusAdocao.EM_PROCESSO) {

            throw new RegraNegocioException(
                    "Este animal não está disponível para adoção."
            );
        }

        for (SolicitacaoAdocao solicitacao :
                BancoMemoria.getSolicitacoes()) {

            boolean mesmoAnimal =
                    solicitacao.getAnimal().getId() == animal.getId();

            boolean mesmoAdotante =
                    solicitacao.getAdotante().getId() == adotante.getId();

            boolean pendente =
                    solicitacao.getStatus() == StatusSolicitacao.PENDENTE;

            if (mesmoAnimal && mesmoAdotante && pendente) {
                throw new RegraNegocioException(
                        "Você já possui uma solicitação pendente para este animal."
                );
            }
        }

        SolicitacaoAdocao solicitacao =
                new SolicitacaoAdocao(animal, adotante);

        BancoMemoria.getSolicitacoes().add(solicitacao);

        animal.setStatusAdocao(StatusAdocao.EM_PROCESSO);

        System.out.println(
                "Solicitação cadastrada com sucesso!"
        );
    }

    //Buscar solicitação por ID
    public SolicitacaoAdocao buscarSolicitacaoPorId(int id) {

        return BancoMemoria.getSolicitacoes()
                .stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new SolicitacaoNaoEncontradaException(
                                "Solicitação não encontrada."
                        )
                );
    }

    // Listar todas as solicitações
    public List<SolicitacaoAdocao> listarSolicitacoes() {
        return new ArrayList<>(
                BancoMemoria.getSolicitacoes()
        );
    }

    //Listar solicitações pendentes
    public List<SolicitacaoAdocao> listarPendentes() {

        return BancoMemoria.getSolicitacoes()
                .stream()
                .filter(s ->
                        s.getStatus() == StatusSolicitacao.PENDENTE)
                .toList();
    }

    //Listar solicitações de um animal
    public List<SolicitacaoAdocao> listarPorAnimal(int idAnimal) {

        return BancoMemoria.getSolicitacoes()
                .stream()
                .filter(s ->
                        s.getAnimal().getId() == idAnimal)
                .toList();
    }

    // Listar solicitações de um adotante
    public List<SolicitacaoAdocao> listarPorAdotante(int idAdotante) {

        return BancoMemoria.getSolicitacoes()
                .stream()
                .filter(s ->
                        s.getAdotante().getId() == idAdotante)
                .toList();
    }

    // Aprovar adoção
    public Adocao aprovarSolicitacao(int idSolicitacao, Administrador administrador) {
        if (administrador == null ||
                !BancoMemoria.getUsuarios().contains(administrador)) {

            throw new RegraNegocioException(
                    "Somente um administrador cadastrado pode aprovar adoções."
            );
        }

        SolicitacaoAdocao solicitacao =
                buscarSolicitacaoPorId(idSolicitacao);

        if (solicitacao.getStatus() != StatusSolicitacao.PENDENTE) {
            throw new RegraNegocioException(
                    "Esta solicitação não está pendente."
            );
        }

        Animal animal = solicitacao.getAnimal();

        if (animal.getEstadoSaude() == EstadoSaude.CRITICO) {
            throw new RegraNegocioException(
                    "Não é possível adotar um animal em estado crítico."
            );
        }

        if (animal.getStatusAdocao() != StatusAdocao.EM_PROCESSO) {
            throw new RegraNegocioException(
                    "O animal não está em processo de adoção."
            );
        }

        boolean jaPossuiAdocaoAtiva =
                BancoMemoria.getAdocoes()
                        .stream()
                        .anyMatch(a ->
                                a.getAnimal().getId() == animal.getId()
                                        && !a.foiDevolvido());

        if (jaPossuiAdocaoAtiva) {
            throw new RegraNegocioException(
                    "Este animal já possui uma adoção ativa."
            );
        }

        Adocao adocao = new Adocao(
                animal,
                solicitacao.getAdotante(),
                administrador
        );

        solicitacao.aprovar();

        animal.setStatusAdocao(StatusAdocao.ADOTADO);

        solicitacao.getAdotante().registrarAdocao();

        BancoMemoria.getAdocoes().add(adocao);

        // Encerrar os demais pedidos pendentes deste animal.

        for (SolicitacaoAdocao outra :
                BancoMemoria.getSolicitacoes()) {

            if (outra.getAnimal().getId() == animal.getId()
                    && outra.getStatus() == StatusSolicitacao.PENDENTE) {

                outra.cancelar();
            }
        }

        System.out.println("Adoção aprovada com sucesso!");

        return adocao;
    }

    //Rejeitar solicitação
    public void rejeitarSolicitacao(int idSolicitacao, Administrador administrador) {
        validarAdministrador(administrador);

        SolicitacaoAdocao solicitacao =
                buscarSolicitacaoPorId(idSolicitacao);

        if (solicitacao.getStatus() != StatusSolicitacao.PENDENTE) {
            throw new RegraNegocioException(
                    "Somente solicitações pendentes podem ser rejeitadas."
            );
        }

        Animal animal = solicitacao.getAnimal();

        solicitacao.rejeitar();

        boolean possuiOutraPendente =
                BancoMemoria.getSolicitacoes()
                        .stream()
                        .anyMatch(s ->
                                s.getAnimal().getId() == animal.getId()
                                        && s.getStatus() == StatusSolicitacao.PENDENTE);

        if (!possuiOutraPendente
                && animal.getEstadoSaude() != EstadoSaude.CRITICO) {

            animal.setStatusAdocao(StatusAdocao.DISPONIVEL);
        }

        System.out.println("Solicitação rejeitada.");
    }

    //Registrar devolução
    public void registrarDevolucao(
            int idAdocao,
            Administrador administrador,
            String motivo) {

        validarAdministrador(administrador);

        Adocao adocao = buscarAdocaoPorId(idAdocao);

        if (adocao.foiDevolvido()) {
            throw new RegraNegocioException(
                    "Esta adoção já foi devolvida."
            );
        }

        adocao.registrarDevolucao(motivo);

        Animal animal = adocao.getAnimal();

        if (animal.getEstadoSaude() == EstadoSaude.CRITICO) {
            animal.setStatusAdocao(StatusAdocao.INDISPONIVEL);
        } else {
            animal.setStatusAdocao(StatusAdocao.DISPONIVEL);
        }

        System.out.println(
                "Devolução registrada com sucesso!"
        );
    }

    //Buscar adoção por ID
    public Adocao buscarAdocaoPorId(int id) {
        return BancoMemoria.getAdocoes()
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Adoção não encontrada."
                        )
                );
    }

    //Listar histórico completo
    public List<Adocao> listarHistoricoAdocoes() {
        return new ArrayList<>(
                BancoMemoria.getAdocoes()
        );
    }

    // Listar adoções de um adotante
    public List<Adocao> listarAdocoesPorAdotante(int idAdotante) {
        return BancoMemoria.getAdocoes()
                .stream()
                .filter(a ->
                        a.getAdotante().getId() == idAdotante)
                .toList();
    }

    // Validar administrador
    private void validarAdministrador(Administrador administrador) {
        if (administrador == null ||
                !BancoMemoria.getUsuarios().contains(administrador)) {

            throw new RegraNegocioException(
                    "Operação permitida somente para administradores cadastrados."
            );
        }
    }
}