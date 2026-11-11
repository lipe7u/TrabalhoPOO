package repository;

import model.Animal;
import model.Usuario;
import model.SolicitacaoAdocao;
import model.Adocao;
import model.Consulta;
import model.Vacina;
import model.Medicamento;
import model.Tratamento;
import model.Atividade;
import model.ParticipacaoAtividade;
import model.Notificacao;

import java.util.ArrayList;
import java.util.List;

public class BancoMemoria {

    private static final List<Animal> animais = new ArrayList<>();
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final List<SolicitacaoAdocao> solicitacoes = new ArrayList<>();
    private static final List<Adocao> adocoes = new ArrayList<>();

    private static final List<Consulta> consultas = new ArrayList<>();
    private static final List<Vacina> vacinas = new ArrayList<>();
    private static final List<Medicamento> medicamentos = new ArrayList<>();
    private static final List<Tratamento> tratamentos = new ArrayList<>();

    private static final List<Atividade> atividades = new ArrayList<>();
    private static final List<ParticipacaoAtividade> participacoes = new ArrayList<>();
    private static final List<Notificacao> notificacoes = new ArrayList<>();

    public static List<Animal> getAnimais() {
        return animais;
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    public static List<SolicitacaoAdocao> getSolicitacoes() {
        return solicitacoes;
    }

    public static List<Adocao> getAdocoes() {
        return adocoes;
    }

    public static List<Consulta> getConsultas() {
        return consultas;
    }

    public static List<Vacina> getVacinas() {
        return vacinas;
    }

    public static List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public static List<Tratamento> getTratamentos() {
        return tratamentos;
    }

    public static List<Atividade> getAtividades() {
        return atividades;
    }

    public static List<ParticipacaoAtividade> getParticipacoes() {
        return participacoes;
    }

    public static List<Notificacao> getNotificacoes() {
        return notificacoes;
    }
}
