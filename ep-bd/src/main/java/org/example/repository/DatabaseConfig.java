package org.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

  @Bean
  public static Connection databaseConnection() throws SQLException {
    String url = "jdbc:postgresql://localhost/ep_db";
    Properties props = new Properties();
    props.setProperty("user", "ep_db_user");
    props.setProperty("password", "1234");
    return DriverManager.getConnection(url, props);
  }

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

  private static boolean isDatabaseAlreadySetup(Statement statement)
      throws SQLException {

    ResultSet resultSet = statement.executeQuery("""
        SELECT * FROM pg_catalog.pg_tables WHERE schemaname = 'public';
        """);

    boolean hasAlreadySetup = resultSet.next();
    return hasAlreadySetup;
  }

  private static void createTables(Statement statement) throws SQLException {
    System.out.println("Creating tables...");
    statement.execute("""
        CREATE TYPE TIPO_ORGANIZACAO AS ENUM ('GOVERNAMENTAL', 'NÃO GOVERNAMENTAL', 'INTERNACIONAL');
        CREATE TYPE TIPO_AJUDA AS ENUM ('MÉDICA', 'DIPLOMÁTICA', 'PRESENCIAL');

        CREATE DOMAIN CONTAGEM_NUMERICA AS INT NULL CHECK (VALUE >= 0);



        CREATE TABLE CONFLITO
        (
            CODIGO           INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
            NOME             VARCHAR(100) NOT NULL,
            NR_MORTOS        CONTAGEM_NUMERICA,
            NR_FERIDOS       CONTAGEM_NUMERICA,
            FLAG_RACIAL      BOOL,
            FLAG_TERRITORIAL BOOL,
            FLAG_RELIGIOSO   BOOL,
            FLAG_ECONOMICO   BOOL,
            PRIMARY KEY (CODIGO)
        );

        CREATE TABLE GRUPO_ARMADO
        (
            CODIGO    INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
            NOME      VARCHAR(100) NOT NULL,
            NR_BAIXAS INT DEFAULT 0,
            PRIMARY KEY (CODIGO)
        );

        CREATE TABLE ORGANIZACAO
        (
            CODIGO                  INT              NOT NULL GENERATED ALWAYS AS IDENTITY,
            NOME                    VARCHAR(100)     NOT NULL,
            TIPO                    TIPO_ORGANIZACAO NOT NULL,
            ORGANIZACAO_DEPENDENCIA INT              NULL,
            PRIMARY KEY (CODIGO),
            FOREIGN KEY (ORGANIZACAO_DEPENDENCIA) REFERENCES ORGANIZACAO (CODIGO) ON DELETE SET NULL
        );

        CREATE TABLE TRAFICANTE
        (
            NOME VARCHAR(100) NOT NULL,
            PRIMARY KEY (NOME)
        );

        CREATE TABLE TIPO_ARMA
        (
            NOME                 VARCHAR(100) NOT NULL,
            INDICADOR_DESTRUICAO CONTAGEM_NUMERICA,
            PRIMARY KEY (NOME)
        );

        CREATE TABLE RACIAL
        (
            ID              INT NOT NULL GENERATED ALWAYS AS IDENTITY,
            CODIGO_CONFLITO INT NOT NULL,
            PRIMARY KEY (ID),
            FOREIGN KEY (CODIGO_CONFLITO) REFERENCES CONFLITO (CODIGO) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE ETNIA_ENFRENTADA
        (
            ID_RACIAL INT          NOT NULL,
            ID        INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
            ETNIA     VARCHAR(100) NOT NULL,
            PRIMARY KEY (ID),
            FOREIGN KEY (ID_RACIAL) REFERENCES RACIAL (ID) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE TERRITORIAL
        (
            ID              INT NOT NULL GENERATED ALWAYS AS IDENTITY,
            CODIGO_CONFLITO INT NOT NULL,
            PRIMARY KEY (ID),
            FOREIGN KEY (CODIGO_CONFLITO) REFERENCES CONFLITO (CODIGO) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE REGIAO_AFETADA
        (
            ID_TERRITORIAL INT          NOT NULL,
            ID             INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
            REGIAO         VARCHAR(100) NOT NULL,
            PRIMARY KEY (ID),
            FOREIGN KEY (ID_TERRITORIAL) REFERENCES TERRITORIAL (ID) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE RELIGIOSO
        (
            ID              INT NOT NULL GENERATED ALWAYS AS IDENTITY,
            CODIGO_CONFLITO INT NOT NULL,
            PRIMARY KEY (ID),
            FOREIGN KEY (CODIGO_CONFLITO) REFERENCES CONFLITO (CODIGO) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE RELIGIAO_AFETADA
        (
            ID_RELIGIOSO INT          NOT NULL,
            ID           INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
            REILIGIAO    VARCHAR(100) NOT NULL,
            PRIMARY KEY (ID),
            FOREIGN KEY (ID_RELIGIOSO) REFERENCES RELIGIOSO (ID) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE ECONOMICO
        (
            ID              INT NOT NULL GENERATED ALWAYS AS IDENTITY,
            CODIGO_CONFLITO INT NOT NULL,
            PRIMARY KEY (ID),
            FOREIGN KEY (CODIGO_CONFLITO) REFERENCES CONFLITO (CODIGO) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE MATERIA_PRIMA_DISPUTADA
        (
            ID_ECONOMICO  INT          NOT NULL,
            ID            INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
            MATERIA_PRIMA VARCHAR(100) NOT NULL,
            PRIMARY KEY (ID),
            FOREIGN KEY (ID_ECONOMICO) REFERENCES ECONOMICO (ID) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE PAISES_AFETADOS
        (
            CODIGO_CONFLITO INT          NOT NULL,
            NOME            VARCHAR(100) NOT NULL,
            PRIMARY KEY (CODIGO_CONFLITO, NOME),
            FOREIGN KEY (CODIGO_CONFLITO) REFERENCES CONFLITO (CODIGO)
        );

        CREATE TABLE LIDER_POLITICO
        (
            NOME                VARCHAR(100) NOT NULL,
            DESCRICAO_APOIO     VARCHAR(100) NOT NULL,
            CODIGO_GRUPO_ARMADO INT          NOT NULL,
            PRIMARY KEY (NOME),
            FOREIGN KEY (CODIGO_GRUPO_ARMADO) REFERENCES GRUPO_ARMADO (CODIGO) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE DIVISAO
        (
            CODIGO              INT NOT NULL GENERATED ALWAYS AS IDENTITY,
            NR_BAIXAS           CONTAGEM_NUMERICA,
            NR_SOLDADOS         CONTAGEM_NUMERICA,
            NR_AVIOES           CONTAGEM_NUMERICA,
            NR_BARCOS           CONTAGEM_NUMERICA,
            NR_TANQUES          CONTAGEM_NUMERICA,
            CODIGO_GRUPO_ARMADO INT NOT NULL,
            PRIMARY KEY (CODIGO),
            FOREIGN KEY (CODIGO_GRUPO_ARMADO) REFERENCES GRUPO_ARMADO (CODIGO) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE CHEFE_MILITAR
        (
            CODIGO              INT          NOT NULL GENERATED ALWAYS AS IDENTITY,
            FAIXA_HIERARQUICA   INT          NOT NULL,
            NOME_LIDER_POLITICO VARCHAR(100) NOT NULL,
            CODIGO_DIVISAO      INT          NOT NULL,
            PRIMARY KEY (CODIGO),
            FOREIGN KEY (NOME_LIDER_POLITICO) REFERENCES LIDER_POLITICO (NOME) ON DELETE CASCADE ON UPDATE CASCADE,
            FOREIGN KEY (CODIGO_DIVISAO) REFERENCES DIVISAO (CODIGO) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE GRUPOS_ARMADOS_ENVOLVIDOS
        (
            CODIGO_CONFLITO     INT  NOT NULL,
            CODIGO_GRUPO_ARMADO INT  NOT NULL,
            DATA_INCORPORACAO   DATE NOT NULL,
            DATA_SAIDA          DATE NULL,
            PRIMARY KEY (CODIGO_CONFLITO, CODIGO_GRUPO_ARMADO),
            FOREIGN KEY (CODIGO_CONFLITO) REFERENCES CONFLITO (CODIGO) ON DELETE CASCADE ON UPDATE CASCADE,
            FOREIGN KEY (CODIGO_GRUPO_ARMADO) REFERENCES GRUPO_ARMADO (CODIGO) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE ORGANIZACOES_ENVOLVIDAS
        (
            CODIGO_CONFLITO     INT        NOT NULL,
            CODIGO_ORGANIZACAO  INT        NOT NULL,
            NR_PESSOAS_MANTIDAS CONTAGEM_NUMERICA,
            TIPO_AJUDA          TIPO_AJUDA NOT NULL,
            DATA_INCORPORACAO   DATE       NOT NULL,
            DATA_SAIDA          DATE       NULL,
            PRIMARY KEY (CODIGO_CONFLITO, CODIGO_ORGANIZACAO),
            FOREIGN KEY (CODIGO_CONFLITO) REFERENCES CONFLITO (CODIGO) ON DELETE CASCADE ON UPDATE CASCADE,
            FOREIGN KEY (CODIGO_ORGANIZACAO) REFERENCES ORGANIZACAO (CODIGO) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE DIALOGA
        (
            CODIGO_ORGANIZACAO  INT          NOT NULL,
            NOME_LIDER_POLITICO VARCHAR(100) NOT NULL,
            PRIMARY KEY (CODIGO_ORGANIZACAO, NOME_LIDER_POLITICO),
            FOREIGN KEY (CODIGO_ORGANIZACAO) REFERENCES ORGANIZACAO (CODIGO) ON DELETE CASCADE ON UPDATE CASCADE,
            FOREIGN KEY (NOME_LIDER_POLITICO) REFERENCES LIDER_POLITICO (NOME) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE ESTOQUE_TRAFICANTE
        (
            NOME_TRAFICANTE       VARCHAR(100) NOT NULL,
            NOME_TIPO_ARMA        VARCHAR(100) NOT NULL,
            QUANTIDADE_DISPONIVEL CONTAGEM_NUMERICA,
            PRIMARY KEY (NOME_TRAFICANTE, NOME_TIPO_ARMA),
            FOREIGN KEY (NOME_TRAFICANTE) REFERENCES TRAFICANTE (NOME) ON DELETE CASCADE ON UPDATE CASCADE,
            FOREIGN KEY (NOME_TIPO_ARMA) REFERENCES TIPO_ARMA (NOME) ON DELETE CASCADE ON UPDATE CASCADE
        );

        CREATE TABLE FORNECIMENTO_ARMAS
        (
            CODIGO_GRUPO_ARMADO INT          NOT NULL,
            NOME_TIPO_ARMA      VARCHAR(100) NOT NULL,
            NOME_TRAFICANTE     VARCHAR(100) NOT NULL,
            PRIMARY KEY (CODIGO_GRUPO_ARMADO, NOME_TIPO_ARMA, NOME_TRAFICANTE),
            FOREIGN KEY (CODIGO_GRUPO_ARMADO) REFERENCES GRUPO_ARMADO (CODIGO) ON DELETE CASCADE ON UPDATE CASCADE,
            FOREIGN KEY (NOME_TIPO_ARMA) REFERENCES TIPO_ARMA (NOME) ON DELETE CASCADE ON UPDATE CASCADE,
            FOREIGN KEY (NOME_TRAFICANTE) REFERENCES TRAFICANTE (NOME) ON DELETE CASCADE ON UPDATE CASCADE
        );
        """);
  }

  private static void createTriggers(Statement statement) throws SQLException {
    System.out.println("Creating triggers...");
    ensureConflictsHierarchyExclusiveness(statement);
    ensureThreeMilitaryChiefsPerDivisionAtMost(statement);
    ensureTwoArmedGroupsPerConflictAtLeast(statement);
    ensureKillsConsistency(statement);
    ensureKillsConsistencyOnGrupoArmadoOperations(statement);
  }

  private static void ensureConflictsHierarchyExclusiveness(Statement statement)
      throws SQLException {
    statement.execute("""
        /*CRIACAO DA FUNCTION PARA VALIDACAO*/
        CREATE OR REPLACE FUNCTION verificaExclusividadeConflito()
        	RETURNS TRIGGER
        	LANGUAGE PLPGSQL
        	AS
        $$
        BEGIN
        	/*VERIFICA SE O CONFLITO É EXCLUSIVO*/
        	IF (NEW.FLAG_RACIAL = TRUE AND NEW.FLAG_TERRITORIAL = FALSE AND NEW.FLAG_RELIGIOSO = FALSE AND NEW.FLAG_ECONOMICO = FALSE) OR
        		(NEW.FLAG_RACIAL = FALSE AND NEW.FLAG_TERRITORIAL = TRUE AND NEW.FLAG_RELIGIOSO = FALSE AND NEW.FLAG_ECONOMICO = FALSE) OR
        		(NEW.FLAG_RACIAL = FALSE AND NEW.FLAG_TERRITORIAL = FALSE AND NEW.FLAG_RELIGIOSO = TRUE AND NEW.FLAG_ECONOMICO = FALSE) OR
        		(NEW.FLAG_RACIAL = FALSE AND NEW.FLAG_TERRITORIAL = FALSE AND NEW.FLAG_RELIGIOSO = FALSE AND NEW.FLAG_ECONOMICO = TRUE)
        	THEN
        		/*RETORNA OK SE ESTIVER DE ACORDO COM A VALIDACAO ACIMA*/
        		RETURN NEW;
        	ELSE
        		/*RETORNA ERRO SE NAO ESTIVER DE ACORDO COM A VALIDACAO ACIMA*/
        		RAISE EXCEPTION 'CONFLITO NÃO É EXCLUSIVO';
        	END IF;

        END;
        $$;

        /*CRIACAO DE TRIGGER*/

        CREATE TRIGGER verificaExclusividadeConflito
        	BEFORE INSERT
        	ON CONFLITO
        	FOR EACH ROW
        	EXECUTE PROCEDURE verificaExclusividadeConflito();
        """);
  }

  private static void ensureThreeMilitaryChiefsPerDivisionAtMost(
      Statement statement) throws SQLException {
    statement.execute("""
        /*CRIACAO DA FUNCTION PARA VALIDACAO*/
        CREATE OR REPLACE FUNCTION validaQuantidadeChefesPorDivisao()
        	RETURNS TRIGGER
        	LANGUAGE PLPGSQL
        	AS
        $$
        BEGIN
        	/*VARIAVEL DE CONTAGEM DE CHEFES MILITARES*/
        	DECLARE
           		counter    INTEGER;

        	BEGIN
        		/*BUSCA A QUANTIDADE DE CHEFES MILITARES QUE ESTÃO CONTIDOS NA DIVISAO DO NOVO CHEFE MILITAR QUE SERA INSERIDO*/
        		SELECT COUNT(1) INTO counter FROM DIVISAO
        			JOIN CHEFE_MILITAR ON CHEFE_MILITAR.CODIGO_DIVISAO = DIVISAO.CODIGO
        		WHERE
        			DIVISAO.CODIGO = NEW.CODIGO_DIVISAO;

        		/*SE HOUVER ATÉ 2 MILITARES, É PERMITIDO INSERIR ATÉ O TERCEIRO, SE HOUVER MAIS DE 2, RETORNA ERRO*/
        		IF (counter <= 2)
        		THEN
        			RETURN NEW;
        		ELSE
        			RAISE EXCEPTION 'DIVISÃO JÁ POSSUI 3 CHEFES MILITARES. NÃO É POSSÍVEL INSERIR UM NOVO CHEFE MILITAR A ESTA DIVISÃO';
        		END IF;
        	END;

        END;
        $$;

        /*CRIACAO DE TRIGGER*/
        CREATE TRIGGER validaQuantidadeChefesPorDivisao
        	BEFORE INSERT
        	ON CHEFE_MILITAR
        	FOR EACH ROW
        	EXECUTE PROCEDURE validaQuantidadeChefesPorDivisao();
        """);
  }

  private static void ensureTwoArmedGroupsPerConflictAtLeast(
      Statement statement) throws SQLException {
    statement.execute("""
        /*CRIACAO DA FUNCTION PARA VALIDACAO*/
        CREATE OR REPLACE FUNCTION validaQuantidadeGruposArmadosPorConflito()
        	RETURNS TRIGGER
        	LANGUAGE PLPGSQL
        	AS
        $$
        BEGIN

        	/*VARIAVEL DE CONTAGEM DE GRUPOS ARMADOS*/
        	DECLARE
           		counter    INTEGER;

        	BEGIN

        		/*VERIFICA SE ANTES DO UPDATE A DATA_SAIDA ERA NULA E DEPOS ELA FOI PREENCHIDA,
        		CASO SIM, É NECESSÁRIO FAZER A VALIDACAO*/
        		IF (OLD.DATA_SAIDA IS NULL AND NEW.DATA_SAIDA IS NOT NULL) THEN

        			/*CONTABILIZA QUANTOS GRUPOS ARMADOS AINDA
        			ESTAO ENVOLVIDOS NO CONFLITO DO GRUPO ARMADO QUE TERÁ A DATA DE SAIDA PREENCHIDA*/
        			SELECT COUNT(1) INTO counter FROM GRUPOS_ARMADOS_ENVOLVIDOS
        			WHERE GRUPOS_ARMADOS_ENVOLVIDOS.CODIGO_CONFLITO = NEW.CODIGO_CONFLITO
        				AND GRUPOS_ARMADOS_ENVOLVIDOS.DATA_SAIDA IS NULL;

        			/*SE AINDA HOUVER MAIS DE DOIS GRUPOS ARMADOS ENVOLVIDOS, PODE ATUALIZAR A DATA DE SAIDA DO GRUPO ARMADO ATUAL
        			CASO CONTRARIO, ENVIA MENSAGEM DE ERRO*/
        			IF (counter > 2)
        			THEN
        				RETURN NEW;
        			ELSE
        				RAISE EXCEPTION 'O GRUPO ARMADO NÃO PODE SAIR DO CONFLITO POIS O CONFLITO FICARÁ APENAS COM UM GRUPO ARMADO REGISTRADO';
        			END IF;

        		/*SE FOI ATUALIZADO QUALQUER OUTRO DADO QUE NÃO SEJA A DATA DE SAIDA, ATUALIZA NORMALMENTE*/
        		ELSE
        			RETURN NEW;
        		END IF;
        	END;

        END;
        $$;

        /*CRIACAO DE TRIGGER*/
        CREATE TRIGGER validaQuantidadeGruposArmadosPorConflito
        	BEFORE UPDATE
        	ON GRUPOS_ARMADOS_ENVOLVIDOS
        	FOR EACH ROW
        	EXECUTE PROCEDURE validaQuantidadeGruposArmadosPorConflito();
        """);
  }

  private static void ensureKillsConsistency(Statement statement)
      throws SQLException {
    statement.execute("""
        CREATE OR REPLACE FUNCTION atualizaNrBaixasGrupoArmado()
            RETURNS TRIGGER
            LANGUAGE PLPGSQL
        AS
        $$

        BEGIN
            UPDATE GRUPO_ARMADO
            SET NR_BAIXAS = (SELECT SUM(divisao.nr_baixas)
                             FROM DIVISAO
                             WHERE DIVISAO.CODIGO_GRUPO_ARMADO = grupo_armado.codigo);
            RETURN NULL;
        END;
        $$;

        CREATE TRIGGER validarNrBaixasGrupoArmado
            AFTER INSERT OR UPDATE OR DELETE
            ON DIVISAO
        EXECUTE PROCEDURE atualizaNrBaixasGrupoArmado();
        """);
  }


  private static void ensureKillsConsistencyOnGrupoArmadoOperations(
      Statement statement) throws SQLException {
    statement.execute("""
          CREATE OR REPLACE FUNCTION validarNrBaixasGrupoArmadoNoUpdate()
              RETURNS TRIGGER
              LANGUAGE PLPGSQL
          AS
          $$
          BEGIN
              DECLARE
                  sum_numero_baixas INTEGER;
              BEGIN
                  SELECT SUM(COALESCE(NR_BAIXAS,0)) INTO sum_numero_baixas  FROM DIVISAO
                  WHERE DIVISAO.CODIGO_GRUPO_ARMADO = NEW.CODIGO;

                  IF(sum_numero_baixas <> NEW.NR_BAIXAS)
                  THEN
                       RAISE EXCEPTION 'O VALOR DE NÚMERO DE BAIXAS DO GRUPO ARMADO NÃO É IGUAL À SOMA DE NÚMERO DE BAIXAS DAS DIVISÕES';
                  ELSE
                      RETURN NEW;
                  END IF;

              END;

          END;
          $$;


          CREATE TRIGGER validarNrBaixasGrupoArmadoNoUpdate
              BEFORE UPDATE
              OR INSERT
              ON GRUPO_ARMADO
              FOR EACH ROW
          EXECUTE PROCEDURE validarNrBaixasGrupoArmadoNoUpdate();



          CREATE OR REPLACE FUNCTION validarInsertGrupoArmadoParaNaoPreencherNumeroDeBaixas()
              RETURNS TRIGGER
              LANGUAGE PLPGSQL
          AS
          $$
          BEGIN
              IF(COALESCE(NEW.NR_BAIXAS,0) <> 0)
              THEN
                  RAISE EXCEPTION 'O VALOR DE NÚMERO DE BAIXAS DEVE SER IGUAL À 0 NO MOMENTO DA INSERÇÃO DO GRUPO ARMADO';
              ELSE
                  RETURN NEW;
              END IF;
          END;
          $$;

          CREATE TRIGGER validarInsertGrupoArmadoParaNaoPreencherNumeroDeBaixas
              BEFORE INSERT
              ON GRUPO_ARMADO
              FOR EACH ROW
          EXECUTE PROCEDURE validarInsertGrupoArmadoParaNaoPreencherNumeroDeBaixas();
        """);
  }

  private static void populateTables(Statement statement) throws SQLException {
    System.out.println("Populating tables...");
    statement.execute("""
        -- Conflitos

        insert into conflito (nome, nr_mortos, nr_feridos, flag_racial, flag_territorial, flag_religioso, flag_economico)
        select 'Conflito ' || i, random() * 1000000, random() * 1000000, true, false, false, false
        from generate_series(1, 25) i;

        insert into conflito (nome, nr_mortos, nr_feridos, flag_racial, flag_territorial, flag_religioso, flag_economico)
        select 'Conflito ' || i, random() * 1000000, random() * 1000000, false, true, false, false
        from generate_series(26, 30) i;

        insert into conflito (nome, nr_mortos, nr_feridos, flag_racial, flag_territorial, flag_religioso, flag_economico)
        select 'Conflito ' || i, random() * 1000000, random() * 1000000, false, false, true, false
        from generate_series(31, 60) i;

        insert into conflito (nome, nr_mortos, nr_feridos, flag_racial, flag_territorial, flag_religioso, flag_economico)
        select 'Conflito ' || i, random() * 1000000, random() * 1000000, false, false, false, true
        from generate_series(60, 70) i;


        -- Paises afetados
        insert into paises_afetados (codigo_conflito, nome)
        values (56, 'Brasil');
        insert into paises_afetados (codigo_conflito, nome)
        values (57, 'Brasil');
        insert into paises_afetados (codigo_conflito, nome)
        values (58, 'Brasil');
        insert into paises_afetados (codigo_conflito, nome)
        values (59, 'Argentina');
        insert into paises_afetados (codigo_conflito, nome)
        values (60, 'Argentina');
        insert into paises_afetados (codigo_conflito, nome)
        values (61, 'Argentina');


        -- Tráfico

        insert into traficante
        values ('Pablo Escobar');
        insert into traficante
        values ('El Chapo');
        insert into traficante
        values ('Nicolas Cage');

        insert into grupo_armado (nome, nr_baixas)
        values ('PCC', 0);
        insert into grupo_armado (nome, nr_baixas)
        values ('Máfia Russa', 0);
        insert into grupo_armado (nome, nr_baixas)
        values ('Tropa de Elite', 0);
        insert into grupo_armado (nome, nr_baixas)
        values ('Máfia Italiana', 0);
        insert into grupo_armado (nome, nr_baixas)
        values ('Máfia Chinesa', 0);
        insert into grupo_armado (nome, nr_baixas)
        values ('Máfia Japonesa', 0);

        insert into tipo_arma
        values ('AK47', 10);
        insert into tipo_arma
        values ('M16', 15);
        insert into tipo_arma
        values ('Desert Eagle', 5);
        insert into tipo_arma
        values ('Barret M82', 12);
        insert into tipo_arma
        values ('M200 intervention', 50);

        insert into fornecimento_armas
        values (1, 'AK47', 'Nicolas Cage');
        insert into fornecimento_armas
        values (2, 'AK47', 'Nicolas Cage');
        insert into fornecimento_armas
        values (3, 'AK47', 'Nicolas Cage');
        insert into fornecimento_armas
        values (1, 'Barret M82', 'Nicolas Cage');
        insert into fornecimento_armas
        values (1, 'Barret M82', 'Pablo Escobar');
        insert into fornecimento_armas
        values (2, 'Barret M82', 'Pablo Escobar');
        insert into fornecimento_armas
        values (3, 'M200 intervention', 'Pablo Escobar');
        insert into fornecimento_armas
        values (4, 'M200 intervention', 'Pablo Escobar');
        insert into fornecimento_armas
        values (4, 'Barret M82', 'Pablo Escobar');
        insert into fornecimento_armas
        values (5, 'M200 intervention', 'Nicolas Cage');
        insert into fornecimento_armas
        values (5, 'Desert Eagle', 'Nicolas Cage');
        insert into fornecimento_armas
        values (5, 'AK47', 'El Chapo');
        insert into fornecimento_armas
        values (5, 'AK47', 'Pablo Escobar');
        insert into fornecimento_armas
        values (6, 'Desert Eagle', 'Nicolas Cage');

        -- Mediacoes

        insert into organizacao (nome, tipo)
        values ('ONU', 'INTERNACIONAL');
        insert into organizacao (nome, tipo)
        values ('UNICEF', 'INTERNACIONAL');
        insert into organizacao (nome, tipo)
        values ('UNESCO', 'INTERNACIONAL');
        insert into organizacao (nome, tipo)
        values ('Governo Brasileiro', 'GOVERNAMENTAL');
        insert into organizacao (nome, tipo)
        values ('OTAN', 'INTERNACIONAL');
        insert into organizacao (nome, tipo)
        values ('Rotary Club', 'NÃO GOVERNAMENTAL');

        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (1, 1, 1000000, 'MÉDICA', '2010-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (2, 1, 1000000, 'MÉDICA', '2010-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (3, 1, 1000000, 'MÉDICA', '2010-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (4, 1, 1000000, 'MÉDICA', '2010-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (5, 1, 1000000, 'MÉDICA', '2010-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (1, 2, 3346, 'DIPLOMÁTICA', '2014-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (2, 3, 3346, 'DIPLOMÁTICA', '2014-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (3, 3, 3346, 'DIPLOMÁTICA', '2014-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (1, 4, 3346, 'DIPLOMÁTICA', '2014-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (2, 4, 3346, 'DIPLOMÁTICA', '2014-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (3, 4, 3346, 'DIPLOMÁTICA', '2014-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (4, 5, 6666, 'PRESENCIAL', '2009-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (4, 6, 6666, 'PRESENCIAL', '2009-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (1, 6, 6666, 'PRESENCIAL', '2009-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (2, 6, 6666, 'PRESENCIAL', '2009-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (3, 6, 6666, 'PRESENCIAL', '2009-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (6, 6, 6666, 'PRESENCIAL', '2009-01-01');
        insert into organizacoes_envolvidas (codigo_conflito, codigo_organizacao, nr_pessoas_mantidas, tipo_ajuda,
                                             data_incorporacao)
        values (10, 6, 6666, 'PRESENCIAL', '2009-01-01');
        """);
  }

}
