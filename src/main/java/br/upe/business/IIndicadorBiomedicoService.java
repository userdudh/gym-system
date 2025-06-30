package br.upe.business;

import br.upe.data.beans.IndicadorBiomedico;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IIndicadorBiomedicoService {
    IndicadorBiomedico cadastrarIndicador(int idUsuario, LocalDate data, double pesoKg, double alturaCm, double percentualGordura, double percentualMassaMagra);

    void importarIndicadoresCsv(int idUsuario, String caminhoArquivoCsv);

    List<IndicadorBiomedico> gerarRelatorioPorData(int idUsuario, LocalDate dataInicio, LocalDate dataFim);

    RelatorioDiferencaIndicadores gerarRelatorioDiferenca(int idUsuario, LocalDate dataInicio, LocalDate dataFim);

    List<IndicadorBiomedico> listarTodosDoUsuario(int idUsuario);

    class RelatorioDiferencaIndicadores {
        public LocalDate dataInicio;
        public LocalDate dataFim;
        public Optional<IndicadorBiomedico> indicadorInicial;
        public Optional<IndicadorBiomedico> indicadorFinal;

        public double diferencaPeso;
        public double diferencaAltura;
        public double diferencaPercentualGordura;
        public double diferencaPercentualMassaMagra;
        public double diferencaImc;

        public RelatorioDiferencaIndicadores() {
            this.indicadorInicial = Optional.empty();
            this.indicadorFinal = Optional.empty();
        }

        public void calcularDiferencas() {
            if (indicadorInicial.isPresent() && indicadorFinal.isPresent()) {
                diferencaPeso = indicadorFinal.get().getPesoKg() - indicadorInicial.get().getPesoKg();
                diferencaAltura = indicadorFinal.get().getAlturaCm() - indicadorInicial.get().getAlturaCm();
                diferencaPercentualGordura = indicadorFinal.get().getPercentualGordura() - indicadorInicial.get().getPercentualGordura();
                diferencaPercentualMassaMagra = indicadorFinal.get().getPercentualMassaMagra() - indicadorInicial.get().getPercentualMassaMagra();
                diferencaImc = indicadorFinal.get().getImc() - indicadorInicial.get().getImc();
            } else {
                diferencaPeso = 0;
                diferencaAltura = 0;
                diferencaPercentualGordura = 0;
                diferencaPercentualMassaMagra = 0;
                diferencaImc = 0;
            }
        }
    }
}