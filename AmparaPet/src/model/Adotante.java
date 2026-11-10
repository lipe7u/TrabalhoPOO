package model;

public class Adotante extends Usuario{
    private String tipoMoradia;
    private String preferenciaEspecie;
    private int adocoesAnteriores;

    public Adotante(String nome, String email, String tipoMoradia, String preferenciaEspecie){
        super(nome, email);
        setTipoMoradia(tipoMoradia);
        setPreferenciaEspecie(preferenciaEspecie);
        this.adocoesAnteriores = 0;
    }


    public String getTipoMoradia(){
        return tipoMoradia;
    }

    public void setTipoMoradia(String tipoMoradia){
        if (tipoMoradia == null || tipoMoradia.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe o tipo de moradia."
            );
        }

        this.tipoMoradia = tipoMoradia;
    }

    public String getPreferenciaEspecie() {
        return preferenciaEspecie;
    }

    public void setPreferenciaEspecie(
            String preferenciaEspecie) {

        if (preferenciaEspecie == null
                || preferenciaEspecie.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe a espécie de preferência."
            );
        }

        this.preferenciaEspecie = preferenciaEspecie;
    }

    public int getAdocoesAnteriores() {
        return adocoesAnteriores;
    }

    public void registrarAdocao() {
        adocoesAnteriores++;
    }

    @Override
    public String getTipoUsuario() {
        return "Adotante";
    }

    @Override
    public boolean autenticar(String senha) {
        return senha != null && !senha.isBlank();
    }
}
