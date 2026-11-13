
package service;

import model.*;
import model.enums.*;
import repository.BancoMemoria;

public class RelatorioService {

    // RELATORIO DE ANIMAIS
    public int totalAnimais() {
        return BancoMemoria.getAnimais().size();
    }

    public long contarAnimaisPorStatus(StatusAdocao status) {
        return BancoMemoria.getAnimais().stream()
                .filter(animal -> animal.getStatusAdocao() == status)
                .count();
    }

    public long contarAnimaisPorSaude(EstadoSaude estado) {
        return BancoMemoria.getAnimais().stream()
                .filter(animal -> animal.getEstadoSaude() == estado)
                .count();
    }

    // RELATORIO DE ADOCOES
    public int totalSolicitacoes() {
        return BancoMemoria.getSolicitacoes().size();
    }

    public long contarSolicitacoesPorStatus(StatusSolicitacao status) {
        return BancoMemoria.getSolicitacoes().stream()
                .filter(s -> s.getStatus() == status)
                .count();
    }

    public int totalAdocoes() {
        return BancoMemoria.getAdocoes().size();
    }

    // RELATORIO DE SAUDE
    public int totalConsultas() {
        return BancoMemoria.getConsultas().size();
    }

    public int totalVacinas() {
        return BancoMemoria.getVacinas().size();
    }

    public int totalMedicamentos() {
        return BancoMemoria.getMedicamentos().size();
    }

    public int totalTratamentos() {
        return BancoMemoria.getTratamentos().size();
    }

    public long contarTratamentosPorStatus(StatusTratamento status) {
        return BancoMemoria.getTratamentos().stream()
                .filter(t -> t.getStatus() == status)
                .count();
    }

    // RELATORIO DE USUARIOS
    public int totalUsuarios() {
        return BancoMemoria.getUsuarios().size();
    }

    public long totalVoluntarios() {
        return BancoMemoria.getUsuarios().stream()
                .filter(u -> u instanceof Voluntario)
                .count();
    }

    public long totalVeterinarios() {
        return BancoMemoria.getUsuarios().stream()
                .filter(u -> u instanceof Veterinario)
                .count();
    }

    public long totalAdministradores() {
        return BancoMemoria.getUsuarios().stream()
                .filter(u -> u instanceof Administrador)
                .count();
    }

    public long totalAdotantes() {
        return BancoMemoria.getUsuarios().stream()
                .filter(u -> u instanceof Adotante)
                .count();
    }

    public int totalHorasVoluntarios() {
        return BancoMemoria.getUsuarios().stream()
                .filter(u -> u instanceof Voluntario)
                .map(u -> (Voluntario) u)
                .mapToInt(Voluntario::getHorasTrabalhadas)
                .sum();
    }

    // RELATORIO DE ATIVIDADE
    public int totalAtividades() {
        return BancoMemoria.getAtividades().size();
    }

    public long contarAtividadesPorStatus(StatusAtividade status) {
        return BancoMemoria.getAtividades().stream()
                .filter(a -> a.getStatus() == status)
                .count();
    }

    public int totalParticipacoes() {
        return BancoMemoria.getParticipacoes().size();
    }

    public long contarParticipacoesPorStatus(StatusParticipacao status) {
        return BancoMemoria.getParticipacoes().stream()
                .filter(p -> p.getStatus() == status)
                .count();
    }

    // RELATORIO DE NOTIFICACOES
    public int totalNotificacoes() {
        return BancoMemoria.getNotificacoes().size();
    }

    public long totalNotificacoesLidas() {
        return BancoMemoria.getNotificacoes().stream()
                .filter(Notificacao::isLida)
                .count();
    }

    public long totalNotificacoesNaoLidas() {
        return BancoMemoria.getNotificacoes().stream()
                .filter(n -> !n.isLida())
                .count();
    }


    // RELATORIO GERAL
    public String gerarRelatorioGeral() {

        StringBuilder relatorio = new StringBuilder();

        relatorio.append("\n========================================\n");
        relatorio.append("       RELATORIO GERAL DO ABRIGO\n");
        relatorio.append("========================================\n");

        relatorio.append("\n--- ANIMAIS ---\n");
        relatorio.append("Total de animais: ").append(totalAnimais()).append("\n");
        relatorio.append("Disponiveis: ")
                .append(contarAnimaisPorStatus(StatusAdocao.DISPONIVEL)).append("\n");
        relatorio.append("Em processo de adocao: ")
                .append(contarAnimaisPorStatus(StatusAdocao.EM_PROCESSO)).append("\n");
        relatorio.append("Adotados: ")
                .append(contarAnimaisPorStatus(StatusAdocao.ADOTADO)).append("\n");
        relatorio.append("Indisponiveis: ")
                .append(contarAnimaisPorStatus(StatusAdocao.INDISPONIVEL)).append("\n");

        relatorio.append("Saudaveis: ")
                .append(contarAnimaisPorSaude(EstadoSaude.SAUDAVEL)).append("\n");
        relatorio.append("Em tratamento: ")
                .append(contarAnimaisPorSaude(EstadoSaude.EM_TRATAMENTO)).append("\n");
        relatorio.append("Em estado critico: ")
                .append(contarAnimaisPorSaude(EstadoSaude.CRITICO)).append("\n");

        relatorio.append("\n--- ADOCOES ---\n");
        relatorio.append("Solicitacoes: ").append(totalSolicitacoes()).append("\n");
        relatorio.append("Pendentes: ")
                .append(contarSolicitacoesPorStatus(StatusSolicitacao.PENDENTE)).append("\n");
        relatorio.append("Aprovadas: ")
                .append(contarSolicitacoesPorStatus(StatusSolicitacao.APROVADA)).append("\n");
        relatorio.append("Rejeitadas: ")
                .append(contarSolicitacoesPorStatus(StatusSolicitacao.REJEITADA)).append("\n");
        relatorio.append("Canceladas: ")
                .append(contarSolicitacoesPorStatus(StatusSolicitacao.CANCELADA)).append("\n");
        relatorio.append("Adocoes realizadas: ").append(totalAdocoes()).append("\n");

        relatorio.append("\n--- SAUDE ---\n");
        relatorio.append("Consultas: ").append(totalConsultas()).append("\n");
        relatorio.append("Vacinas: ").append(totalVacinas()).append("\n");
        relatorio.append("Medicamentos: ").append(totalMedicamentos()).append("\n");
        relatorio.append("Tratamentos: ").append(totalTratamentos()).append("\n");
        relatorio.append("Tratamentos em andamento: ")
                .append(contarTratamentosPorStatus(StatusTratamento.EM_ANDAMENTO)).append("\n");
        relatorio.append("Tratamentos concluidos: ")
                .append(contarTratamentosPorStatus(StatusTratamento.CONCLUIDO)).append("\n");
        relatorio.append("Tratamentos interrompidos: ")
                .append(contarTratamentosPorStatus(StatusTratamento.INTERROMPIDO)).append("\n");

        relatorio.append("\n--- USUARIOS ---\n");
        relatorio.append("Total de usuarios: ").append(totalUsuarios()).append("\n");
        relatorio.append("Voluntarios: ").append(totalVoluntarios()).append("\n");
        relatorio.append("Veterinarios: ").append(totalVeterinarios()).append("\n");
        relatorio.append("Administradores: ").append(totalAdministradores()).append("\n");
        relatorio.append("Adotantes: ").append(totalAdotantes()).append("\n");
        relatorio.append("Horas registradas por voluntarios: ")
                .append(totalHorasVoluntarios()).append("\n");

        relatorio.append("\n--- ATIVIDADES ---\n");
        relatorio.append("Total de atividades: ").append(totalAtividades()).append("\n");
        relatorio.append("Agendadas: ")
                .append(contarAtividadesPorStatus(StatusAtividade.AGENDADA)).append("\n");
        relatorio.append("Em andamento: ")
                .append(contarAtividadesPorStatus(StatusAtividade.EM_ANDAMENTO)).append("\n");
        relatorio.append("Concluidas: ")
                .append(contarAtividadesPorStatus(StatusAtividade.CONCLUIDA)).append("\n");
        relatorio.append("Canceladas: ")
                .append(contarAtividadesPorStatus(StatusAtividade.CANCELADA)).append("\n");
        relatorio.append("Participacoes: ").append(totalParticipacoes()).append("\n");

        relatorio.append("\n--- NOTIFICACOES ---\n");
        relatorio.append("Total: ").append(totalNotificacoes()).append("\n");
        relatorio.append("Lidas: ").append(totalNotificacoesLidas()).append("\n");
        relatorio.append("Nao lidas: ").append(totalNotificacoesNaoLidas()).append("\n");

        relatorio.append("\n========================================\n");
        relatorio.append("          FIM DO RELATORIO\n");
        relatorio.append("========================================\n");

        return relatorio.toString();
    }

    public void imprimirRelatorioGeral() {
        System.out.println(gerarRelatorioGeral());
    }
}
