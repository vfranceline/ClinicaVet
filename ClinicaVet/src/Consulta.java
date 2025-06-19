public class Consulta implements Faturavel{
    private Veterinario veterinario; 
    private String problema;
    private String diagnostico;
    private String medicamento;
    private String dataConsulta; 
    private double preco;

    public Consulta(Veterinario veterinario, String problema, String diagnostico,
                    String medicamento, String dataConsulta, double preco) {
        this.veterinario = veterinario;
        this.problema = problema;
        this.diagnostico = diagnostico;
        this.medicamento = medicamento;
        this.dataConsulta = dataConsulta;
        this.preco = preco;
    }
    
    // Getters e Setters
    public Veterinario getVeterinario() { return veterinario; }
    public void setVeterinario(Veterinario veterinario) { this.veterinario = veterinario; }

    public String getProblema() { return problema; }
    public void setProblema(String problema) { this.problema = problema; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String medicamento) { this.medicamento = medicamento; }

    public String getDataConsulta() { return dataConsulta; }
    public void setDataConsulta(String dataConsulta) { this.dataConsulta = dataConsulta; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    @Override
    public double calcularValor(){
        return this.preco;
    }
}