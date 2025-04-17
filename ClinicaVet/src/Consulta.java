public class Consulta {
    //Atributos
    private Veterinario nome, especialidade;
    private String problema, diagnostico, medicamento;
    private Horario inicio;
    private double preco;

    //Construtor
    public Consulta(Veterinario nome, Veterinario especialidade, String problema, String diagnostico,
                    String medicamento, Horario inicio, double preco){
        this.nome = nome;
        this.especialidade = especialidade;
        this.problema = problema;
        this.diagnostico = diagnostico;
        this.medicamento = medicamento;
        this.inicio = inicio;
        this.preco = preco;
    }

    //Getters e Setters
    public Veterinario getNome(){ return nome; }
    public void setNome(Veterinario nome){ this.nome = nome; }
    
    public Veterinario getEspecialidade(){ return especialidade; }
    public void setEspecialidade(Veterinario especialidade){ this.especialidade = especialidade; }
    
    public String getProblema(){ return problema; }
    public void setProblema(String problema){ this.problema = problema; }
        
    public String getDiagnostico(){ return diagnostico; }
    public void setDiagnostico(String diagnostico){ this.diagnostico = diagnostico; }
        
    public String getMedicamento(){ return medicamento; }
    public void setMedicamento(String medicamento){ this.medicamento = medicamento; }

    public Horario getInicio(){ return inicio; }
    public void setInicio(Horario inicio){ this.inicio = inicio; }

    public double getPreco(){ return preco; }
    public void setPreco(double preco){ this.preco = preco; }
    

}
