package br.upe.data.repository;
import br.upe.data.beans.PlanoTreino;

import java.util.List;

public interface PlanoTreinoRepository {
    void salvar(PlanoTreino plano);
    PlanoTreino buscarPorId(int id);
    List<PlanoTreino> listarPorUsuario(int usuarioId);
    void atualizar(PlanoTreino plano);
}
