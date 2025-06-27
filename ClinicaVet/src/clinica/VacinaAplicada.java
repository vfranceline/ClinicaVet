import java.time.LocalDate;
public class VacinaAplicada {
    private Vacina vacina;
    private LocalDate dataDeAplicacao; // Alterado de String para LocalDate
    private LocalDate dataDeValidade;  // Alterado de String para LocalDate

    public VacinaAplicada(Vacina vacina, LocalDate dataDeAplicacao, LocalDate dataDeValidade) { // Construtor atualizado
        this.vacina = vacina;
        this.dataDeAplicacao = dataDeAplicacao;
        this.dataDeValidade = dataDeValidade;
    }
    
    // Getters atualizados
    public Vacina getVacina() { return vacina; }
    public LocalDate getDataDeAplicacao() { return dataDeAplicacao; }
    public LocalDate getDataDeValidade() { return dataDeValidade; }
}
