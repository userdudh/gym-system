package br.upe.data.repository;

import java.util.List;

public interface UsuarioRepository {
    void salvar(Usuario usuario);
    List<Usuario> listarTodos();
    Usuario buscarPorEmail(String email);
}
