package br.upe;

import br.upe.ui.MenuPrincipal; // Importa a nova classe MenuPrincipal

public class Main {
    public static void main(String[] args) {
        // Por enquanto, a gente simula que o usuário de ID 1 está logado.
        // Mais pra frente, isso virá de uma tela de login real.
        int idUsuarioLogado = 1;
        System.out.println("--- DEBUG: Usuário ID " + idUsuarioLogado + " LOGADO para testes. ---");

        // Cria o menu principal e o exibe, começando a interação com o usuário.
        MenuPrincipal menuPrincipal = new MenuPrincipal(idUsuarioLogado);
        menuPrincipal.exibirMenu();
    }
}