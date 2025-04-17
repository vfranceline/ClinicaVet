public class Cobranca {
    private double valor;
    private Data data;
    private String tipo;
    private int parcelas; // clquei essa + cas cliente esclha parcelar

    public Cobranca(double valor, Data data, String tipo, int parcelas) {
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.parcelas = parcelas;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }
}
