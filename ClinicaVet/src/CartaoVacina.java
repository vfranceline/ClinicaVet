import java.util.ArrayList;
import java.util.List;

public class CartaoVacina {
    //Atributos
    private String nome;
    private String validade, data;
    private List<Vacina> vacinas = new ArrayList<>(); 

    //Construtor
    public CartaoVacina(String nome, String validade, String data){
        this.nome = nome;
        this.validade = validade;
        this.data = data;
    }

    //Getters e Setters
    public String getNome(){ return nome; }
    public void setNome(String nome){ this.nome = nome; }

    public Data getValidade(){ return validade;}
    public void setValidade(String validade){ this.validade = validade; }

    public Data getData(){ return data; }
    public void setData(String data){ this.data = data; }
}
