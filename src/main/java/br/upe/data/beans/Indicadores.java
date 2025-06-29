package br.upe.data.beans;

import java.time.LocalDate;

public class Indicadores {
    private Long id;
    private LocalDate data;
    private double peso;
    private double altura; 
    private double porcentagemGordura;
    private double porcentagemMM;
    private double imc;

    public Indicadores() {}

    public Indicadores(LocalDate data, double peso, double altura, double porcentagemGordura, double porcentagemMM) {
        this.data = data;
        this.peso = peso;
        this.altura = altura;
        this.porcentagemGordura = porcentagemGordura;
        this.porcentagemMM = porcentagemMM;
        this.calculaImc();
    }

    private void calculaImc() {
        double alturaMetros = altura / 100;
        this.imc = peso / (alturaMetros * alturaMetros);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("Peso deve ser maior que zero.");
        }
        this.peso = peso;
        this.calculaImc();
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        if (altura <= 0) {
            throw new IllegalArgumentException("Altura deve ser maior que zero.");
        }
        this.altura = altura;
        this.calculaImc();
    }

    public double getPorcentagemGordura() {
        return porcentagemGordura;
    }

    public void setPorcentagemGordura(double porcentagemGordura) {
        this.porcentagemGordura = porcentagemGordura;
    }

    public double getPorcentagemMM() {
        return porcentagemMM;
    }

    public void setPorcentagemMM(double porcentagemMM) {
        this.porcentagemMM = porcentagemMM;
    }

    public double getImc() {
        return imc;
    }

    @Override
    public String toString() {
        return "Indicadores{" +
                "data=" + data +
                ", peso=" + peso +
                ", altura=" + altura +
                ", porcentagemGordura=" + porcentagemGordura +
                ", porcentagemMM=" + porcentagemMM +
                ", imc=" + imc +
                '}';
    }
}
