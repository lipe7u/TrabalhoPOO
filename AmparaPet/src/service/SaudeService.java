
package service;

import model.*;
import model.enums.*;
import repository.BancoMemoria;
import exception.RegraNegocioException;

import java.time.LocalDate;
import java.util.List;

public class SaudeService {

    private void validarVeterinario(Veterinario veterinario) {
        if (veterinario == null ||
                !BancoMemoria.getUsuarios().contains(veterinario)) {
            throw new RegraNegocioException(
                    "Operação permitida somente para veterinários cadastrados."
            );
        }
    }

    private void validarAnimal(Animal animal) {
        if (animal == null ||
                !BancoMemoria.getAnimais().contains(animal)) {
            throw new RegraNegocioException(
                    "Animal não encontrado no sistema."
            );
        }
    }

    public Consulta registrarConsulta(
            Animal animal,
            Veterinario veterinario,
            TipoConsulta tipo,
            String diagnostico,
            String observacoes
    ) {
        validarVeterinario(veterinario);
        validarAnimal(animal);

        Consulta consulta = new Consulta(
                animal, veterinario, tipo, diagnostico, observacoes
        );

        BancoMemoria.getConsultas().add(consulta);

        return consulta;
    }

    public Vacina registrarVacina(
            Animal animal,
            Veterinario veterinario,
            String nome,
            LocalDate dataAplicacao,
            LocalDate dataValidade
    ) {
        validarVeterinario(veterinario);
        validarAnimal(animal);

        boolean duplicada = BancoMemoria.getVacinas().stream()
                .anyMatch(v ->
                        v.getAnimal().getId() == animal.getId()
                                && v.getNome().equalsIgnoreCase(nome)
                                && v.getDataAplicacao().equals(dataAplicacao)
                );

        if (duplicada) {
            throw new RegraNegocioException(
                    "Esta vacina já foi registrada para o animal nessa data."
            );
        }

        Vacina vacina = new Vacina(
                animal, veterinario, nome, dataAplicacao, dataValidade
        );

        BancoMemoria.getVacinas().add(vacina);

        return vacina;
    }

    public Medicamento registrarMedicamento(
            Animal animal,
            Veterinario veterinario,
            String nome,
            TipoMedicamento tipo,
            String dosagem,
            int duracaoDias
    ) {
        validarVeterinario(veterinario);
        validarAnimal(animal);

        Medicamento medicamento = new Medicamento(
                animal, veterinario, nome, tipo, dosagem, duracaoDias
        );

        BancoMemoria.getMedicamentos().add(medicamento);

        return medicamento;
    }

    public Tratamento iniciarTratamento(
            Animal animal,
            Veterinario veterinario,
            String descricao
    ) {
        validarVeterinario(veterinario);
        validarAnimal(animal);

        Tratamento tratamento = new Tratamento(
                animal, veterinario, descricao
        );

        BancoMemoria.getTratamentos().add(tratamento);

        animal.atualizarEstadoSaude(EstadoSaude.EM_TRATAMENTO, veterinario);

        return tratamento;
    }

    public void concluirTratamento(
            int idTratamento,
            Veterinario veterinario
    ) {
        validarVeterinario(veterinario);

        Tratamento tratamento = buscarTratamentoPorId(idTratamento);

        if (tratamento.getVeterinario().getId() != veterinario.getId()) {
            throw new RegraNegocioException(
                    "Somente o veterinário responsável pode concluir este tratamento."
            );
        }

        tratamento.concluir();
    }

    public void interromperTratamento(
            int idTratamento,
            Veterinario veterinario
    ) {
        validarVeterinario(veterinario);

        Tratamento tratamento = buscarTratamentoPorId(idTratamento);

        if (tratamento.getVeterinario().getId() != veterinario.getId()) {
            throw new RegraNegocioException(
                    "Somente o veterinário responsável pode interromper este tratamento."
            );
        }

        tratamento.interromper();
    }

    public void atualizarEstadoSaude(
            Animal animal,
            Veterinario veterinario,
            EstadoSaude novoEstado
    ) {
        validarVeterinario(veterinario);
        validarAnimal(animal);

        if (novoEstado == null) {
            throw new IllegalArgumentException(
                    "O novo estado de saúde é obrigatório."
            );
        }

        animal.atualizarEstadoSaude(novoEstado, veterinario);
    }

    public Tratamento buscarTratamentoPorId(int id) {
        return BancoMemoria.getTratamentos().stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RegraNegocioException(
                        "Tratamento não encontrado."
                ));
    }

    public List<Consulta> listarConsultasPorAnimal(int idAnimal) {
        return BancoMemoria.getConsultas().stream()
                .filter(c -> c.getAnimal().getId() == idAnimal)
                .toList();
    }

    public List<Vacina> listarVacinasPorAnimal(int idAnimal) {
        return BancoMemoria.getVacinas().stream()
                .filter(v -> v.getAnimal().getId() == idAnimal)
                .toList();
    }

    public List<Medicamento> listarMedicamentosPorAnimal(int idAnimal) {
        return BancoMemoria.getMedicamentos().stream()
                .filter(m -> m.getAnimal().getId() == idAnimal)
                .toList();
    }

    public List<Tratamento> listarTratamentosPorAnimal(int idAnimal) {
        return BancoMemoria.getTratamentos().stream()
                .filter(t -> t.getAnimal().getId() == idAnimal)
                .toList();
    }

    public List<Tratamento> listarTratamentosEmAndamento() {
        return BancoMemoria.getTratamentos().stream()
                .filter(t -> t.getStatus() == StatusTratamento.EM_ANDAMENTO)
                .toList();
    }

    public List<Vacina> listarVacinasVencidas() {
        return BancoMemoria.getVacinas().stream()
                .filter(Vacina::estaVencida)
                .toList();
    }

    public List<Vacina> listarVacinasProximasDoVencimento(int dias) {
        if (dias < 0) {
            throw new IllegalArgumentException(
                    "O número de dias não pode ser negativo."
            );
        }

        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(dias);

        return BancoMemoria.getVacinas().stream()
                .filter(v -> !v.getDataValidade().isBefore(hoje))
                .filter(v -> !v.getDataValidade().isAfter(limite))
                .toList();
    }
}
