public class VacinaAplicada {
    private Vacina vacina;
    private String dataDeAplicacao;
    private String dataDeValidade;

    public VacinaAplicada(Vacina vacina, String dataDeAplicacao, String dataDeValidade) {
        this.vacina = vacina;
        this.dataDeAplicacao = dataDeAplicacao;
        this.dataDeValidade = dataDeValidade;
    }
    
    // Getters
    public Vacina getVacina() { return vacina; }
    public String getDataDeAplicacao() { return dataDeAplicacao; }
    public String getDataDeValidade() { return dataDeValidade; }
}