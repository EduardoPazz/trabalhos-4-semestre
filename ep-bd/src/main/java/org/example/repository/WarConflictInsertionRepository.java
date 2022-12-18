package org.example.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class WarConflictInsertionRepository {

  private final Connection connection;

  public void insertArmedGroupDivision(Object[] values) throws Exception {
    insert(
        "insert into divisao (nr_baixas, nr_soldados, nr_avioes, nr_barcos, nr_tanques, codigo_grupo_armado) VALUES (?,?,?,?,?,?);",
        values);
  }

  public void insertWarConflict(Object... values) throws Exception {
    insert(
        "insert into conflito (nome, nr_mortos, nr_feridos, flag_racial, flag_territorial, flag_religioso, flag_economico) VALUES (?,?,?,?,?,?,?);",
        values);
  }

  public void insertPoliticalLeader(Object... values) throws Exception {
    insert(
        "insert into lider_politico (nome, descricao_apoio, codigo_grupo_armado) VALUES (?,?,?);",
        values);

  }

  public void insertArmedGroup(Object[] values) throws Exception {
    insert("insert into grupo_armado (nome) VALUES (?);", values);
  }

  private void insert(String query, Object[] values) throws Exception {
    PreparedStatement preparedStatement = connection.prepareStatement(query);

    try (preparedStatement) {
      IntStream.rangeClosed(1, values.length).forEach(i -> {
        try {
          preparedStatement.setObject(i, values[i - 1]);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      });

      preparedStatement.executeUpdate();
    }
  }
}
