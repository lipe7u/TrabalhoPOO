package repository;

import model.Animal;
import model.Usuario;
import model.SolicitacaoAdocao;
import model.Adocao;

import java.util.ArrayList;
import java.util.List;

public class BancoMemoria {

    private static final List<Animal> animais = new ArrayList<>();

    private static final List<Usuario> usuarios = new ArrayList<>();

    private static final List<SolicitacaoAdocao> solicitacoes =
            new ArrayList<>();

    private static final List<Adocao> adocoes = new ArrayList<>();

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
}