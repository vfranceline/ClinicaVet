import java.util.ArrayList;

public class Cobranca {
    private Data data;
    private int parcelas;
    private ArrayList<ItemCobranca> itens;

    public Cobranca(Data data, int parcelas) {
        this.data = data;
        this.parcelas = parcelas;
        this.itens = new ArrayList<>();
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public void addItem(ItemCobranca item) {
        itens.add(item);
    }

    public void removerItem(ItemCobranca item) {
        itens.remove(item);
    }

    public ArrayList<ItemCobranca> getItens() {
        return itens;
    }

    public double getValorTotal() {
        double total = 0;
        for (ItemCobranca item : itens) {
            total += item.getValor();
        }
        return total;
    }

    public double getValorParcela() {
        return getValorTotal() / parcelas;
    }
}
