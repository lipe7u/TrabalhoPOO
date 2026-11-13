
package model;

public class ResultadoCompatibilidade {

    private final Animal animal;
    private final int pontuacao;

    public ResultadoCompatibilidade(Animal animal, int pontuacao) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal não informado.");
        }

        if (pontuacao < 0 || pontuacao > 100) {
            throw new IllegalArgumentException("Pontuação inválida.");
        }

        this.animal = animal;
        this.pontuacao = pontuacao;
    }

    public Animal getAnimal() {
        return animal;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    @Override
    public String toString() {
        return "Animal: " + animal.getNome()
                + " | Compatibilidade: " + pontuacao + "%";
    }
}
