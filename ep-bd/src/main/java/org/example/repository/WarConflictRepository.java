package org.example.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WarConflictRepository {

  private final Connection connection;

  public List<List<String>> selectDealersAndArmedGroups() {
    List<List<String>> result = new ArrayList<>();

    try (Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery("""
          select *, grupo_armado.nome
          from fornecimento_armas
          join grupo_armado
          on fornecimento_armas.codigo_grupo_armado = grupo_armado.codigo
          where nome_tipo_arma = 'Barret M82' or nome_tipo_arma = 'M200 intervention';
          """);

      while (resultSet.next()) {
        List<String> row = List.of(resultSet.getString("grupo_armado.nome"),
            resultSet.getString("nome_traficante"));
        result.add(row);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return result;
  }
}
