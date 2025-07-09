# ClinicaVet
Este projeto é uma aplicação em Java para gerenciamento de uma clínica veterinária, desenvolvida como parte da disciplina de Linguagem de Programação 2. O sistema permite o controle de tutores, animais, veterinários, consultas, vacinas e agendamentos.

O sistema foi projetado para realizar as seguintes operações:

* **Cadastros:**
    * **Tutores:** Inclusão de novos tutores com nome, CPF, e-mail, telefone e endereço.
    * **Veterinários:** Cadastro de veterinários, incluindo sua especialidade e número do CFMV.
    * **Animais:** Registro de pets associados a um tutor, com informações de nome, raça e data de nascimento.

* **Gerenciamento de Atendimentos:**
    * **Agendamento:** Permite agendar e cancelar consultas para os animais.
    * **Consulta:** Registra o histórico de consultas no prontuário do animal, detalhando o problema, diagnóstico e medicação.
    * **Vacinação:** Aplica vacinas e as registra no cartão de vacinação do pet.

* **Emissão de Documentos e Relatórios:**
    * **Prontuário do Animal:** Exibe todo o histórico de consultas, da mais recente para a mais antiga.
    * **Cartão de Vacina:** Gera um relatório com todas as vacinas aplicadas, incluindo datas de aplicação e validade.
    * **Consulta de Vacinas a Vencer:** Lista as vacinas que estão prestes a vencer em um determinado período.
* **Financeiro:**
    * **Faturamento:** Calcula e emite o valor total a ser pago por um tutor, somando os custos de consultas e vacinas.

## Diagrama de Classes

O diagrama abaixo ilustra a relação entre as principais classes do sistema:

![Diagrama de Classes](diagramaDeClasses.pdf)

## Estrutura do Projeto

O código-fonte está organizado da seguinte maneira para garantir clareza e manutenibilidade:


- **`/src`**: Contém os arquivos de código-fonte (`.java`) com a implementação da lógica da Clínica Veterinária.
- **`/bin`**: Pasta de destino para os arquivos executáveis gerados após a compilação.


## Como Executar

1.  **Pré-requisitos:**
    * Ter o JDK (Java Development Kit) instalado.

2.  **Compilação:**
    * Navegue até o diretório `ClinicaVet` do projeto pelo terminal.
    * Compile todos os arquivos `.java`:
        ```bash
        javac -d bin ClinicaVet/src/clinica/*.java src/GUI/*.java
        ```

3.  **Execução:**
    * Ainda no diretório `ClinicaVet`, execute a classe principal `Menu`:
        ```bash
        java -cp bin GUI.Menu 
        ```

