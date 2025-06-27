package clinica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // 1. Inicialização da Clínica
        System.out.println("--- Bem-vindo à Clínica Veterinária EXO-L ---");
        Clinica clinica = new Clinica();
        System.out.println("Sistema da clínica pronto para operar.\n");

        // 2. Cadastro de Pessoas (Tutores e Veterinário)
        System.out.println("--- Cadastrando Equipe e Clientes ---");
        Tutor tutorSuho = new Tutor("Kim Junmyeon (Suho)", "001.001.001-01", "suho.kim@weareone.com", "(11) 91111-1111", "Avenida EXO, 1");
        clinica.cadastrarTutor(tutorSuho);

        Tutor tutorBaekhyun = new Tutor("Byun Baekhyun", "004.004.004-04", "baekhyun.byun@weareone.com", "(11) 94444-4444", "Rua K-BBING, 4");
        clinica.cadastrarTutor(tutorBaekhyun);

        Veterinario vetKyungsoo = new Veterinario("Do Kyungsoo (D.O.)", "012.012.012-12", "kyungsoo.do@vet.com", "(11) 91212-1212", "Cardiologia e Oftalmologia", "CRMV-SP 121212", "Integral");
        clinica.cadastrarVeterinario(vetKyungsoo);
        System.out.println();

        // 3. Cadastro dos Animais
        System.out.println("--- Cadastrando Pacientes Pets ---");
        // O cachorrinho do Suho
        Animal petBunny = new Animal("Bunny", "Bichon Frise", tutorSuho, LocalDate.of(2021, 5, 22));
        clinica.cadastrarAnimal(petBunny);

        // O famoso cachorro do Baekhyun
        Animal petMongryong = new Animal("Mongryong", "Welsh Corgi", tutorBaekhyun, LocalDate.of(2015, 5, 6));
        clinica.cadastrarAnimal(petMongryong);
        System.out.println();

        // 4. Demonstração de Agendamento e Cancelamento
        System.out.println("--- Gerenciando Agendamentos ---");
        LocalDate dataAgendamento = LocalDate.of(2025, 7, 10);
        Agendamento agendamentoMongryong = new Agendamento(dataAgendamento, "10:00", "Check-up Anual", petMongryong);

        // Agendando a consulta
        clinica.agendar(agendamentoMongryong);

        // Cancelando a consulta
        System.out.println("Baekhyun precisa cancelar o agendamento devido a um show...");
        clinica.cancelarAgendamento(agendamentoMongryong);
        System.out.println();

        // 5. Atendimento do pet do Suho
        System.out.println("--- Realizando Atendimento para 'Bunny' ---");
        LocalDate dataConsulta = LocalDate.of(2025, 6, 19);
        Consulta consultaBunny = new Consulta(vetKyungsoo, "Tosse depois de latir muito no show", "Excesso de fofura", 
                                                "Dar mais petiscos e carinho", dataConsulta, 250.00);
        clinica.realizarConsulta(petBunny, consultaBunny); // Necessita de um método realizarConsulta melhor elaborado na classe Clínica!!!
        System.out.println();

        // 6. Aplicação de uma Vacina no pet do Suho
        System.out.println("--- Aplicando Vacina em 'Bunny' ---");
        Vacina vacinaV10 = new Vacina("V10 (Múltipla)", 120.00);
        clinica.aplicarVacina(petBunny, vacinaV10, dataConsulta, dataConsulta.plusYears(1));
        System.out.println();

        // 7. Consulta de Vacinas a vencer
        System.out.println("--- Consultando Vacina em 'Bunny' ---");
        clinica.consultarVacinasAVencer(petBunny, dataConsulta.plusYears(1).minusDays(15));

        // 8. Gerar Cobrança para o Suho
        System.out.println("--- Gerando Faturamento para Suho ---");
        List<Faturavel> itensFaturaveisSuho = new ArrayList<>();
        itensFaturaveisSuho.add(consultaBunny);
        itensFaturaveisSuho.add(vacinaV10);
        clinica.emitirCobranca(tutorSuho, itensFaturaveisSuho);
        System.out.println();

        // 9. Impressão de Documentos do Bunny
        System.out.println("--- Imprimindo Documentos de 'Bunny' ---\n");
        clinica.imprimirDocumento(petBunny.getProntuario());
        clinica.imprimirDocumento(petBunny.getCartaoVacina());

        System.out.println("--- Sistema Finalizado. We Are One! ---");
    }
}
