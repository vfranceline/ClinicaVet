public class Agendamento {
    private String dataConsulta;
    private String hora;
    private String especialidade;
    private Animal animal;

    public Agendamento(String dataConsulta, String hora, String especialidade, Animal animal) {
        this.dataConsulta = dataConsulta;
        this.hora = hora;
        this.especialidade = especialidade;
        this.animal = animal;
    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
