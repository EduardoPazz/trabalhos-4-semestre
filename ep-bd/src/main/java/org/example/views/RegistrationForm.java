package org.example.views;

import lombok.AllArgsConstructor;
import org.example.services.RegistrationService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.function.BooleanSupplier;

import static org.example.views.ViewHelpers.getInput;
import static org.example.views.ViewHelpers.getValidInputWithOptions;

@Component
@AllArgsConstructor
class RegistrationForm {

  private final Scanner scanner;
  private final RegistrationService service;

  void registerArmedGroupDivision() {
    register(() -> {
      String nr_baixas = getInput(scanner, "nr_baixas: ");
      String nr_soldados = getInput(scanner, "nr_soldados: ");
      String nr_avioes = getInput(scanner, "nr_avioes: ");
      String nr_barcos = getInput(scanner, "nr_barcos: ");
      String nr_tanques = getInput(scanner, "nr_tanques: ");
      String codigo_grupo_armado = getInput(scanner, "codigo_grupo_armado: ");

      boolean success = service.registerArmedGroupDivision(nr_baixas,
          nr_soldados, nr_avioes, nr_barcos, nr_tanques, codigo_grupo_armado);

      return success;
    });
  }

  public void registerWarConflict() {
    register(() -> {
      String nome = getInput(scanner, "nome: ");
      String nr_mortos = getInput(scanner, "nr_mortos: ");
      String nr_feridos = getInput(scanner, "nr_feridos: ");
      String flag_racial = getInput(scanner, "flag_racial: ");
      String flag_territorial = getInput(scanner, "flag_territorial: ");
      String flag_religioso = getInput(scanner, "flag_religioso: ");
      String flag_economico = getInput(scanner, "flag_economico: ");

      boolean success = service.registerWarConflict(nome, nr_mortos, nr_feridos,
          flag_racial, flag_territorial, flag_religioso, flag_economico);

      return success;
    });
  }

  public void registerArmedGroup() {
    register(() -> {
      String nome = getInput(scanner, "nome: ");

      boolean success = service.registerArmedGroup(nome);

      return success;
    });
  }

  public void registerPoliticalLeader() {
    register(() -> {
      String nome = getInput(scanner, "nome: ");
      String descricao_apoio = getInput(scanner, "descricao_apoio: ");
      String codigo_grupo_armado = getInput(scanner, "codigo_grupo_armado: ");

      boolean success = service.registerPoliticalLeader(nome, descricao_apoio,
          codigo_grupo_armado);

      return success;
    });
  }

  public void registerMilitaryLeader() {
    register(() -> {
      String faixa_hierarquica = getInput(scanner, "faixa_hierarquica: ");
      String nome_lider_politico = getInput(scanner, "nome_lider_politico: ");
      String codigo_divisao = getInput(scanner, "codigo_divisao: ");

      boolean success = service.registerMilitaryLeader(faixa_hierarquica,
          nome_lider_politico, codigo_divisao);

      return success;
    });
  }

  private void register(BooleanSupplier registrationFunction) {
    while (true) {
      System.out.println(
          "\nPreencha os dados a seguir. Digite apenas enter para deixar algum campo em branco.\n");

      boolean success = registrationFunction.getAsBoolean();

      String message =
          success ? "Deseja cadastrar novamente?" : "Deseja tentar novamente?";

      String chosenOption = getValidInputWithOptions(scanner,
          "\n" + message + " [S]im ou [N]ao", List.of("S", "N"));

      if (chosenOption.equals("N")) {
        break;
      }
    }
  }
}
