public class Veterinario {
    protect String especialidade;
    protect String cfmv;

    public Veterinario(String nome, String cpf, String email, String telefone, String especialidade, String cfmv) {
        super(nome, cpf, email, telefone);
        this.especialidade = especialidade;
        this.cfmv = cfmv;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCfmv() {
        return cfmv;
    }

    public void setCfmv(String cfmv) {
        this.cfmv = cfmv;
    }
}
