package br.upe.business;

import br.upe.data.beans.*;
import br.upe.data.repository.*;

import java.util.List;

public class PlanoTreinoService {
    private final PlanoTreinoRepository planoRepo;

    public PlanoTreinoService(PlanoTreinoRepository planoRepo) {
        this.planoRepo = planoRepo;
    }

    public PlanoTreino criarPlano(int usuarioId) {
        PlanoTreino plano = new PlanoTreino(0, usuarioId); // ID será atribuído no repo
        planoRepo.salvar(plano);
        return plano;
    }

    public void adicionarExercicioAoPlano(PlanoTreino plano, Exercicio exercicio, int repeticoes, double carga) {
        plano.adicionarItem(exercicio, repeticoes, carga);
        planoRepo.atualizar(plano);
    }

    public List<PlanoTreino> listarPorUsuario(int usuarioId) {
        return planoRepo.listarPorUsuario(usuarioId);
    }

    public PlanoTreino buscarPorId(int id) {
        return planoRepo.buscarPorId(id);
    }
}
