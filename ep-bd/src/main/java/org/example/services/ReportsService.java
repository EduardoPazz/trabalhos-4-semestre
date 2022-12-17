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
    String[] columns = {"grupo_armado.nome", "nome_traficante"};

    printReport(repository::selectDealersAndArmedGroups, columns);
  }

  public void printTop5DeadliestConflictsReport() {
    String[] columns = {"nome", "nr_mortos"};

    printReport(repository::selectTop5DeadliestConflicts, columns);
  }

  public void printTop5MediatorsReport() {
    System.out.println("NAO IMPLEMENTADO");
  }

  public void printTop5ArmedGroupsReport() {
    System.out.println("NAO IMPLEMENTADO");
  }

  public void printReligiousConflictsReport() {
    System.out.println("NAO IMPLEMENTADO");
  }

  private void printReport(Function<String[], String[][]> selector, String[] columns) {
    String[][] result = selector.apply(columns);

    new TextTable(columns, result).printTable();
  }
}
