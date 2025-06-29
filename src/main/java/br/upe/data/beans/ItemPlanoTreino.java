package br.upe.data.beans;

import br.upe.data.beans.Exercicio;

public class ItemPlanoTreino {
    private Exercicio exercicio;
    private int repeticoes;
    private double carga;

    public ItemPlanoTreino(Exercicio exercicio, int repeticoes, double carga) {
        this.exercicio = exercicio;
        this.repeticoes = repeticoes;
        this.carga = carga;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public double getCarga() {
        return carga;
    }
}
