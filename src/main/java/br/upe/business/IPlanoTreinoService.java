package br.upe.business;

import br.upe.data.beans.PlanoTreino;

import java.util.List;
import java.util.Optional;

public interface IPlanoTreinoService {

    PlanoTreino criarPlano(int idUsuario, String nome);
    List<PlanoTreino> listarMeusPlanos(int idUsuario);
    Optional<PlanoTreino> buscarPlanoPorNomeEUsuario(int idUsuario, String nome);
    void editarPlano(int idUsuario, String nomeAtual, String novoNome);
    boolean deletarPlano(int idUsuario, String nome);
    void adicionarExercicioAoPlano(int idUsuario, String nomePlano, int idExercicio, int carga, int repeticoes);
    void removerExercicioDoPlano(int idUsuario, String nomePlano, int idExercicio);
}
