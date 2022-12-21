package org.example.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class WarConflictSelectionRepository {

  private final Connection connection;

  public String[][] selectConflictTypeVsNumberOfConflicts(String[] columns) {
    return select("""
        select *
        from (select count(*) as "Religioso" from conflito where flag_religioso = true) as religioso,
             (select count(*) as "Racial" from conflito where flag_racial = true) as racial,
             (select count(*) as "Territorial" from conflito where flag_territorial = true) as territorial,
             (select count(*) as "Economico" from conflito where flag_economico = true) as economico;
        """, columns);
  }

  public String[][] selectDealersAndArmedGroups(String[] columns) {
    return select("""
        select nome_traficante as "Nome Traficante", grupo_armado.nome as "Nome Grupo Armado", nome_tipo_arma as "Nome Arma"
        from fornecimento_armas
        join grupo_armado
        on fornecimento_armas.codigo_grupo_armado = grupo_armado.codigo
        where nome_tipo_arma = 'Barret M82' or nome_tipo_arma = 'M200 intervention'
        order by nome_tipo_arma, nome_traficante, grupo_armado.nome
        """, columns);
  }

  public String[][] selectTop5DeadliestConflicts(String[] columns) {
    return select("""
        select nome as "Nome Conflito", nr_mortos as "Número de Mortos"
        from conflito
        order by nr_mortos desc
        limit 5;
        """, columns);
  }

  public String[][] selectTop5ArmedGroups(String[] columns) {
    return select("""
        select nome as "Nome Grupo Armado", count(nome_tipo_arma) as "Número de Armas" from fornecimento_armas
        join grupo_armado on fornecimento_armas.codigo_grupo_armado = grupo_armado.codigo
        group by nome
        order by "Número de Armas" desc
        limit 5;
        """, columns);
  }

  public String[][] selectTop5Mediators(String[] columns) {
    return select("""
        select o.nome as "Nome Organização", count(codigo_conflito) as "Número de conflitos"
        from organizacoes_envolvidas oe
        join organizacao o on o.codigo = oe.codigo_organizacao
        group by o.nome
        order by "Número de conflitos" desc
        limit 5;
        """, columns);
  }

  public String[][] selectReligiousConflicts(String[] columns) {
    return select("""
        select paises_afetados.nome as "País", count(codigo) as "Número de Conflitos"
        from paises_afetados
        join conflito
        on paises_afetados.codigo_conflito = conflito.codigo and conflito.flag_religioso = true
        group by "País"
        order by "Número de Conflitos" desc
        limit 1;
        """, columns);
  }

  private String[][] select(String query, String... columns) {
    List<List<String>> result = new ArrayList<>();

    try (Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(query);

      while (resultSet.next()) {
        List<String> row = Arrays.stream(columns).map(column -> {
          try {
            return resultSet.getString(column);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        }).toList();

        result.add(row);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return result.stream().map(row -> row.toArray(String[]::new))
        .toArray(String[][]::new);
  }
}
