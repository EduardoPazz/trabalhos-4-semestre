package org.example.views;

import lombok.AllArgsConstructor;
import org.example.services.ChartsService;
import org.example.services.ReportsService;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.example.views.ViewHelpers.getValidInputWithOptions;

@Component
@AllArgsConstructor
class ReportsMenu {
  private final Scanner scanner;
  private final ChartsService chartsService;
  private final ReportsService reportsService;

  void run(){
    reportsMenuWhile:
    while (true) {
      String chosenOption = getValidInputWithOptions(scanner, """
          \n*** Conflitos Belicos - Graficos e Relatorios ***
          [1] - Grafico: Tipo de Conflito x Numero de Conflitos
          [2] - Relatorio: Traficantes e Grupos Armados cuja armas fornecidas sao "Barret M82" ou "M200 Intervention"
          [3] - Relatorio: 5 maiores conflitos em numero de mortos
          [4] - Relatorio: 5 maiores organizacoes em numero de mediacoes
          [5] - Relatorio: 5 maiores grupos armados com maior numero de armas fornecidos
          [6] - Relatorio: Pais com maior numero de conflitos religiosos
          [Q] - Voltar ao menu anterior
          """, List.of("1", "2", "3", "4", "5", "6", "Q"));

      switch (chosenOption) {
        case "1" -> chartsService.generateConflictTypeVsNumberOfConflictsChart();
        case "2" -> reportsService.printDealersAndArmedGroupsReport();
        case "3" -> reportsService.printTop5DeadliestConflictsReport();
        case "4" -> reportsService.printTop5MediatorsReport();
        case "5" -> reportsService.printTop5ArmedGroupsReport();
        case "6" -> reportsService.printReligiousConflictsReport();
        case "Q" -> {
          break reportsMenuWhile;
        }
      }
    }
  }


}
