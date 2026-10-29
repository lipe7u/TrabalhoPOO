package model;

public class Voluntario extends Usuario{

    private int horasTrabalhadas;

    public Voluntario(String nome, String email, int horasTrabalhadas){
        super(nome, email);
        this.horasTrabalhadas = 0;
    }


    public void SetHorasTrabalhadas(int horas){
        if(horas <= 0){
            throw new IllegalArgumentException("Horas inválidas");
        }

        this.horasTrabalhadas += horas;
    }

    public int getHorasTrabalhadas(){ return horasTrabalhadas;}

    @Override
    public String getTipoUsuario(){
        return "Voluntario";
    }

    @Override
    public boolean autenticar(String senha){
        return senha != null && !senha.isBlank();
    }
}
