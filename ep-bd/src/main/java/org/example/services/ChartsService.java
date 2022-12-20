package org.example.services;

import org.example.services.graphic.BarChart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import org.example.repository.WarConflictRepository;
import java.util.Arrays;
import java.util.function.Function;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import javax.swing.*;

@Component
@AllArgsConstructor
public class ChartsService{

  private static final String[] columns = {"Religioso", "Territorial", "Economico", "Racial"};
  private static final String fileName= "./histograma.png";
  private static final String title = "Números de Conflitpos X Tipo de Conflito";
  private static final String categoryLabel = "Tipo de Conflito";
  private static final String valueLabel= "Número de Conflitos";

  private final WarConflictRepository repository;
  public void generateConflictTypeVsNumberOfConflictsChart(){
    String[][] values = repository.selectConflictTypeVsNumberOfConflicts(columns);
    double[] data = transformSelectToDoubleArray(values);
    DefaultCategoryDataset dataset = createDataSet(data);
    BarChart barChart = new BarChart(title, categoryLabel, valueLabel, dataset);
    barChart.setSize(800, 400);
    barChart.setLocationRelativeTo(null);
    barChart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    barChart.setVisible(true);

  }

  private String[][] getReport(Function<String[], String[][]> selector, String[] columns) {
    return selector.apply(columns);
  }

  private double[] transformSelectToDoubleArray(String[][] result){
    return Arrays.stream(result).flatMap(Arrays::stream).mapToDouble(Double::valueOf).toArray();
  }

  private static DefaultCategoryDataset createDataSet(double[] data) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (int i = 0; i < data.length; i++) {
      dataset.addValue(data[i], columns[i], "");
    }
    return dataset;
  }

//    try{
//      ChartUtils.saveChartAsPNG(new File(fileName), barChart, 900, 600);
//      System.out.println("\nHistograma gerado na pasta raiz do programa: " + fileName);
//
//    }
//    catch (IOException e){
//      System.out.println(e);
//    }
//  }


}
