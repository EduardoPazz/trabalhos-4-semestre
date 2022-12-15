package org.example.views;

import java.util.List;
import java.util.Scanner;

class ViewHelpers {

  static void quit() {
    System.out.println("Saindo do programa...");
    System.exit(0);
  }

  static String getValidInputWithOptions(final Scanner scanner, final String message,
      final List<String> validOptions) {

    System.out.println(message);

    String input = scanner.nextLine();

    while (input.trim().isEmpty() || !validOptions.contains(input)) {
      System.out.println("Por favor, digite uma opção válida: " + validOptions);
      input = scanner.nextLine();
    }

    return input;
  }
}
