package br.upe.data.repository.impl;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class UsuarioRepositoryCSV implements UsuarioRepository {

    private static final String CAMINHO_ARQUIVO = "resources/data/usuarios.csv";
    private int proximoId = 1;

    public UsuarioRepositoryCSV() {
        inicializarId();
    }

    private void inicializarId() {
        try {
            List<Usuario> usuarios = listarTodos();
            if (!usuarios.isEmpty()) {
                int ultimoId = usuarios.get(usuarios.size() - 1).getId();
                proximoId = ultimoId + 1;
            }
        } catch (Exception e) {
            proximoId = 1;
        }
    }

    @Override
    public void salvar(Usuario usuario) {
        try {
            Path caminho = Paths.get(CAMINHO_ARQUIVO);
            Files.createDirectories(caminho.getParent());

            boolean arquivoExiste = Files.exists(caminho);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO, true))) {
                if (!arquivoExiste) {
                    writer.write("id,nome,email,tipo\n");
                }

                writer.write(String.format("%d,%s,%s,%s\n",
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getTipo().name()));
            }

        } catch (IOException e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();

        Path caminho = Paths.get(CAMINHO_ARQUIVO);
        if (!Files.exists(caminho)) return usuarios;

        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            reader.readLine(); // pula cabeçalho

            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                String email = partes[2];
                TipoUsuario tipo = TipoUsuario.valueOf(partes[3]);
                usuarios.add(new Usuario(id, nome, email, tipo));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler usuários: " + e.getMessage());
        }

        return usuarios;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return listarTodos().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public int gerarId() {
        return proximoId++;
    }
}
