public class CartaoVacina {
    //Atributos
    private String nome;
    private Data validade, data;

    //Construtor
    public CartaoVacina(String nome, Data validade, Data data){
        this.nome = nome;
        this.validade = validade;
        this.data = data;
    }

    //Getters e Setters
    public String getNome(){ return nome; }
    public void setNome(String nome){ this.nome = nome; }

    public Data getValidade(){ return validade;}
    public void setValidade(Data validade){ this.validade = validade; }

    public Data getData(){ return data; }
    public void setData(Data data){ this.data = data; }
}
