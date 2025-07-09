package clinica;

import java.util.ArrayList;

/**
 * Classe que representa um tutor de animais na clínica veterinária.
 * Herda da classe Pessoa e adiciona o atributo endereço e uma lista de animais.
 */
public class Tutor extends Pessoa{
    protected String endereco;
    private ArrayList<Animal> animais;

    public Tutor(String nome, String cpf, String email, String telefone, String endereco) {
        super(nome, cpf, email, telefone);
        this.endereco = endereco;
        this.animais = new ArrayList<>();
    }
    
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public ArrayList<Animal> getAnimais() {
        return animais;
    }

    public void addAnimal(Animal animal) {
        animais.add(animal);
    }

    public void removerAnimal(Animal animal) {
        animais.remove(animal);
    }
}
