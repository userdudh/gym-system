package br.upe.ui;

import br.upe.business.IIndicadorBiomedicoService;
import br.upe.business.IndicadorBiomedicoService;
import br.upe.data.beans.IndicadorBiomedico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuIndicadores {

    private final Scanner sc;
    private final int idUsuarioLogado;
    private final IIndicadorBiomedicoService indicadorService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public MenuIndicadores(Scanner scanner, int idUsuarioLogado) {
        this.sc = scanner;
        this.idUsuarioLogado = idUsuarioLogado;
        this.indicadorService = new IndicadorBiomedicoService();
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n===== MEUS INDICADORES BIOMÉDICOS =====");
            System.out.println("1. Cadastrar Novo Indicador");
            System.out.println("2. Importar Indicadores (CSV)");
            System.out.println("3. Ver Relatório por Data");
            System.out.println("4. Ver Relatório de Diferença");
            System.out.println("0. Voltar");
            System.out.print("\nEscolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    cadastrarNovoIndicador();
                    break;
                case 2:
                    importarIndicadores();
                    break;
                case 3:
                    verRelatorioPorData();
                    break;
                case 4:
                    verRelatorioDiferenca();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma das opções válidas.");
            }
        } while (opcao != 0);
    }

    private void cadastrarNovoIndicador() {
        System.out.println("\n===== CADASTRAR NOVO INDICADOR =====");
        System.out.print("Data (DD/MM/AAAA, deixe em branco para hoje): ");
        String dataStr = sc.nextLine();
        LocalDate data = LocalDate.now();
        if (!dataStr.isEmpty()) {
            try {
                data = LocalDate.parse(dataStr, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.err.println("Formato de data inválido. Usando a data de hoje.");
            }
        }

        System.out.print("Peso (kg, ex: 75.5): ");
        double peso;
        try {
            peso = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Peso inválido. Digite um número.");
            return;
        }

        System.out.print("Altura (cm, ex: 175.0): ");
        double altura;
        try {
            altura = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Altura inválida. Digite um número.");
            return;
        }

        System.out.print("Percentual de Gordura (%, ex: 15.2): ");
        double percGordura;
        try {
            percGordura = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Percentual de gordura inválido. Digite um número.");
            return;
        }

        System.out.print("Percentual de Massa Magra (%, ex: 70.8): ");
        double percMassaMagra;
        try {
            percMassaMagra = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Percentual de massa magra inválido. Digite um número.");
            return;
        }

        try {
            IndicadorBiomedico novo = indicadorService.cadastrarIndicador(idUsuarioLogado, data, peso, altura, percGordura, percMassaMagra);
            System.out.println("Indicador cadastrado com sucesso! IMC: " + String.format("%.1f", novo.getImc()));
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao cadastrar indicador: " + e.getMessage());
        }
    }

    private void importarIndicadores() {
        System.out.println("\n===== IMPORTAR INDICADORES (CSV) =====");
        System.out.println("Formato CSV esperado: data(DD/MM/AAAA);peso;altura;perc_gordura;perc_massa_magra");
        System.out.print("Caminho completo do arquivo CSV para importação: ");
        String caminhoArquivo = sc.nextLine();

        try {
            indicadorService.importarIndicadoresCsv(idUsuarioLogado, caminhoArquivo);
            System.out.println("Solicitação de importação enviada. Verifique mensagens no console.");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro na importação: " + e.getMessage());
        }
    }

    private void verRelatorioPorData() {
        System.out.println("\n===== RELATÓRIO DE INDICADORES POR DATA =====");
        LocalDate dataInicio = pedirData("Data de Início (DD/MM/AAAA): ");
        if (dataInicio == null) return;
        LocalDate dataFim = pedirData("Data de Fim (DD/MM/AAAA): ");
        if (dataFim == null) return;

        try {
            List<IndicadorBiomedico> resultados = indicadorService.gerarRelatorioPorData(idUsuarioLogado, dataInicio, dataFim);
            if (resultados.isEmpty()) {
                System.out.println("Nenhum indicador encontrado para o período e usuário.");
            } else {
                System.out.println("\n--- Indicadores de " + dataInicio.format(DATE_FORMATTER) + " a " + dataFim.format(DATE_FORMATTER) + " ---");
                resultados.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    private void verRelatorioDiferenca() {
        System.out.println("\n===== RELATÓRIO DE DIFERENÇA DE INDICADORES =====");
        LocalDate dataInicio = pedirData("Data de Início do Período (DD/MM/AAAA): ");
        if (dataInicio == null) return;
        LocalDate dataFim = pedirData("Data de Fim do Período (DD/MM/AAAA): ");
        if (dataFim == null) return;

        try {
            IIndicadorBiomedicoService.RelatorioDiferencaIndicadores relatorio = indicadorService.gerarRelatorioDiferenca(idUsuarioLogado, dataInicio, dataFim);

            System.out.println("\n--- Diferença de Indicadores de " + dataInicio.format(DATE_FORMATTER) + " a " + dataFim.format(DATE_FORMATTER) + " ---");
            if (!relatorio.indicadorInicial.isPresent()) {
                System.out.println("Nenhum indicador encontrado no período selecionado para calcular a diferença.");
                return;
            }

            System.out.println("Início do Período: " + relatorio.indicadorInicial.get());
            System.out.println("Fim do Período:    " + relatorio.indicadorFinal.get());
            System.out.println("\n--- Diferenças ---");
            System.out.printf("Peso: %.1fkg\n", relatorio.diferencaPeso);
            System.out.printf("Altura: %.1fcm\n", relatorio.diferencaAltura);
            System.out.printf("Gordura: %.1f%%\n", relatorio.diferencaPercentualGordura);
            System.out.printf("Massa Magra: %.1f%%\n", relatorio.diferencaPercentualMassaMagra);
            System.out.printf("IMC: %.1f\n", relatorio.diferencaImc);

        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    private LocalDate pedirData(String mensagem) {
        System.out.print(mensagem);
        String dataStr = sc.nextLine();
        try {
            return LocalDate.parse(dataStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Formato de data inválido. Use DD/MM/AAAA.");
            return null;
        }
    }
}