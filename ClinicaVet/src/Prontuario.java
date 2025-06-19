// src/Prontuario.java
import java.util.ArrayList;

public class Prontuario implements Imprimivel{
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

    /**
     * gera texto para impressão, listando as consultas da mais recente para a mais antiga.
     * @return string com o conteúdo exibido
     */
    
    @Override
    public String gerarConteudoImpressao() {
        String textoFinal = "--- Prontuário do Animal ---\n";
        textoFinal += "----------------------------\n";

        // laço 'for' para percorrer a lista de consultas DE TRÁS PARA FRENTE.
        for (int i = consultas.size() - 1; i >= 0; i--) {
            Consulta consultaAtual = this.consultas.get(i);

            Veterinario veterinarioDaConsulta = consultaAtual.getNome();

            textoFinal += "Data: " + consultaAtual.getInicio() + "\n";
            textoFinal += "Veterinário: " + veterinarioDaConsulta.getNome() + "\n";
            textoFinal += "Problema: " + consultaAtual.getProblema() + "\n";
            textoFinal += "Diagnóstico: " + consultaAtual.getDiagnostico() + "\n";
            textoFinal += "Medicamentos: " + consultaAtual.getMedicamento() + "\n";
            textoFinal += "----------------------------\n";
        }

        return textoFinal;
    }
    
}