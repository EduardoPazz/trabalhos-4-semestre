package org.example.view;

import java.util.List;
import java.util.Scanner;

public class View {

  public static void mainMenu() {
    try (var scanner = new Scanner(System.in)) {
      String chosenOption = getValidInputWithOptions(scanner, """
          *** Conflitos Belicos ***
          [1] - Fazer novos cadastros
          [2] - Gerar relatorios e graficos
          [Q] - Sair do programa
          """, List.of("1", "2", "Q"));

      switch (chosenOption) {
        case "1" -> System.out.println("Fazer novos cadastros");
        case "2" -> System.out.println("Gerar relatorios e graficos");
        case "Q" -> quit();
      }
    }
  }

  private static void quit() {
    System.out.println("Saindo do programa...");
    System.exit(0);
  }

  private static String getValidInputWithOptions(final Scanner scanner,
      final String message, final List<String> validOptions) {

    System.out.println(message);

    String input = scanner.nextLine();

    while (input.trim().isEmpty() || !validOptions.contains(input)) {
      System.out.println("Por favor, digite uma opção válida:" + validOptions);
      input = scanner.nextLine();
    }

    return input;
  }
}
