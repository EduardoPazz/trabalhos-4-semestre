package org.example.services;

import lombok.AllArgsConstructor;
import org.example.repository.WarConflictInsertionRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static org.example.services.Parser.parseBoolean;
import static org.example.services.Parser.parseInteger;
import static org.example.services.Parser.parseString;

@Component
@AllArgsConstructor
public class RegistrationService {

  private final WarConflictInsertionRepository repository;

  public boolean registerArmedGroupDivision(String... values) {
    Object[] parsedValues = Arrays.stream(values).map(Parser::parseInteger)
        .toArray();

    boolean success = register(repository::insertArmedGroupDivision,
        parsedValues);

    return success;
  }

  public boolean registerWarConflict(String nome, String nr_mortos,
      String nr_feridos, String flag_racial, String flag_territorial,
      String flag_religioso, String flag_economico) {

    String parsedName = parseString(nome);
    Integer parsedNrMortos = parseInteger(nr_mortos);
    Integer parsedNrFeridos = parseInteger(nr_feridos);
    Boolean parsedFlagRacial = parseBoolean(flag_racial);
    Boolean parsedFlagTerritorial = parseBoolean(flag_territorial);
    Boolean parsedFlagReligioso = parseBoolean(flag_religioso);
    Boolean parsedFlagEconomico = parseBoolean(flag_economico);

    boolean success = register(repository::insertWarConflict, parsedName,
        parsedNrMortos, parsedNrFeridos, parsedFlagRacial,
        parsedFlagTerritorial, parsedFlagReligioso, parsedFlagEconomico);

    return success;
  }

  public boolean registerPoliticalLeader(String nome, String descricao_apoio,
      String codigo_grupo_armado) {
    String parsedName = parseString(nome);
    String parsedDescricaoApoio = parseString(descricao_apoio);
    Integer parsedCodigoGrupoArmado = parseInteger(codigo_grupo_armado);

    boolean success = register(repository::insertPoliticalLeader, parsedName,
        parsedDescricaoApoio, parsedCodigoGrupoArmado);

    return success;
  }

  public boolean registerArmedGroup(String nome) {
    String parsedName = parseString(nome);

    boolean success = register(repository::insertArmedGroup, parsedName);

    return success;
  }

  private boolean register(RegistrationFunction registrationFunction,
      Object... values) {
    try {

      registrationFunction.insert(values);

      System.out.println("\nCadastro realizado com sucesso!");

      return true;
    } catch (Exception e) {
      System.out.println(
          "\nErro no cadastro. Banco de dados retornou a seguinte mensagem de erro:\n"
              + e.getMessage());
      return false;
    }
  }

  private interface RegistrationFunction {

    void insert(Object... values) throws Exception;
  }

}
