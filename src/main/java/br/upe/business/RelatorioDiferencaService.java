public void exportarRelatorioDiferencaCSV(RelatorioDiferencaIndicadores relatorio, String caminhoArquivo) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
        // Cabeçalho com o período e os nomes das colunas das diferenças
        writer.write("Periodo, DiferencaPeso, DiferencaAltura, DiferencaPercentualGordura, DiferencaPercentualMassaMagra, DiferencaIMC");
        writer.newLine();

       
        writer.write(String.format("%s a %s, %.2f, %.2f, %.2f, %.2f, %.2f",
                relatorio.dataInicio, relatorio.dataFim,
                relatorio.diferencaPeso,
                relatorio.diferencaAltura,
                relatorio.diferencaPercentualGordura,
                relatorio.diferencaPercentualMassaMagra,
                relatorio.diferencaImc));
        writer.newLine();

        System.out.println("Relatório de diferença exportado com sucesso em: " + caminhoArquivo);

    } catch (IOException e) {
        e.printStackTrace();
    }
}
