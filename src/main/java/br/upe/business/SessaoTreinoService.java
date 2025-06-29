package br.upe.business;

import br.upe.data.beans.*;
import br.upe.data.repository.*;

import java.util.List;

public class SessaoTreinoService {
    private final SessaoTreinoRepository sessaoRepo;
    private final PlanoTreinoRepository planoRepo;

    public SessaoTreinoService(SessaoTreinoRepository sessaoRepo, PlanoTreinoRepository planoRepo) {
        this.sessaoRepo = sessaoRepo;
        this.planoRepo = planoRepo;
    }

    public SessaoTreino iniciarSessao(int planoId) {
        SessaoTreino sessao = new SessaoTreino(0, planoId); // ID será gerado depois, se quiser
        return sessao;
    }

    public void registrarExecucao(SessaoTreino sessao, Exercicio exercicio, int repeticoes, double carga) {
        sessao.registrarItem(exercicio, repeticoes, carga);
    }

    public void salvarSessao(SessaoTreino sessao) {
        sessaoRepo.salvar(sessao);
    }

    public void verificarAlteracoesEAtualizarPlano(SessaoTreino sessao) {
        PlanoTreino plano = planoRepo.buscarPorId(sessao.getPlanoId());

        for (ItemPlanoTreino executado : sessao.getItensExecutados()) {
            for (ItemPlanoTreino planejado : plano.getItens()) {
                if (executado.getExercicio().getNome().equalsIgnoreCase(planejado.getExercicio().getNome())) {
                    boolean mudou = executado.getRepeticoes() != planejado.getRepeticoes()
                            || executado.getCarga() != planejado.getCarga();

                    if (mudou) {
                        System.out.printf("Exercício %s teve alterações. Atualizar plano? (s/n): ",
                                executado.getExercicio().getNome());

                        Scanner sc = new Scanner(System.in);
                        String resposta = sc.nextLine();
                        if (resposta.equalsIgnoreCase("s")) {
                            planejado.setRepeticoes(executado.getRepeticoes());
                            planejado.setCarga(executado.getCarga());
                        }
                    }
                }
            }
        }

        planoRepo.atualizar(plano);
    }
}
