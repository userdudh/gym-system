package br.upe.business;

import br.upe.data.*;

public class UsuarioService {
    private final UsuarioRepositoryCSV repositorio = new UsuarioRepositoryCSV();

    public void cadastrarUsuario(String nome, String email, TipoUsuario tipo) {
        int id = repositorio.gerarId();
        Usuario usuario = new Usuario(id, nome, email, tipo);
        repositorio.salvar(usuario);
    }
}
