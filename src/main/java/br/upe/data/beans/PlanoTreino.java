package br.upe.data.beans;

import br.upe.data.beans.Exercicio;
import java.util.ArrayList;
import java.util.List;

public class PlanoTreino {
    private int id;
    private int usuarioId;
    private List<ItemPlanoTreino> itens;

    public PlanoTreino(int id, int usuarioId) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Exercicio exercicio, int repeticoes, double carga) {
        itens.add(new ItemPlanoTreino(exercicio, repeticoes, carga));
    }

    public List<ItemPlanoTreino> getItens() {
        return itens;
    }
}
