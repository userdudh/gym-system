package br.upe.business;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class IndicadoresService {

    public void exportarParaCSV(List<Indicadores> indicadoresList, String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            // Escrever cabeçalho
            writer.write("Data,Peso,Altura,PorcentagemGordura,PorcentagemMM,IMC");
            writer.newLine();

            // Escrever dados
            for (Indicadores indicador : indicadoresList) {
                writer.write(String.format("%s,%.2f,%.2f,%.2f,%.2f,%.2f",
                        indicador.getData(),
                        indicador.getPeso(),
                        indicador.getAltura(),
                        indicador.getPorcentagemGordura(),
                        indicador.getPorcentagemMM(),
                        indicador.getImc()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
