package br.upe;

import ui.MenuExercicios;

public class Main {
    public static void main(String[] args) {
        int idUsuarioLogado = 1;
        System.out.println("--- DEBUG: Usuário ID " + idUsuarioLogado + " LOGADO para testes. ---");

        MenuExercicios menuExercicios = new MenuExercicios(idUsuarioLogado);
        menuExercicios.exibirMenu();

        System.out.println("SysFit encerrado. Até mais!");
    }
}
