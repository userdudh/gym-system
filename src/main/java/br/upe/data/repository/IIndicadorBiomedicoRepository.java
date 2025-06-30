package br.upe.data.repository;

import br.upe.data.beans.IndicadorBiomedico;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IIndicadorBiomedicoRepository {
    IndicadorBiomedico salvar(IndicadorBiomedico indicador);
    Optional<IndicadorBiomedico> buscarPorId(int id);
    List<IndicadorBiomedico> buscarTodosDoUsuario(int idUsuario);
    List<IndicadorBiomedico> buscarPorPeriodo(int idUsuario, LocalDate dataInicio, LocalDate dataFim);
    void editar(IndicadorBiomedico indicador);
    void deletar(int id);
    int proximoId();
}