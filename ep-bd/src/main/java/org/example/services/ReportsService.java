package org.example.services;

import lombok.AllArgsConstructor;
import org.example.repository.WarConflictRepository;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ReportsService {

  private final WarConflictRepository repository;

  public void printDealersAndArmedGroupsReport() {
    System.out.println("SEMI IMPLEMENTADO");
    List<List<String>> lists = repository.selectDealersAndArmedGroups();
    System.out.println(lists);
  }

  public void printTop5DeadliestConflictsReport() {
    System.out.println("NAO IMPLEMENTADO");
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
}
