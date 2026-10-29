package model;

import interfaces.Autenticavel;
import util.ValidarEmail;

public abstract class Usuario implements Autenticavel {
    private static int proximoId = 1;


    private final int id;
    private String nome;
    private  String email;

    public Usuario(String nome, String email){
        this.id = proximoId++;
        setEmail(email);
        setNome(nome);
    }


    public void setNome(String nome){
        if(nome == null|| nome.isBlank()){
            throw new IllegalArgumentException("Nome inválido");
        }

        this.nome = nome;
    }

    public void setEmail(String email){
        if(ValidarEmail.Validador(email)){
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    public int getId(){return id;}
    public String getNome(){return nome;}
    public String getEmail(){return email;}


    public abstract String getTipoUsuario();

    public static int getTotalIdsGerados(){
        return proximoId - 1;
    }
}
