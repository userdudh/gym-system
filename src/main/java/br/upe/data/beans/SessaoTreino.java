package br.upe.data.beans;

import br.upe.data.beans.Exercicio;
import java.time.LocalDate;
import java.util.List;

public class SessaoTreino {
    private int id;
    private int planoId;
    private LocalDate data;
    private List<ItemPlanoTreino> itensExecutados;

    public SessaoTreino(int id, int planoId) {
        this.id = id;
        this.planoId = planoId;
        this.data = LocalDate.now();
        this.itensExecutados = new List<ItemPlanoTreino>();
    }

    public void registrarItem(Exercicio exercicio, int repeticoes, double carga) {
        itensExecutados.add(new ItemPlanoTreino(exercicio, repeticoes, carga));
    }

    public int getId() {
        return id;
    }

    public int getPlanoId() {
        return planoId;
    }

    public LocalDate getData() {
        return data;
    }

    public List<ItemPlanoTreino> getItensExecutados() {
        return itensExecutados;
    }
}
