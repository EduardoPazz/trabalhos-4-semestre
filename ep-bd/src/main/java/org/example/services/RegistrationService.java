package org.example.services;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import org.example.repository.WarConflictInsertionRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationService {

  private final WarConflictInsertionRepository repository;

  public boolean registerArmedGroupDivision(String... values) {
    try {
      Object[] parsedValues = Arrays.stream(values)
          .map(value -> value.trim().isEmpty() ? null : Integer.valueOf(value))
          .toArray();

      repository.insertArmedGroupDivision(parsedValues);

      System.out.println("\nCadastro realizado com sucesso!");

      return true;
    } catch (Exception e) {
      System.out.println("\nErro no cadastro. Banco de dados retornou a seguinte mensagem de erro:\n" + e.getMessage());
      return false;
    }
  }
}
