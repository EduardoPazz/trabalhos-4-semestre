# Trabalho Conflitos Bélicos - Parte 3 - ACH2004 Bancos de Dados 1

<!-- TOC -->
  * [Alunos](#alunos)
  * [Desenvolvimento](#desenvolvimento)
    * [Arquitetura](#arquitetura)
      * [Views](#views)
      * [Services](#services)
      * [Repositories](#repositories)
    * [Configuração do Banco de Dados](#configurao-do-banco-de-dados)
<!-- TOC -->

## Alunos
| Nome                      | NUSP     |
|---------------------------|----------|
| João Eduardo da Paz Silva | 11845514 |
| Ryan ||
| Vinicius ||
| Silas ||

## Como executar

### PostgreSQL e Docker Compose

Para executar o projeto, é necessário ter uma instância do PostgreSQL executando localmente com as seguintes configurações:

| Configuração | Valor      |
|--------------|------------|
| Host         | localhost  |
| Port         | 5432       |
| Database     | ep_db      |
| User         | ep_db_user |
| Password     | 1234       |

Para facilitar a configuração de tal banco de dados, é fornecido um [docker-compose.yml](docker-compose.yml). Para executá-lo, basta ter o `docker-compose` habilitado e executar o seguinte comando na pasta raiz do projeto, através de um terminal:

```bash
docker-compose up
```
ou
```bash
sudo docker-compose up
```

caso o sistema exija privilégios de administrador.

### Baixando as dependências e compilando o projeto com Gradle

Não é necessário ter o Gradle instalado. O projeto já possui um wrapper universal para o Gradle, que pode ser executado através do seguinte comando:

```bash
./gradlew shadowJar
```

### Executando o projeto

Uma vez que o projeto esteja compilado, basta executar o seguinte comando:

```bash
java -jar build/libs/ep-bd-1.0-SNAPSHOT-all.jar
```

O projeto irá configurar e popular o banco de dados apenas na primeira execução.

## Desenvolvimento

O projeto foi desenvolvido usando o SGBD PostgreSQL e a linguagem de programação Java em sua versão 17 incluindo, além de algumas bibliotecas nativas, as seguintes bibliotecas externas:

- Lombok: para reduzir código _boilerplate_.
- Shadow: para gerar um único arquivo JAR com todas as dependências.
- Spring Context: para facilitar a injeção de dependência.
- PostgreSQL JDBC Driver: para se conectar com o banco de dados.
- Java Text Utilities: para formatar tabelas.
- TODO: biblioteca dos gráficos

### Configuração do Banco de Dados

Toda a conexão e setup inicial das tabelas do banco de dados estão na classe `org.example.repository.DatabaseConfig`:

```java
  public static void setup() throws SQLException {
    System.out.println("Setting up database...");
    var connection = databaseConnection();

    var statement = connection.createStatement();

    try (statement) {
      boolean hasAlreadySetup = isDatabaseAlreadySetup(statement);

      if (hasAlreadySetup) {
        System.out.println("Database already setup");
        return;
      }

      createTables(statement);
      createTriggers(statement);
      populateTables(statement);
    }
  }
```

Os scripts de criação de tabelas podem ser encontrados no método `DatabaseConfig.createTables`, os scripts de criação de triggers no método `DatabaseConfig.createTriggers` e os scripts de inserção de dados de teste no método `DatabaseConfig.populateTables`.

Os dados inseridos através de `populateTables` são meramente para fins de teste de geração dos relatórios e gráficos.

#### Dados de teste necessários para o cadastro de novos registros:

##### Grupos Armados

|codigo|nome          |
|------|--------------|
|1     |PCC           |
|2     |Máfia Russa   |
|3     |Tropa de Elite|
|4     |Máfia Italiana|
|5     |Máfia Chinesa |
|6     |Máfia Japonesa|

### Arquitetura

As responsabilidades do projeto foram divididas em três camadas:

![img.png](arquitetura.png)

#### Views

Na camada Views, que se encontra no pacote `org.example.views`, está implementada toda a lógica de interação com o usuário através do terminal. Para gerar os relatórios e gráficos e permitir o cadastro de novos registros no banco de dados, a Views delega essa responsabilidade para a Services.

Cada classe da Views lida com um menu em específico. A `MainMenu` é a que está no topo da hierarquia:

```java
  public void run() {
    while (true) {
      String chosenOption = getValidInputWithOptions(scanner, """
          \n*** Conflitos Belicos - Menu Principal ***
          [1] - Fazer novos cadastros
          [2] - Gerar relatorios e graficos
          [Q] - Sair do programa
          """, List.of("1", "2", "Q"));

      switch (chosenOption) {
        case "1" -> registrationMenu.run();
        case "2" -> reportsMenu.run();
        case "Q" -> quit();
      }
    }
  }
```


#### Services

A camada Services, que se encontra no pacote `org.example.services`, é responsável por fazer a comunicação entre a Views e a Repositories.

A classe `services.RegistrationService` é responsável por fazer a conversão de dados para que sejam compatíveis com o banco de dados.

Já as classes `services.ReportsService` e `services.ChartsService` são responsáveis por gerar as tabelas e gráficos.

#### Repositories

A camada Repositories é responsável pela abstração da conexão com o banco de dados e sua manipulação com SQL. Ela se encontra no pacote `org.example.repository`.

A classe `WarConflictSelectionRepository` é responsável por buscar tuplas do banco de dados por meio de `SELECT`s e retornar os dados como matrizes de `String` para a camada Services.

A classe `WarConflictInsertionRepository` é responsável por inserir novas tuplas no banco de dados por meio de `INSERT`s com base nos dados validados pela camada Services.
