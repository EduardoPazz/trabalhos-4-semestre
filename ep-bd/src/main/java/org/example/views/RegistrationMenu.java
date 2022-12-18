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
  private final RegistrationForm registrationForm;

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
        case "1" -> registrationForm.registerArmedGroupDivision();
        case "2" -> registrationForm.registerWarConflict();
        case "3" -> registrationForm.registerArmedGroup();
        case "4" -> registrationForm.registerPoliticalLeader();
        case "5" -> registrationForm.registerMilitaryLeader();
        case "Q" -> {
          break registrationMenuWhile;
        }
      }
    }
  }
}
