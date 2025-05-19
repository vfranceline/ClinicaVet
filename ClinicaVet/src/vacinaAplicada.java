import java.util.ArrayList;
import java.util.List;

public class vacinaAplicada {
    //Atributos
    private String dataDeVacina;
    private String validade;

    //Construtor
    public vacinaAplicada(String dataDeVacina, String validade){
        this.dataDeVacina = dataDeVacina;
        this.validade = validade;
    }

    //Getters e Setters
    public String getDataDeVacina(){ return dataDeVacina; }
    public void setDataDeVacina(String dataDeVacina){ this.dataDeVacina = dataDeVacina; }

    public Data getValidade(){ return validade;}
    public void setValidade(String validade){ this.validade = validade; }
}
