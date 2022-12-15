package org.example.view;

import java.util.List;
import java.util.Scanner;

public class View {

  private final Scanner scanner = new Scanner(System.in);

  public void mainMenu() {
    while (true) {
      String chosenOption = getValidInputWithOptions("""
          \n*** Conflitos Belicos ***
          [1] - Fazer novos cadastros
          [2] - Gerar relatorios e graficos
          [Q] - Sair do programa
          """, List.of("1", "2", "Q"));

      switch (chosenOption) {
        case "1" -> registrationMenu();
        case "2" -> reportsMenu();
        case "Q" -> quit();
      }

    }
  }

  private void reportsMenu() {
    reportsMenuWhile:
    while (true) {
      String chosenOption = getValidInputWithOptions("""
          \n*** Conflitos Belicos - Graficos e Relatorios ***
          [1] - Grafico: Tipo de Conflito x Numero de Conflitos
          [2] - Relatorio: Traficantes e Grupos Armados cuja armas fornecidas sao "Barret M82" ou "Intervention"
          [3] - Relatorio: 5 maiores conflitos em numero de mortos
          [4] - Relatorio: 5 maiores organizacoes em numero de mediacoes
          [5] - Relatorio: 5 maiores grupos armados com maior numero de armas fornecidos
          [6] - Pais com maior numero de conflitos religiosos
          [Q] - Voltar ao menu anterior
          """, List.of("1", "2", "3", "4", "5", "6", "Q"));

      switch (chosenOption) {
        case "1" -> {
          System.out.println("Grafico: Tipo de Conflito x Numero de Conflitos");
          System.out.println("Ainda nao implementado");
        }
        case "2" -> {
          System.out.println(
              "Relatorio: Traficantes e Grupos Armados cuja armas fornecidas sao \"Barret M82\" ou \"Intervention\"");
          System.out.println("Ainda nao implementado");
        }
        case "3" -> {
          System.out.println(
              "Relatorio: 5 maiores conflitos em numero de mortos");
          System.out.println("Ainda nao implementado");
        }
        case "4" -> {
          System.out.println(
              "Relatorio: 5 maiores organizacoes em numero de mediacoes");
          System.out.println("Ainda nao implementado");
        }
        case "5" -> {
          System.out.println(
              "Relatorio: 5 maiores grupos armados com maior numero de armas fornecidos");
          System.out.println("Ainda nao implementado");
        }
        case "6" -> {
          System.out.println("Pais com maior numero de conflitos religiosos");
          System.out.println("Ainda nao implementado");
        }
        case "Q" -> {
          break reportsMenuWhile;
        }
      }
    }
  }

  private void registrationMenu() {
    System.out.println("Ainda nao implementado");
  }

  private static void quit() {
    System.out.println("Saindo do programa...");
    System.exit(0);
  }

  private String getValidInputWithOptions(final String message,
      final List<String> validOptions) {

    System.out.println(message);

    String input = scanner.nextLine();

    while (input.trim().isEmpty() || !validOptions.contains(input)) {
      System.out.println("Por favor, digite uma opção válida:" + validOptions);
      input = scanner.nextLine();
    }

    return input;
  }
}
