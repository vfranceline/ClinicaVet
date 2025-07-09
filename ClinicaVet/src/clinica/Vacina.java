package clinica;


public class Vacina implements Faturavel{
    private String nome;
    private double preco;

    public Vacina(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public double calcularValor() { //implementa interface de faturavel
        return this.preco; // O valor da vacina é o seu preço tabelado
    }

    /**
     * Retorna o nome da vacina para ser exibido em componentes de UI como JComboBox.
     */
    @Override
    public String toString() {
        return this.nome;
    }
}
