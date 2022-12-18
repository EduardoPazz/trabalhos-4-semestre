package org.example.views;

import static org.example.views.ViewHelpers.getInput;
import static org.example.views.ViewHelpers.getValidInputWithOptions;

import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import org.example.services.RegistrationService;
import org.springframework.stereotype.Component;

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
  }

  private void register(Supplier<Boolean> registrationFunction) {
    while (true) {
      System.out.println(
          "\nPreencha os dados a seguir. Digite apenas enter para deixar algum campo em branco.\n");

      boolean success = registrationFunction.get();

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