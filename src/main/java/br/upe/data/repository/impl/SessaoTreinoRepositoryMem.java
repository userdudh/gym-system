package br.upe.data.repository.impl;

import br.upe.data.beans.SessaoTreino;
import br.upe.data.repository.SessaoTreinoRepository;
import java.util.ArrayList;
import java.util.List;

public class SessaoTreinoRepositoryMem implements SessaoTreinoRepository {
    private final List<SessaoTreino> sessoes = new ArrayList<>();

    @Override
    public void salvar(SessaoTreino sessao) {
        sessoes.add(sessao);
    }

    @Override
    public List<SessaoTreino> listarPorPlano(int planoId) {
        List<SessaoTreino> resultado = new ArrayList<>();
        for (SessaoTreino s : sessoes) {
            if (s.getPlanoId() == planoId) {
                resultado.add(s);
            }
        }
        return resultado;
    }

    @Override
    public List<SessaoTreino> listarTodas() {
        return new ArrayList<>(sessoes);
    }
}
