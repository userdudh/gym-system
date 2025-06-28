package br.upe.ui;

import br.upe.business.IExercicioService;
import br.upe.business.ExercicioService;
import br.upe.business.IPlanoTreinoService;
import br.upe.business.PlanoTreinoService;
import br.upe.data.beans.Exercicio;
import br.upe.data.beans.ItemPlanoTreino;
import br.upe.data.beans.PlanoTreino;
import br.upe.data.repository.IExercicioRepository;
import br.upe.data.repository.impl.ExercicioRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuPlanoTreino {

    private final IPlanoTreinoService planoTreinoService;
    private final IExercicioService exercicioService;
    private final IExercicioRepository exercicioRepository;
    private final Scanner sc;
    private final int idUsuarioLogado;

    public MenuPlanoTreino(int idUsuarioLogado) {
        this.planoTreinoService = new PlanoTreinoService();
        this.exercicioService = new ExercicioService();
        this.exercicioRepository = new ExercicioRepositoryImpl();
        this.sc = new Scanner(System.in);
        this.idUsuarioLogado = idUsuarioLogado;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n===== GERENCIAR PLANOS DE TREINO =====");
            System.out.println("1. Criar novo plano de treino");
            System.out.println("2. Listar meus planos");
            System.out.println("3. Editar plano");
            System.out.println("4. Deletar plano");
            System.out.println("5. Adicionar exercício");
            System.out.println("6. Remover exercício");
            System.out.println("7. Ver detalhes do plano");
            System.out.println("8. Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1: criarNovoPlano(); break;
                case 2: listarMeusPlanos(); break;
                case 3: editarPlano(); break;
                case 4: deletarPlano(); break;
                case 5: adicionarExercicioAoPlano(); break;
                case 6: removerExercicioDoPlano(); break;
                case 7: verDetalhesDoPlano(); break;
                case 8: System.out.println("Voltando ao menu principal..."); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 8);
    }

    private void criarNovoPlano() {
        System.out.print("Nome do plano: ");
        String nome = sc.nextLine();
        try {
            PlanoTreino plano = planoTreinoService.criarPlano(idUsuarioLogado, nome);
            System.out.println("Plano criado com ID: " + plano.getIdPlano());
        } catch (Exception e) {
            System.err.println("Erro ao criar plano: " + e.getMessage());
        }
    }

    private void listarMeusPlanos() {
        List<PlanoTreino> planos = planoTreinoService.listarMeusPlanos(idUsuarioLogado);
        if (planos.isEmpty()) {
            System.out.println("Você ainda não tem planos.");
        } else {
            planos.forEach(System.out::println);
        }
    }

    private void editarPlano() {
        System.out.print("Nome atual do plano: ");
        String nomeAtual = sc.nextLine();

        Optional<PlanoTreino> planoOpt = planoTreinoService.buscarPlanoPorNomeEUsuario(idUsuarioLogado, nomeAtual);
        if (!planoOpt.isPresent()) {
            System.out.println("Plano não encontrado.");
            return;
        }

        System.out.print("Novo nome (ou deixe em branco): ");
        String novoNome = sc.nextLine();
        try {
            planoTreinoService.editarPlano(idUsuarioLogado, nomeAtual, novoNome.trim().isEmpty() ? null : novoNome);
            System.out.println("Plano atualizado.");
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private void deletarPlano() {
        System.out.print("Nome do plano: ");
        String nome = sc.nextLine();
        try {
            boolean ok = planoTreinoService.deletarPlano(idUsuarioLogado, nome);
            if (ok) System.out.println("Plano deletado.");
            else System.out.println("Plano não encontrado.");
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private void adicionarExercicioAoPlano() {
        System.out.print("Nome do plano: ");
        String nomePlano = sc.nextLine();

        List<Exercicio> lista = exercicioService.listarExerciciosDoUsuario(idUsuarioLogado);
        if (lista.isEmpty()) {
            System.out.println("Cadastre exercícios primeiro.");
            return;
        }

        lista.forEach(e -> System.out.println("ID: " + e.getIdExercicio() + " - " + e.getNome()));

        try {
            System.out.print("ID do exercício: ");
            int idEx = Integer.parseInt(sc.nextLine());

            System.out.print("Carga (kg): ");
            int carga = Integer.parseInt(sc.nextLine());

            System.out.print("Repetições: ");
            int reps = Integer.parseInt(sc.nextLine());

            planoTreinoService.adicionarExercicioAoPlano(idUsuarioLogado, nomePlano, idEx, carga, reps);
            System.out.println("Exercício adicionado!");

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private void removerExercicioDoPlano() {
        System.out.print("Nome do plano: ");
        String nomePlano = sc.nextLine();

        Optional<PlanoTreino> planoOpt = planoTreinoService.buscarPlanoPorNomeEUsuario(idUsuarioLogado, nomePlano);
        if (!planoOpt.isPresent()) {
            System.out.println("Plano não encontrado.");
            return;
        }

        PlanoTreino plano = planoOpt.get();
        if (plano.getItensTreino().isEmpty()) {
            System.out.println("Plano vazio.");
            return;
        }

        for (ItemPlanoTreino item : plano.getItensTreino()) {
            Optional<Exercicio> ex = exercicioRepository.buscarPorId(item.getIdExercicio());
            String nome = ex.isPresent() ? ex.get().getNome() : "Desconhecido";
            System.out.println("ID: " + item.getIdExercicio() + " - " + nome + ", " + item.getCargaKg() + "kg, " + item.getRepeticoes() + " rep");
        }

        try {
            System.out.print("ID do exercício a remover: ");
            int idEx = Integer.parseInt(sc.nextLine());
            planoTreinoService.removerExercicioDoPlano(idUsuarioLogado, nomePlano, idEx);
            System.out.println("Exercício removido.");
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private void verDetalhesDoPlano() {
        System.out.print("Nome do plano: ");
        String nome = sc.nextLine();

        Optional<PlanoTreino> planoOpt = planoTreinoService.buscarPlanoPorNomeEUsuario(idUsuarioLogado, nome);
        if (!planoOpt.isPresent()) {
            System.out.println("Plano não encontrado.");
            return;
        }

        PlanoTreino plano = planoOpt.get();
        System.out.println(plano.getNome() + " - ID: " + plano.getIdPlano());

        for (ItemPlanoTreino item : plano.getItensTreino()) {
            Optional<Exercicio> ex = exercicioRepository.buscarPorId(item.getIdExercicio());
            String nomeEx = ex.isPresent() ? ex.get().getNome() : "Desconhecido";
            System.out.println(" - " + nomeEx + " (ID " + item.getIdExercicio() + "): " +
                    item.getCargaKg() + "kg, " + item.getRepeticoes() + " repetições");
        }
    }
}
