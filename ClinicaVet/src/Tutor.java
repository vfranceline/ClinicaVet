public class Tutor extends Pessoa{
    protect String endereco;

    public Tutor(String nome, String cpf, String email, String telefone, String endereco) {
        super(nome, cpf, email, telefone);
        this.endereco = endereco;
    }
    
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
