package br.upe.data.repository.impl;

import br.upe.data.beans.IndicadorBiomedico;
import br.upe.data.repository.IIndicadorBiomedicoRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class IndicadorBiomedicoRepositoryImpl implements IIndicadorBiomedicoRepository {

    private static final String ARQUIVO_CSV = "src/main/resources/data/indicadores_biomedicos.csv";
    private List<IndicadorBiomedico> indicadores;
    private AtomicInteger proximoId;

    public IndicadorBiomedicoRepositoryImpl() {
        this.indicadores = new ArrayList<>();
        this.proximoId = new AtomicInteger(0);
        carregarDoCsv();
    }

    // Busca o usuario pelo arquivo CSV
    private void carregarDoCsv() {
        try {
            Files.createDirectories(Paths.get("src/main/resources/data"));
        } catch (IOException e) {
            System.err.println("Erro ao criar diretório para CSV: " + e.getMessage());
            return;
        }

        File file = new File(ARQUIVO_CSV);
        if (!file.exists()) {
            System.out.println("Arquivo " + ARQUIVO_CSV + " não encontrado. Será criado vazio no primeiro salvamento.");
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Erro ao criar o arquivo CSV vazio: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            int maxId = 0;
            while ((linha = br.readLine()) != null) {
                IndicadorBiomedico indicador = parseLinhaCsv(linha);
                if (indicador != null) {
                    indicadores.add(indicador);
                    if (indicador.getId() > maxId) {
                        maxId = indicador.getId();
                    }
                }
            }
            proximoId.set(maxId + 1);
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV de indicadores biomédicos: " + e.getMessage());
        }
    }

    // Grava os indicadores no arquivo CSV
    private void escreverParaCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_CSV))) {
            for (IndicadorBiomedico indicador : indicadores) {
                bw.write(formatarLinhaCsv(indicador));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo CSV de indicadores biomédicos: " + e.getMessage());
        }
    }

    // Lê uma linha do arquivo CSV
    private IndicadorBiomedico parseLinhaCsv(String linha) {
        String[] partes = linha.split(";");
        if (partes.length == 8) {
            try {
                int id = Integer.parseInt(partes[0]);
                int idUsuario = Integer.parseInt(partes[1]);
                LocalDate data = LocalDate.parse(partes[2]);
                double pesoKg = Double.parseDouble(partes[3]);
                double alturaCm = Double.parseDouble(partes[4]);
                double percentualGordura = Double.parseDouble(partes[5]);
                double percentualMassaMagra = Double.parseDouble(partes[6]);
                double imc = Double.parseDouble(partes[7]);
                return new IndicadorBiomedico(id, idUsuario, data, pesoKg, alturaCm, percentualGordura, percentualMassaMagra, imc);
            } catch (NumberFormatException | DateTimeParseException e) {
                System.err.println("Erro ao converter número ou data em linha CSV de indicador: " + linha + " - " + e.getMessage());
                return null;
            }
        }
        System.err.println("Formato inválido de linha CSV de indicador: " + linha);
        return null;
    }

    // Formata uma linha no arquivo CSV
    private String formatarLinhaCsv(IndicadorBiomedico indicador) {
        return indicador.getId() + ";" +
                indicador.getIdUsuario() + ";" +
                indicador.getData().toString() + ";" +
                indicador.getPesoKg() + ";" +
                indicador.getAlturaCm() + ";" +
                indicador.getPercentualGordura() + ";" +
                indicador.getPercentualMassaMagra() + ";" +
                indicador.getImc();
    }

    // Salva os indicadores no arquivo CSV
    @Override
    public IndicadorBiomedico salvar(IndicadorBiomedico indicador) {
        if (indicador.getId() == 0) {
            indicador.setId(proximoId.getAndIncrement());
            indicadores.add(indicador);
        } else {
            editar(indicador);
        }
        escreverParaCsv();
        return indicador;
    }

    // Buscar indicadores por id
    @Override
    public Optional<IndicadorBiomedico> buscarPorId(int id) {
        return indicadores.stream()
                .filter(i -> i.getId() == id)
                .findFirst();
    }

    // Buscar todos os indicadores de um usuario
    @Override
    public List<IndicadorBiomedico> buscarTodosDoUsuario(int idUsuario) {
        return indicadores.stream()
                .filter(i -> i.getIdUsuario() == idUsuario)
                .collect(Collectors.toList());
    }

    // Listar indicadores de um usuario pelo periodo
    @Override
    public List<IndicadorBiomedico> buscarPorPeriodo(int idUsuario, LocalDate dataInicio, LocalDate dataFim) {
        return indicadores.stream()
                .filter(i -> i.getIdUsuario() == idUsuario &&
                        !i.getData().isBefore(dataInicio) &&
                        !i.getData().isAfter(dataFim))
                .collect(Collectors.toList());
    }

    // Verifica condições e altera indicadores
    @Override
    public void editar(IndicadorBiomedico indicador) {
        Optional<IndicadorBiomedico> existenteOpt = buscarPorId(indicador.getId());
        if (existenteOpt.isPresent()) {
            indicadores.removeIf(i -> i.getId() == indicador.getId());
            indicadores.add(indicador);
            escreverParaCsv();
        } else {
            System.err.println("Erro: Indicador com ID " + indicador.getId() + " não encontrado para edição.");
        }
    }

    // Verifica condições e deleta indicadores pelo id
    @Override
    public void deletar(int id) {
        boolean removido = indicadores.removeIf(i -> i.getId() == id);
        if (removido) {
            escreverParaCsv();
        } else {
            System.err.println("Erro: Indicador com ID " + id + " não encontrado para remoção.");
        }
    }

    @Override
    public int proximoId() {
        return proximoId.get();
    }
}