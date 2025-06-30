import br.upe.data.beans.Indicadores;
import br.upe.service.ExportacaoService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ExportacaoServiceTest {

    @Test
    public void testExportarParaCSV() {
        List<Indicadores> lista = Arrays.asList(
                new Indicadores(LocalDate.of(2025, 6, 1), 75, 175, 20, 40),
                new Indicadores(LocalDate.of(2025, 6, 10), 74, 175, 19, 41)
        );

        ExportacaoService service = new ExportacaoService();
        service.exportarParaCSV(lista, "indicadores_test.csv");

        // Aqui você poderia adicionar assertivas para verificar se o arquivo foi criado
        // ou se o conteúdo bate com o esperado (ex.: usando Files.readAllLines).
    }
}
