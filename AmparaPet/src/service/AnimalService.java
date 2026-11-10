package service;

import exception.AnimalNaoEncontradoException;
import exception.RegraNegocioException;
import model.Animal;
import model.enums.*;

import repository.BancoMemoria;

import java.util.ArrayList;
import java.util.List;

public class AnimalService {

    public void cadastrarAnimal(Animal animal) {

        if (animal == null) {
            throw new RegraNegocioException(
                    "O animal não pode ser nulo."
            );
        }

        BancoMemoria.getAnimais().add(animal);
    }

    public Animal buscarPorId(int id) {

        return BancoMemoria.getAnimais()
                .stream()
                .filter(animal -> animal.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new AnimalNaoEncontradoException(
                                "Animal não encontrado."
                        )
                );
    }

    public List<Animal> listarTodos() {
        return new ArrayList<>(BancoMemoria.getAnimais());
    }

    public List<Animal> buscarPorNome(String nome) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe o nome para realizar a busca."
            );
        }

        return BancoMemoria.getAnimais()
                .stream()
                .filter(animal ->
                        animal.getNome()
                                .toLowerCase()
                                .contains(nome.toLowerCase()))
                .toList();
    }

    public List<Animal> filtrarPorEspecie(Especie especie) {

        return BancoMemoria.getAnimais()
                .stream()
                .filter(animal -> animal.getEspecie() == especie)
                .toList();
    }

    public List<Animal> filtrarPorPorte(Porte porte) {

        return BancoMemoria.getAnimais()
                .stream()
                .filter(animal -> animal.getPorte() == porte)
                .toList();
    }

    public List<Animal> filtrarPorSaude(EstadoSaude estadoSaude) {

        return BancoMemoria.getAnimais()
                .stream()
                .filter(animal ->
                        animal.getEstadoSaude() == estadoSaude)
                .toList();
    }

    public List<Animal> filtrarPorStatus(StatusAdocao status) {

        return BancoMemoria.getAnimais()
                .stream()
                .filter(animal -> animal.getStatusAdocao() == status)
                .toList();
    }

    public void atualizarAnimal(
            int id,
            String nome,
            String raca,
            int idade,
            Porte porte,
            EstadoSaude estadoSaude) {

        Animal animal = buscarPorId(id);

        animal.setNome(nome);
        animal.setRaca(raca);
        animal.setIdade(idade);
        animal.setPorte(porte);
        animal.setEstadoSaude(estadoSaude);
    }

    public void removerAnimal(int id) {

        Animal animal = buscarPorId(id);

        if (animal.getStatusAdocao() == StatusAdocao.ADOTADO) {
            throw new RegraNegocioException(
                    "Não é permitido remover um animal adotado do histórico."
            );
        }

        BancoMemoria.getAnimais().remove(animal);
    }

    public void disponibilizarParaAdocao(int id) {

        Animal animal = buscarPorId(id);

        if (animal.getEstadoSaude() == EstadoSaude.CRITICO) {
            throw new RegraNegocioException(
                    "Animal em estado crítico não pode ser disponibilizado."
            );
        }

        if (animal.getStatusAdocao() == StatusAdocao.ADOTADO) {
            throw new RegraNegocioException(
                    "Animal já foi adotado."
            );
        }

        animal.setStatusAdocao(StatusAdocao.DISPONIVEL);
    }
}