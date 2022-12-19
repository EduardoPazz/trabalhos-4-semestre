package org.example.services;

import lombok.AllArgsConstructor;
import org.example.repository.WarConflictSelectionRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChartsService {

  private final WarConflictSelectionRepository repository;

  public void generateConflictTypeVsNumberOfConflictsChart() {

    String[][] values = repository.selectConflictTypeVsNumberOfConflicts(
        new String[]{"Religioso", "Territorial", "Economico", "Racial"});

    int religiousConflictsQuantity = Integer.parseInt(values[0][0]);
    int territorialConflictsQuantity = Integer.parseInt(values[0][1]);
    int economicConflictsQuantity = Integer.parseInt(values[0][2]);
    int racialConflictsQuantity = Integer.parseInt(values[0][3]);

    double max = Math.max(religiousConflictsQuantity,
        Math.max(territorialConflictsQuantity,
            Math.max(economicConflictsQuantity, racialConflictsQuantity)));

    int MAX_LENGTH = 40;
    int normalizedReligiousConflictQuantity = (int) (
        MAX_LENGTH * religiousConflictsQuantity / max);
    int normalizedTerritorialConflictsQuantity = (int) (
        MAX_LENGTH * territorialConflictsQuantity / max);
    int normalizedEconomicConflictsQuantity = (int) (
        MAX_LENGTH * economicConflictsQuantity / max);
    int normalizedRacialConflictsQuantity = (int) (
        MAX_LENGTH * racialConflictsQuantity / max);

    System.out.format("""

            | %13s | %-45s |
            | %s |
            | %13s | %-45s |
            | %13s | %-45s |
            | %13s | %-45s |
            | %13s | %-45s |

            """, "Tipo Conflito", "Numero de Conflitos", "-".repeat(61),
        "Religioso", "*".repeat(normalizedReligiousConflictQuantity) + " ("
            + religiousConflictsQuantity + ")", "Territorial",
        "*".repeat(normalizedTerritorialConflictsQuantity) + " ("
            + territorialConflictsQuantity + ")", "Economico",
        "*".repeat(normalizedEconomicConflictsQuantity) + " ("
            + economicConflictsQuantity + ")", "Racial",
        "*".repeat(normalizedRacialConflictsQuantity) + " ("
            + racialConflictsQuantity + ")");
  }
}
