package br.upe.ui;

import br.upe.business.UsuarioService;
import br.upe.data.TipoUsuario;

import java.util.Scanner;

public class MenuUsuario {
    private final UsuarioService usuarioService;
    private final Scanner scanner;

    public MenuUsuario(Scanner scanner) {
        this.usuarioService = new UsuarioService();
        this.scanner = scanner;
    }

    public void exibirMenuCadastro() {
        System.out.println("===== Cadastro de Usuário =====");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        TipoUsuario tipo = escolherTipo();

        usuarioService.cadastrarUsuario(nome, email, tipo);

        System.out.println("Usuário cadastrado com sucesso!");
    }

    private TipoUsuario escolherTipo() {
        while (true) {
            System.out.print("Tipo de usuário [1] ADMIN, [2] COMUM: ");
            String opcao = scanner.nextLine();

            if (opcao.equals("1")) return TipoUsuario.ADMIN;
            if (opcao.equals("2")) return TipoUsuario.COMUM;

            System.out.println("Opção inválida. Tente novamente.");
        }
    }
}
