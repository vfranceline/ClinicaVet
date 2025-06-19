import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class CartaoVacina implements Imprimivel {
    private List<VacinaAplicada> vacinasAplicadas;

    public CartaoVacina() {
        this.vacinasAplicadas = new ArrayList<>();
    }

    public void adicionarVacinaAplicada(VacinaAplicada vacina) {
        this.vacinasAplicadas.add(vacina);
    }

    public List<VacinaAplicada> getVacinasAplicadas() {
        return vacinasAplicadas;
    }

    @Override
    public String gerarConteudoImpressao() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder textoFinal = new StringBuilder("--- Cartão de Vacinas ---\n");
        textoFinal.append("---------------------------\n");

        for (VacinaAplicada vacinaApp : this.vacinasAplicadas) {
            textoFinal.append("Vacina: ").append(vacinaApp.getVacina().getNome()).append("\n");
            textoFinal.append("Data de Aplicação: ").append(vacinaApp.getDataDeAplicacao().format(formatador)).append("\n");
            textoFinal.append("Data de Validade: ").append(vacinaApp.getDataDeValidade().format(formatador)).append("\n");
            textoFinal.append("---------------------------\n");
        }

        return textoFinal.toString();
    }
}
