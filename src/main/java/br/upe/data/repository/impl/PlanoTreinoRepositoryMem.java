package br.upe.data.repository.impl;

import br.upe.data.beans.PlanoTreino;
import br.upe.data.repository.PlanoTreinoRepository;
import java.util.ArrayList;
import java.util.List;

public class PlanoTreinoRepositoryMem implements PlanoTreinoRepository {
    private final List<PlanoTreino> planos = new ArrayList<>();
    private int proximoId = 1;

    @Override
    public void salvar(PlanoTreino plano) {
        plano.setId(proximoId++);
        planos.add(plano);
    }

    @Override
    public PlanoTreino buscarPorId(int id) {
        for (PlanoTreino plano : planos) {
            if (plano.getId() == id) {
                return plano;
            }
        }
        return null;
    }

    @Override
    public List<PlanoTreino> listarPorUsuario(int usuarioId) {
        List<PlanoTreino> resultado = new ArrayList<>();
        for (PlanoTreino plano : planos) {
            if (plano.getUsuarioId() == usuarioId) {
                resultado.add(plano);
            }
        }
        return resultado;
    }

    @Override
    public void atualizar(PlanoTreino planoAtualizado) {
        for (int i = 0; i < planos.size(); i++) {
            if (planos.get(i).getId() == planoAtualizado.getId()) {
                planos.set(i, planoAtualizado);
                return;
            }
        }
    }
}
