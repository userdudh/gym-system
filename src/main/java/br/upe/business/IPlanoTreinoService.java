package br.upe.business;

import br.upe.data.beans.Exercicio;
import br.upe.data.beans.ItemPlanoTreino;
import br.upe.data.beans.PlanoTreino;

import java.util.List;
import java.util.Optional;

public interface IPlanoTreinoService {
    PlanoTreino criarPlano(int idUsuario, String nome);
    void adicionarExercicioAoPlano(int idUsuario, String nomePlano, int idExercicio, int cargaKg, int repeticoes);
    void removerExercicioDoPlano(int idUsuario, String nomePlano, int idExercicio);
    List<PlanoTreino> listarMeusPlanos(int idUsuario);
    Optional<PlanoTreino> buscarPlanoPorNomeEUsuario(int idUsuario, String nomePlano);
    void editarPlano(int idUsuario, String nomeAtualPlano, String novoNome);
    boolean deletarPlano(int idUsuario, String nomePlano);
    Optional<PlanoTreino> buscarPlanoPorId(int idPlanoEscolhido);
}