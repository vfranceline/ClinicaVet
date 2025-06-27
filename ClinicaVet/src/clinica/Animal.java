package clinica;

import java.time.LocalDate;

public class Animal {
    private String nome;
    private String raca;
    private Tutor tutor;
    private LocalDate dataDeNascimento;
    private CartaoVacina cartaoVacina;
    private Prontuario prontuario;

    public Animal(String nome, String raca, Tutor tutor, LocalDate dataDeNascimento) {
        this.nome = nome;
        this.raca = raca;
        this.tutor = tutor;
        this.dataDeNascimento = dataDeNascimento;
        this.cartaoVacina = new CartaoVacina();
        this.prontuario= new Prontuario();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public CartaoVacina getCartaoVacina() {
        return cartaoVacina;
    }

    public void setCartaoVacina(CartaoVacina cartaoVacina) {
        this.cartaoVacina = cartaoVacina;
    }

    public void adicionarConsulta(Consulta consulta) {
        this.prontuario.adicionarConsulta(consulta);
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
    }

}
