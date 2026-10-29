package model;

public class Veterinario extends Usuario {

    private String crmv;

    public Veterinario(String nome, String email, String crmv){
        super(nome, email);
        setCrmv(crmv);
    }


    public void setCrmv(String crmv){
        if(crmv == null || crmv.isBlank()){
            throw new IllegalArgumentException("crmv obrigatório");
        }

        this.crmv = crmv;
    }


    public String getCrmv(){return crmv;}

    @Override
    public String getTipoUsuario(){
        return "Veterinario";
    }



    @Override
    public boolean autenticar(String senha){
        return senha != null && !senha.isBlank();
    }

}
