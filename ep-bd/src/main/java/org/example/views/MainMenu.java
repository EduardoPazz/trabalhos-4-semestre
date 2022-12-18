package org.example.views;

import static org.example.views.ViewHelpers.getValidInputWithOptions;
import static org.example.views.ViewHelpers.quit;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MainMenu {

  private final Scanner scanner;
  private final ReportsMenu reportsMenu;
  private final RegistrationMenu registrationMenu;

  public void run() throws IOException {
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
}
