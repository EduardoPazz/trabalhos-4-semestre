package org.example.services;

import lombok.AllArgsConstructor;
import org.example.repository.WarConflictSelectionRepository;
import org.example.services.graphic.BarChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Component;

import javax.swing.WindowConstants;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class ChartsService {

  private static final String[] columns = {"Religioso", "Territorial",
      "Economico", "Racial"};
  private static final String title = "Números de Conflitpos X Tipo de Conflito";
  private static final String categoryLabel = "Tipo de Conflito";
  private static final String valueLabel = "Número de Conflitos";

  private final WarConflictSelectionRepository repository;

  public void generateConflictTypeVsNumberOfConflictsChart() {
    String[][] values = repository.selectConflictTypeVsNumberOfConflicts(
        columns);
    double[] data = transformSelectToDoubleArray(values);
    DefaultCategoryDataset dataset = createDataSet(data);
    BarChart barChart = new BarChart(title, categoryLabel, valueLabel, dataset);
    barChart.setSize(800, 400);
    barChart.setLocationRelativeTo(null);
    barChart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    barChart.setVisible(true);
  }

  private DefaultCategoryDataset createDataSet(double[] data) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (int i = 0; i < data.length; i++) {
      dataset.addValue(data[i], columns[i], "");
    }
    return dataset;
  }

  private double[] transformSelectToDoubleArray(String[][] result) {
    return Arrays.stream(result).flatMap(Arrays::stream)
        .mapToDouble(Double::valueOf).toArray();
  }
}
