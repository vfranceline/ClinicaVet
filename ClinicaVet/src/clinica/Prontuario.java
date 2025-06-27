package clinica;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Prontuario implements Imprimivel {
    private ArrayList<Consulta> consultas;

    public Prontuario() {
        this.consultas = new ArrayList<>();
    }

    public void adicionarConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }

    public ArrayList<Consulta> getConsultas() {
        return consultas;
    }

    public void removerConsulta(Consulta consulta) {
        consultas.remove(consulta);
    }

    @Override
    public String gerarConteudoImpressao() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder textoFinal = new StringBuilder("--- Prontuário do Animal ---\n");
        textoFinal.append("----------------------------\n");

        for (int i = consultas.size() - 1; i >= 0; i--) {
            Consulta consultaAtual = this.consultas.get(i);
            textoFinal.append("Data: ").append(consultaAtual.getDataConsulta().format(formatador)).append("\n");
            textoFinal.append("Veterinário: ").append(consultaAtual.getVeterinario().getNome()).append("\n");
            textoFinal.append("Problema: ").append(consultaAtual.getProblema()).append("\n");
            textoFinal.append("Diagnóstico: ").append(consultaAtual.getDiagnostico()).append("\n");
            textoFinal.append("Medicamentos: ").append(consultaAtual.getMedicamento()).append("\n");
            textoFinal.append("----------------------------\n");
        }

        return textoFinal.toString();
    }
}
