package repository;

import model.Animal;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class BancoMemoria {

    private static final List<Animal> animais = new ArrayList<>();
    private static final List<Usuario> usuarios = new ArrayList<>();

    public static List<Animal> getAnimais() {
        return animais;
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }
}