package model;

public class Administrador extends Usuario{

    public Administrador(String nome, String email){
        super(nome, email);
    }

    @Override
    public String getTipoUsuario(){
        return "Administrador do Abrigo";
    }

    @Override
    public boolean autenticar(String senha){
        return senha != null && !senha.isBlank();
    }
}
