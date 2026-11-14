package app;

import model.*;
import model.enums.*;
import repository.BancoMemoria;
import service.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final AnimalService animalService = new AnimalService();
    private static final AdocaoService adocaoService = new AdocaoService();
    private static final SaudeService saudeService = new SaudeService();
    private static final AtividadeService atividadeService = new AtividadeService();
    private static final NotificacaoService notificacaoService = new NotificacaoService();
    private static final RelatorioService relatorioService = new RelatorioService();
    private static final CompatibilidadeService compatibilidadeService =
            new CompatibilidadeService();

    public static void main(String[] args) {

        int opcao;

        do {
            System.out.println("\n======================================");
            System.out.println("             AMPARAPET");
            System.out.println(" Sistema de Gerenciamento de Abrigos");
            System.out.println("======================================");
            System.out.println("1 - Usuários");
            System.out.println("2 - Animais");
            System.out.println("3 - Adoções");
            System.out.println("4 - Saúde dos animais");
            System.out.println("5 - Atividades e notificações");
            System.out.println("6 - Relatórios e estatísticas");
            System.out.println("7 - Compatibilidade inteligente");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            try {
                switch (opcao) {
                    case 1 -> menuUsuarios();
                    case 2 -> menuAnimais();
                    case 3 -> menuAdocoes();
                    case 4 -> menuSaude();
                    case 5 -> menuAtividades();
                    case 6 -> relatorioService.imprimirRelatorioGeral();
                    case 7 -> menuCompatibilidade();
                    case 0 -> System.out.println("Sistema encerrado.");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (RuntimeException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);

        scanner.close();
    }

    // =====================================
    // USUARIOS
    // =====================================

    private static void menuUsuarios() {

        int opcao;

        do {
            System.out.println("\n--- USUARIOS ---");
            System.out.println("1 - Cadastrar administrador");
            System.out.println("2 - Cadastrar veterinário");
            System.out.println("3 - Cadastrar voluntário");
            System.out.println("4 - Cadastrar adotante");
            System.out.println("5 - Listar usuários");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {

                case 1 -> {
                    Administrador admin = new Administrador(
                            lerTexto("Nome: "),
                            lerTexto("Email: ")
                    );

                    BancoMemoria.getUsuarios().add(admin);
                    System.out.println("Administrador cadastrado!");
                }

                case 2 -> {
                    Veterinario veterinario = new Veterinario(
                            lerTexto("Nome: "),
                            lerTexto("Email: "),
                            lerTexto("CRVM: ")
                    );

                    BancoMemoria.getUsuarios().add(veterinario);
                    System.out.println("Veterinário cadastrado!");
                }

                case 3 -> {
                    Voluntario voluntario = new Voluntario(
                            lerTexto("Nome: "),
                            lerTexto("Email: "),
                            0
                    );

                    BancoMemoria.getUsuarios().add(voluntario);
                    System.out.println("Voluntário cadastrado!");
                }

                case 4 -> {
                    Adotante adotante = new Adotante(
                            lerTexto("Nome: "),
                            lerTexto("Email: "),
                            lerTexto("Tipo de moradia: "),
                            lerTexto("Preferência de espécie: ")
                    );

                    BancoMemoria.getUsuarios().add(adotante);
                    System.out.println("Adotante cadastrado!");
                }

                case 5 -> {
                    for (Usuario usuario : BancoMemoria.getUsuarios()) {
                        System.out.println(usuario);
                    }
                }

                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    // =====================================
    // ANIMAIS
    // =====================================

    private static void menuAnimais() {

        int opcao;

        do {
            System.out.println("\n--- ANIMAIS ---");
            System.out.println("1 - Cadastrar animal");
            System.out.println("2 - Listar animais");
            System.out.println("3 - Buscar animal por ID");
            System.out.println("4 - Remover animal");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {

                case 1 -> {

                    String nome = lerTexto("Nome: ");
                    Especie especie = lerEnum(Especie.class);
                    Porte porte = lerEnum(Porte.class);
                    EstadoSaude saude = lerEnum(EstadoSaude.class);

                    Animal animal = new Animal(
                            nome,
                            especie,
                            lerTexto("Raça: "),
                            lerInteiro("Idade: "),
                            porte,
                            saude,
                            lerData("Data do resgate (AAAA-MM-DD): ")
                    );

                    animalService.cadastrarAnimal(animal);

                    System.out.println("Animal cadastrado!");
                }

                case 2 -> {
                    animalService.listarTodos().forEach(System.out::println);
                }

                case 3 -> {
                    int id = lerInteiro("ID do animal: ");
                    System.out.println(animalService.buscarPorId(id));
                }

                case 4 -> {
                    int id = lerInteiro("ID do animal: ");
                    animalService.removerAnimal(id);
                    System.out.println("Animal removido!");
                }

                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    // =====================================
    // ADOCOES
    // =====================================

    private static void menuAdocoes() {

        int opcao;

        do {
            System.out.println("\n--- ADOCOES ---");
            System.out.println("1 - Solicitar adoção");
            System.out.println("2 - Aprovar solicitação");
            System.out.println("3 - Rejeitar solicitação");
            System.out.println("4 - Registrar devolução");
            System.out.println("5 - Listar solicitações");
            System.out.println("6 - Consultar histórico");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {

                case 1 -> {
                    int idAnimal = lerInteiro("ID do animal: ");
                    int idAdotante = lerInteiro("ID do adotante: ");

                    Animal animal = animalService.buscarPorId(idAnimal);

                    Adotante adotante = buscarUsuario(
                            idAdotante, Adotante.class
                    );

                    adocaoService.solicitarAdocao(animal, adotante);

                    System.out.println("Solicitação registrada!");
                }

                case 2 -> {
                    int idSolicitacao = lerInteiro("ID da solicitação: ");
                    int idAdmin = lerInteiro("ID do administrador: ");

                    Administrador admin = buscarUsuario(
                            idAdmin, Administrador.class
                    );

                    adocaoService.aprovarSolicitacao(idSolicitacao, admin);

                    System.out.println("Adoção aprovada!");
                }

                case 3 -> {
                    int idSolicitacao = lerInteiro("ID da solicitação: ");
                    int idAdmin = lerInteiro("ID do administrador: ");

                    Administrador admin = buscarUsuario(
                            idAdmin, Administrador.class
                    );

                    adocaoService.rejeitarSolicitacao(idSolicitacao, admin);

                    System.out.println("Solicitação rejeitada!");
                }

                case 4 -> {
                    int idAdocao = lerInteiro("ID da adoção: ");

                    Administrador administrador = buscarUsuario(
                            lerInteiro("ID do administrador: "),
                            Administrador.class
                    );

                    String motivo = lerTexto("Motivo da devolução: ");

                    adocaoService.registrarDevolucao(
                            idAdocao,
                            administrador,
                            motivo
                    );

                    System.out.println("Devolução registrada!");
                }

                case 5 -> {
                    BancoMemoria.getSolicitacoes().forEach(System.out::println);
                }

                case 6 -> {
                    BancoMemoria.getAdocoes().forEach(System.out::println);
                }

                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    // =====================================
    // SAUDE
    // =====================================

    private static void menuSaude() {

        int opcao;

        do {
            System.out.println("\n--- SAUDE DOS ANIMAIS ---");
            System.out.println("1 - Registrar consulta");
            System.out.println("2 - Registrar vacina");
            System.out.println("3 - Registrar medicamento");
            System.out.println("4 - Iniciar tratamento");
            System.out.println("5 - Listar consultas");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {

                case 1 -> {
                    System.out.println(
                            "Para registrar uma consulta, informe os dados:"
                    );

                    Animal animal = animalService.buscarPorId(
                            lerInteiro("ID do animal: ")
                    );

                    Veterinario veterinario = buscarUsuario(
                            lerInteiro("ID do veterinário: "),
                            Veterinario.class
                    );

                    saudeService.registrarConsulta(
                            animal,
                            veterinario,
                            lerEnum(TipoConsulta.class),
                            lerTexto("Diagnóstico: "),
                            lerTexto("Observações: ")
                    );

                    System.out.println("Consulta registrada!");
                }

                case 2 -> {
                    System.out.println(
                            "Registro de vacina: confira os dados solicitados."
                    );

                    Animal animal = animalService.buscarPorId(
                            lerInteiro("ID do animal: ")
                    );

                    Veterinario veterinario = buscarUsuario(
                            lerInteiro("ID do veterinário: "),
                            Veterinario.class
                    );

                    saudeService.registrarVacina(
                            animal,
                            veterinario,
                            lerTexto("Nome da vacina: "),
                            lerData("Data de aplicação (AAAA-MM-DD): "),
                            lerData("Data de validade (AAAA-MM-DD): ")
                    );

                    System.out.println("Vacina registrada!");
                }

                case 3 -> {
                    Animal animal = animalService.buscarPorId(
                            lerInteiro("ID do animal: ")
                    );

                    Veterinario veterinario = buscarUsuario(
                            lerInteiro("ID do veterinário: "),
                            Veterinario.class
                    );

                    saudeService.registrarMedicamento(
                            animal,
                            veterinario,
                            lerTexto("Nome do medicamento: "),
                            lerEnum(TipoMedicamento.class),
                            lerTexto("Dosagem: "),
                            lerInteiro("Duração em dias: ")
                    );

                    System.out.println("Medicamento registrado!");
                }

                case 4 -> {
                    Animal animal = animalService.buscarPorId(
                            lerInteiro("ID do animal: ")
                    );

                    Veterinario veterinario = buscarUsuario(
                            lerInteiro("ID do veterinário: "),
                            Veterinario.class
                    );

                    saudeService.iniciarTratamento(
                            animal,
                            veterinario,
                            lerTexto("Descrição do tratamento: ")
                    );

                    System.out.println("Tratamento iniciado!");
                }

                case 5 -> {
                    BancoMemoria.getConsultas().forEach(System.out::println);
                }

                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    // =====================================
    // ATIVIDADES E NOTIFICACOES
    // =====================================

    private static void menuAtividades() {

        int opcao;

        do {
            System.out.println("\n--- ATIVIDADES ---");
            System.out.println("1 - Cadastrar atividade");
            System.out.println("2 - Inscrever voluntário");
            System.out.println("3 - Iniciar atividade");
            System.out.println("4 - Concluir atividade");
            System.out.println("5 - Listar atividades");
            System.out.println("6 - Listar notificações");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {

                case 1 -> {
                    Administrador admin = buscarUsuario(
                            lerInteiro("ID do administrador: "),
                            Administrador.class
                    );

                    atividadeService.cadastrarAtividade(
                            lerTexto("Nome: "),
                            lerTexto("Descrição: "),
                            lerData("Data (AAAA-MM-DD): "),
                            lerTexto("Local: "),
                            lerInteiro("Limite de participantes: "),
                            admin
                    );

                    System.out.println("Atividade cadastrada!");
                }

                case 2 -> {
                    int idAtividade = lerInteiro("ID da atividade: ");

                    Voluntario voluntario = buscarUsuario(
                            lerInteiro("ID do voluntário: "),
                            Voluntario.class
                    );

                    atividadeService.inscreverVoluntario(
                            idAtividade, voluntario
                    );

                    System.out.println("Inscrição realizada!");
                }

                case 3 -> {
                    Administrador admin = buscarUsuario(
                            lerInteiro("ID do administrador: "),
                            Administrador.class
                    );

                    atividadeService.iniciarAtividade(
                            lerInteiro("ID da atividade: "), admin
                    );

                    System.out.println("Atividade iniciada!");
                }

                case 4 -> {
                    Administrador admin = buscarUsuario(
                            lerInteiro("ID do administrador: "),
                            Administrador.class
                    );

                    atividadeService.concluirAtividade(
                            lerInteiro("ID da atividade: "), admin
                    );

                    System.out.println("Atividade concluída!");
                }

                case 5 -> {
                    atividadeService.listarAtividades()
                            .forEach(System.out::println);
                }

                case 6 -> {
                    int idUsuario = lerInteiro("ID do usuário: ");

                    notificacaoService.listarNotificacoesPorUsuario(idUsuario)
                            .forEach(System.out::println);
                }

                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    // =====================================
    // COMPATIBILIDADE
    // =====================================

    private static void menuCompatibilidade() {

        int id = lerInteiro("ID do adotante: ");

        Adotante adotante = buscarUsuario(id, Adotante.class);

        compatibilidadeService.imprimirRecomendacoes(adotante);
    }

    // =====================================
    // METODOS AUXILIARES
    // =====================================

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Digite um número válido: ");
            }
        }
    }

    private static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        return lerInteiro();
    }

    private static LocalDate lerData(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Data inválida. Use AAAA-MM-DD.");
            }
        }
    }

    private static <E extends Enum<E>> E lerEnum(Class<E> tipo) {

        E[] valores = tipo.getEnumConstants();

        System.out.println("\nSelecione:");

        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + " - " + valores[i]);
        }

        while (true) {
            int opcao = lerInteiro("Opção: ");

            if (opcao >= 1 && opcao <= valores.length) {
                return valores[opcao - 1];
            }

            System.out.println("Opção inválida.");
        }
    }

    private static <T extends Usuario> T buscarUsuario(
            int id, Class<T> tipo
    ) {

        return BancoMemoria.getUsuarios().stream()
                .filter(u -> u.getId() == id)
                .filter(tipo::isInstance)
                .map(tipo::cast)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuário não encontrado ou tipo incorreto."
                        )
                );
    }
}
