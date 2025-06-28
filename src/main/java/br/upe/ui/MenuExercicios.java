package br.upe.ui;

import br.upe.business.ExercicioService;
import br.upe.data.beans.Exercicio;
import br.upe.business.IExercicioService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuExercicios {

    private IExercicioService exercicioService;
    private Scanner sc;
    private int idUsuarioLogado;

    public MenuExercicios(int idUsuarioLogado) {
        this.exercicioService = new ExercicioService();
        this.sc = new Scanner(System.in);
        this.idUsuarioLogado = idUsuarioLogado;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n===== GERENCIAR EXERCÍCIOS =====");
            System.out.println("1. Cadastrar Novo Exercício");
            System.out.println("2. Listar Meus Exercícios");
            System.out.println("3. Editar Exercício");
            System.out.println("4. Excluir Exercício");
            System.out.println("5. Ver Detalhes de um Exercício");
            System.out.println("6. Voltar");
            System.out.print("\nEscolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    cadastrarNovoExercicio();
                    break;
                case 2:
                    listarMeusExercicios();
                    break;
                case 3:
                    editarExercicio();
                    break;
                case 4:
                    excluirExercicio();
                    break;
                case 5:
                    verDetalhesExercicio();
                    break;
                case 6:
                    System.out.println("Direcionando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma das opções válidas.");
            }
        } while (opcao != 6);
    }

    private void cadastrarNovoExercicio() {
        System.out.println("\n====== CADASTRAR NOVO EXERCÍCIO =====");
        System.out.print("Nome do Exercício: ");
        String nome = sc.nextLine();
        System.out.print("Descrição: ");
        String descricao = sc.nextLine();
        System.out.print("Caminho do GIF (ex: gif/agachamento.gif): ");
        String caminhoGif = sc.nextLine();

        try {
            Exercicio novo = exercicioService.cadastrarExercicio(idUsuarioLogado, nome, descricao, caminhoGif);
            System.out.println("Exercício '" + novo.getNome() + "' cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void listarMeusExercicios() {
        System.out.println("\n===== LISTA DOS MEUS EXERCÍCIOS ======");
        List<Exercicio> exercicios = exercicioService.listarExerciciosDoUsuario(idUsuarioLogado);
        if (exercicios.isEmpty()) {
            System.out.println("Nenhum exercício cadastrado por você ainda.");
        } else {
            exercicios.forEach(System.out::println);
        }
    }

    private void editarExercicio() {
        System.out.println("\n===== EDITAR EXERCÍCIO =====");
        System.out.print("Digite o NOME do exercício que você deseja editar: ");
        String nomeAtualExercicio = sc.nextLine(); // Corrigido: ler como String

        Optional<Exercicio> exercicioOpt = exercicioService.buscarExercicioDoUsuarioPorNome(idUsuarioLogado, nomeAtualExercicio);

        if (exercicioOpt.isPresent()) {
            Exercicio exercicio = exercicioOpt.get();

            System.out.println("Exercício atual: " + exercicio);
            System.out.println("Deixe o campo em branco se não quiser alterar o valor.");

            System.out.print("Novo Nome (" + exercicio.getNome() + "): ");
            String novoNome = sc.nextLine();
            System.out.print("Nova Descrição (" + exercicio.getDescricao() + "): ");
            String novaDescricao = sc.nextLine();
            System.out.print("Novo Caminho do GIF (" + exercicio.getCaminhoGif() + "): ");
            String novoCaminhoGif = sc.nextLine();

            try {
                exercicioService.atualizarExercicio(idUsuarioLogado, nomeAtualExercicio, // Passa idUsuarioLogado e nomeAtual
                        novoNome.isEmpty() ? null : novoNome,
                        novaDescricao.isEmpty() ? null : novaDescricao,
                        novoCaminhoGif.isEmpty() ? null : novoCaminhoGif);
                System.out.println("Exercício '" + nomeAtualExercicio + "' atualizado com sucesso!");
            } catch (IllegalArgumentException e) {
                System.err.println("Erro ao atualizar: " + e.getMessage());
            }
        } else {
            System.out.println("Exercício com nome '" + nomeAtualExercicio + "' não encontrado entre os seus exercícios.");
        }
    }

    private void excluirExercicio() {
        System.out.println("\n===== EXCLUIR EXERCÍCIO =====");
        System.out.print("Digite o NOME do exercício que você deseja excluir: ");
        String nomeExercicio = sc.nextLine();

        try {
            boolean deletado = exercicioService.deletarExercicioPorNome(idUsuarioLogado, nomeExercicio);
            if (deletado) {
                System.out.println("Exercício '" + nomeExercicio + "' excluído com sucesso!");
            } else {
                System.out.println("Exercício '" + nomeExercicio + "' não encontrado entre os seus exercícios.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao excluir: " + e.getMessage());
        }
    }

    private void verDetalhesExercicio() {
        System.out.println("\n===== VER DETALHES DO EXERCÍCIO =====");
        System.out.print("Digite o NOME do exercício para ver os detalhes: ");
        String nomeExercicio = sc.nextLine(); // Corrigido: ler como String

        Optional<Exercicio> exercicioOpt = exercicioService.buscarExercicioDoUsuarioPorNome(idUsuarioLogado, nomeExercicio);

        if (exercicioOpt.isPresent()) {
            Exercicio exercicio = exercicioOpt.get();
            VisualizadorExercicio visualizador = new VisualizadorExercicio();
            visualizador.exibirDetalhes(exercicio);

        } else {
            System.out.println("Exercício com nome '" + nomeExercicio + "' não encontrado entre os seus exercícios.");
        }
    }
}