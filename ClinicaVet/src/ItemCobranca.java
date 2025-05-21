public class ItemCobranca {
    private String descricao;
    private double valor;

    public ItemCobranca(String descricao, double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    void setDescricao(String descricao){
      this.descricao=descricao;
    }

    public double getValor() {
        return valor;
    }

    void setValor(String valor){
        this.valor=valor;
      }
}
