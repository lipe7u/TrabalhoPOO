
package service;

import model.Adotante;
import model.Animal;
import model.ResultadoCompatibilidade;
import model.enums.EstadoSaude;
import model.enums.Porte;
import model.enums.StatusAdocao;
import repository.BancoMemoria;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;

public class CompatibilidadeService {

    public List<ResultadoCompatibilidade> recomendarAnimais(Adotante adotante) {

        if (adotante == null) {
            throw new IllegalArgumentException("Adotante não informado.");
        }

        return BancoMemoria.getAnimais().stream()

                // Considera somente animais disponíveis e não críticos.
                .filter(animal ->
                        animal.getStatusAdocao() == StatusAdocao.DISPONIVEL
                )
                .filter(animal ->
                        animal.getEstadoSaude() != EstadoSaude.CRITICO
                )

                // Calcula a compatibilidade.
                .map(animal -> new ResultadoCompatibilidade(
                        animal,
                        calcularPontuacao(adotante, animal)
                ))

                // Ordena do maior percentual para o menor.
                .sorted(Comparator.comparingInt(
                        ResultadoCompatibilidade::getPontuacao
                ).reversed())

                .toList();
    }

    public int calcularPontuacao(Adotante adotante, Animal animal) {

        if (adotante == null || animal == null) {
            throw new IllegalArgumentException(
                    "Adotante e animal devem ser informados."
            );
        }

        int pontuacao = 0;

        pontuacao += avaliarEspecie(adotante, animal);
        pontuacao += avaliarMoradia(adotante, animal);

        return pontuacao;
    }

    private int avaliarEspecie(Adotante adotante, Animal animal) {

        String preferencia = normalizar(
                adotante.getPreferenciaEspecie()
        );

        String especieAnimal = normalizar(
                animal.getEspecie().name()
        );

        // O adotante aceita qualquer espécie.
        if (preferencia.equals("qualquer")
                || preferencia.equals("indiferente")) {
            return 50;
        }

        // Verifica se a preferência corresponde à espécie.
        if (preferencia.equals(especieAnimal)) {
            return 50;
        }

        return 0;
    }

    private int avaliarMoradia(Adotante adotante, Animal animal) {

        String moradia = normalizar(adotante.getTipoMoradia());

        Porte porte = animal.getPorte();

        // Apartamentos são mais adequados para animais pequenos.
        if (moradia.contains("apartamento")) {

            if (porte == Porte.PEQUENO) {
                return 50;
            }

            if (porte == Porte.MEDIO) {
                return 25;
            }

            return 10;
        }

        // Casas com quintal oferecem mais espaço.
        if (moradia.contains("quintal")
                || moradia.contains("terreno")) {
            return 50;
        }

        // Casas sem informação sobre quintal.
        if (moradia.contains("casa")) {

            if (porte == Porte.PEQUENO) {
                return 40;
            }

            if (porte == Porte.MEDIO) {
                return 30;
            }

            return 20;
        }

        // Tipo de moradia não reconhecido.
        return 25;
    }

    private String normalizar(String texto) {

        if (texto == null) {
            return "";
        }

        return Normalizer.normalize(
                texto.trim().toLowerCase(),
                Normalizer.Form.NFD
        ).replaceAll("\\p{M}", "");
    }

    public void imprimirRecomendacoes(Adotante adotante) {

        List<ResultadoCompatibilidade> resultados =
                recomendarAnimais(adotante);

        System.out.println("\n========================================");
        System.out.println("    RECOMENDACOES DE ADOCACAO");
        System.out.println("========================================");

        if (resultados.isEmpty()) {
            System.out.println(
                    "Nenhum animal disponivel para recomendacao."
            );
            return;
        }

        for (ResultadoCompatibilidade resultado : resultados) {
            System.out.println(resultado);
        }

        System.out.println("========================================");
    }
}
