package br.upe.data.repository;

import br.upe.data.beans.SessaoTreino;

import java.util.List;

public interface SessaoTreinoRepository {
    void salvar(SessaoTreino sessao);
    List<SessaoTreino> listarPorPlano(int planoId);
    List<SessaoTreino> listarTodas();
}
