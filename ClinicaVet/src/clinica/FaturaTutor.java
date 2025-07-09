
package clinica;

import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * Classe responsável por gerar a fatura detalhada de um tutor, incluindo os custos
 * dos serviços prestados aos animais sob sua responsabilidade.
 * Implementa a interface Imprimivel para permitir a impressão do conteúdo formatado.
 */
public class FaturaTutor implements Imprimivel {
    private Tutor tutor;

    public FaturaTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public String gerarConteudoImpressao() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        double totalFatura = 0;

        sb.append("==============================================\n");
        sb.append("           FATURA - CLÍNICA VETERINÁRIA\n");
        sb.append("==============================================\n\n");
        sb.append("TUTOR: ").append(tutor.getNome()).append(" (CPF: ").append(tutor.getCpf()).append(")\n");
        sb.append("ENDEREÇO: ").append(tutor.getEndereco()).append("\n");
        sb.append("----------------------------------------------\n\n");
        sb.append("DETALHAMENTO DOS SERVIÇOS:\n\n");

        // Itera sobre cada animal do tutor para coletar os custos
        for (Animal animal : tutor.getAnimais()) {
            sb.append("  PACIENTE: ").append(animal.getNome().toUpperCase()).append(" (Raça: ").append(animal.getRaca()).append(")\n");
            sb.append("  ------------------------------------------\n");

            // Coleta os custos das consultas do prontuário
            List<Consulta> consultas = animal.getProntuario().getConsultas();
            if (!consultas.isEmpty()) {
                sb.append("    Consultas:\n");
                for (Consulta consulta : consultas) {
                    sb.append(String.format("    - Data: %s | Dr(a). %s | Valor: R$ %.2f\n",
                            consulta.getDataConsulta().format(formatador),
                            consulta.getVeterinario().getNome(),
                            consulta.getPreco()));
                    totalFatura += consulta.getPreco();
                }
            }

            // Coleta os custos das vacinas aplicadas
            List<VacinaAplicada> vacinas = animal.getCartaoVacina().getVacinasAplicadas();
            if (!vacinas.isEmpty()) {
                sb.append("    Vacinas:\n");
                for (VacinaAplicada vacinaApp : vacinas) {
                    sb.append(String.format("    - Data: %s | Vacina: %s | Valor: R$ %.2f\n",
                            vacinaApp.getDataDeAplicacao().format(formatador),
                            vacinaApp.getVacina().getNome(),
                            vacinaApp.getVacina().getPreco()));
                    totalFatura += vacinaApp.getVacina().getPreco();
                }
            }
            sb.append("\n");
        }

        sb.append("----------------------------------------------\n");
        sb.append(String.format("VALOR TOTAL DA FATURA: R$ %.2f\n", totalFatura));
        sb.append("==============================================\n");

        return sb.toString();
    }
}