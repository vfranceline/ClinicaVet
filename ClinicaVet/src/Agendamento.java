import java.time.LocalDate;
public class Agendamento {
    private LocalDate dataConsulta; // Alterado de String para LocalDate
    private String hora;
    private String especialidade;
    private Animal animal;

    public Agendamento(LocalDate dataConsulta, String hora, String especialidade, Animal animal) { // Construtor atualizado
        this.dataConsulta = dataConsulta;
        this.hora = hora;
        this.especialidade = especialidade;
        this.animal = animal;
    }

    public LocalDate getDataConsulta() { return dataConsulta; } // Retorna LocalDate
    public void setDataConsulta(LocalDate dataConsulta) { this.dataConsulta = dataConsulta; } // Recebe LocalDate

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }
}
