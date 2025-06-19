import java.util.ArrayList;
import java.util.List;
public class CartaoVacina implements Imprimivel{
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

    /**
     * Gera texto para impressão, listando as vacinas aplicadas.
     * @return string com o conteúdo a ser exibido.
     */
    @Override
    public String gerarConteudoImpressao(){
        String textoFinal = "--- Cartão de Vacinas ---\n";
        textoFinal += "---------------------------\n";

        for(VacinaAplicada vacinaAplicada : this.vacinasAplicadas){
            textoFinal += "Vacina: " + vacinaAplicada.getVacina().getNome() + "\n";
            textoFinal += "Data de aplicação: " + vacinaAplicada.getDataDeAplicacao() + "\n";
            textoFinal += "Data de validade: " + vacinaAplicada.getDataDeValidade() + "\n";
            textoFinal += "---------------------------\n";
        }
        return textoFinal;
    }
}