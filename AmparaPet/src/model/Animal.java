package model;

import model.enums.Especie;
import model.enums.Porte;
import model.enums.EstadoSaude;
import model.enums.StatusAdocao;

import java.time.LocalDate;

public class Animal {
    private static int proximoId = 1;

    private final int id;
    private String nome;
    private Especie especie;
    private String raca;
    private int idade;
    private Porte porte;
    private EstadoSaude estadoSaude;
    private final LocalDate dataResgate;
    private StatusAdocao statusAdocao;

    public Animal(
            String nome,
            Especie especie,
            String raca,
            int idade,
            Porte porte,
            EstadoSaude estadoSaude,
            LocalDate dataResgate) {

        this.id = proximoId++;

        setNome(nome);
        setEspecie(especie);
        setRaca(raca);
        setIdade(idade);
        setPorte(porte);
        setEstadoSaude(estadoSaude);

        if (dataResgate == null || dataResgate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de resgate inválida.");
        }

        this.dataResgate = dataResgate;

        this.statusAdocao = estadoSaude == EstadoSaude.CRITICO
                ? StatusAdocao.INDISPONIVEL
                : StatusAdocao.DISPONIVEL;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do animal é obrigatório."
            );
        }

        this.nome = nome;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        if (especie == null) {
            throw new IllegalArgumentException(
                    "A espécie é obrigatória."
            );
        }

        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        if (raca == null || raca.isBlank()) {
            throw new IllegalArgumentException(
                    "A raça é obrigatória."
            );
        }

        this.raca = raca;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 0) {
            throw new IllegalArgumentException(
                    "A idade não pode ser negativa."
            );
        }

        this.idade = idade;
    }

    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        if (porte == null) {
            throw new IllegalArgumentException(
                    "O porte é obrigatório."
            );
        }

        this.porte = porte;
    }

    public EstadoSaude getEstadoSaude() {
        return estadoSaude;
    }

    public void setEstadoSaude(EstadoSaude estadoSaude) {
        if (estadoSaude == null) {
            throw new IllegalArgumentException(
                    "O estado de saúde é obrigatório."
            );
        }

        this.estadoSaude = estadoSaude;

        if (estadoSaude == EstadoSaude.CRITICO) {
            this.statusAdocao = StatusAdocao.INDISPONIVEL;
        } else if (this.statusAdocao == StatusAdocao.INDISPONIVEL) {
            this.statusAdocao = StatusAdocao.DISPONIVEL;
        }
    }

    public LocalDate getDataResgate() {
        return dataResgate;
    }


    public StatusAdocao getStatusAdocao() {
        return statusAdocao;
    }

    public void setStatusAdocao(StatusAdocao statusAdocao) {
        if (statusAdocao == null) {
            throw new IllegalArgumentException(
                    "O status de adoção é obrigatório."
            );
        }

        if (estadoSaude == EstadoSaude.CRITICO
                && statusAdocao == StatusAdocao.DISPONIVEL) {
            throw new IllegalStateException(
                    "Animais em estado crítico não podem ser disponibilizados."
            );
        }

        this.statusAdocao = statusAdocao;
    }

    public static int getTotalIdsGerados() {
        return proximoId - 1;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + " | Nome: " + nome
                + " | Espécie: " + especie
                + " | Raça: " + raca
                + " | Idade: " + idade
                + " | Porte: " + porte
                + " | Saúde: " + estadoSaude
                + " | Resgate: " + dataResgate
                + " | Adoção: " + statusAdocao;
    }
}
