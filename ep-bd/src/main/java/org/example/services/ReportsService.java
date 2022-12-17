package org.example.services;

import dnl.utils.text.table.TextTable;
import lombok.AllArgsConstructor;
import org.example.repository.WarConflictRepository;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class ReportsService {

  private final WarConflictRepository repository;

  public void printDealersAndArmedGroupsReport() {
    String[] columns = {"Nome Traficante", "Nome Grupo Armado", "Nome Arma"};

    printReport(repository::selectDealersAndArmedGroups, columns);
  }

  public void printTop5DeadliestConflictsReport() {
    String[] columns = {"Nome Conflito", "Número de Mortos"};

    printReport(repository::selectTop5DeadliestConflicts, columns);
  }

  public void printTop5MediatorsReport() {
    System.out.println("NAO IMPLEMENTADO");
  }

  public void printTop5ArmedGroupsReport() {
    String[] columns = {"Nome Grupo Armado", "Número de Armas"};

    printReport(repository::selectTop5ArmedGroups, columns);
  }

  public void printReligiousConflictsReport() {
    String[] columns = {"País", "Número de Conflitos"};

    printReport(repository::selectReligiousConflicts, columns);
  }

  private void printReport(Function<String[], String[][]> selector, String[] columns) {
    String[][] result = selector.apply(columns);

    new TextTable(columns, result).printTable();
  }
}
