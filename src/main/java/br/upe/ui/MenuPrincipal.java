package br.upe.ui;


import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner sc; // Usamos 'final' porque o Scanner não vai mudar depois de inicializado
    private final int idUsuarioLogado; // O ID do usuário logado é fixo enquanto o programa roda

    public MenuPrincipal(int idUsuarioLogado) {
        this.sc = new Scanner(System.in);
        this.idUsuarioLogado = idUsuarioLogado;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n===== MENU PRINCIPAL SYSFIT =====");
            System.out.println("1. Gerenciar Usuários (Em breve!)");
            System.out.println("2. Gerenciar Medidas (Em breve!)");
            System.out.println("3. Gerenciar Planos de Treino");
            System.out.println("4. Gerenciar Exercícios");
            System.out.println("0. Sair");
            System.out.print("\nEscolha uma opção: ");

            try {
                // Tenta ler a opção do usuário como um número inteiro
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                // Se o que o usuário digitou não for um número, a opção é inválida
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    System.out.println("Funcionalidade de gerenciar usuários ainda está sendo desenvolvida.");
                    break;
                case 2:
                    System.out.println("Funcionalidade de gerenciar medidas ainda está sendo desenvolvida.");
                    break;
                case 3:
                    // Cria e mostra o menu de Planos de Treino, passando o ID do usuário
                    MenuPlanoTreino menuPlanoTreino = new MenuPlanoTreino(idUsuarioLogado);
                    menuPlanoTreino.exibirMenu();
                    break;
                case 4:
                    // Cria e mostra o menu de Exercícios, passando o ID do usuário
                    MenuExercicios menuExercicios = new MenuExercicios(idUsuarioLogado);
                    menuExercicios.exibirMenu();
                    break;
                case 0:
                    System.out.println("Saindo do SysFit. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha um número válido.");
            }
        } while (opcao != 0); // Continua exibindo o menu até o usuário digitar '0'
        sc.close(); // Fecha o Scanner para liberar recursos
    }
}
