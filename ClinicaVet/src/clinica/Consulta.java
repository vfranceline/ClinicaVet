package clinica;

import java.time.LocalDate;

public class Consulta implements Faturavel {
    private Veterinario veterinario;
    private String problema;
    private String diagnostico;
    private String medicamento;
    private LocalDate dataConsulta; // Alterado de String para LocalDate
    private double preco;

    public Consulta(Veterinario veterinario, String problema, String diagnostico,
                    String medicamento, LocalDate dataConsulta) { // Construtor atualizado
        this.veterinario = veterinario;
        this.problema = problema;
        this.diagnostico = diagnostico;
        this.medicamento = medicamento;
        this.dataConsulta = dataConsulta;
        this.preco = veterinario.getPrecoPorEspecialidade();
    }
    
    // Getters e Setters atualizados
    public Veterinario getVeterinario() { return veterinario; }
    public void setVeterinario(Veterinario veterinario) { this.veterinario = veterinario; }

    public String getProblema() { return problema; }
    public void setProblema(String problema) { this.problema = problema; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String medicamento) { this.medicamento = medicamento; }

    public LocalDate getDataConsulta() { return dataConsulta; } // Retorna LocalDate
    public void setDataConsulta(LocalDate dataConsulta) { this.dataConsulta = dataConsulta; } // Recebe LocalDate

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    @Override
    public double calcularValor() {
        return this.preco;
    }
}
