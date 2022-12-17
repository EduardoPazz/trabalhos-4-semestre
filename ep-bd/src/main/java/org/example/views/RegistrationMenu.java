package org.example.views;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

import static org.example.views.ViewHelpers.getValidInputWithOptions;

@Component
@AllArgsConstructor
class RegistrationMenu {

  private final Scanner scanner;
  private final Registration registration;

  void run() {
    registrationMenuWhile:
    while (true) {
      String chosenOption = getValidInputWithOptions(scanner, """
          \n*** Conflitos Belicos - Cadastros ***
          [1] - Cadastrar Divisoes dentro de um Grupo Militar
          [2] - Cadastrar Conflito Belico
          [3] - Cadastrar Grupo Militar
          [4] - Cadastrar Lider Politico
          [5] - Cadastrar Chefe Militar
          [Q] - Voltar ao menu anterior
          """, List.of("1", "2", "3", "4", "5", "Q"));

      switch (chosenOption) {
        case "1" -> registration.registerArmedGroupDivision();
        case "2" -> System.out.println("Not implemented yet");
        case "3" -> System.out.println("Not implemented yet");
        case "4" -> System.out.println("Not implemented yet");
        case "5" -> System.out.println("Not implemented yet");
        case "Q" -> {
          break registrationMenuWhile;
        }
      }
    }
  }
}
