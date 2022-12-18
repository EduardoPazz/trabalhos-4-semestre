package org.example.services;

import org.jfree.chart.ChartUtils;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import org.example.repository.WarConflictRepository;
import java.beans.beancontext.BeanContextChild;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;

@Component
@AllArgsConstructor
public class ChartsService {
  private final String[]racial = {"racial"};
  private final String[]economico = {"economico"};
  private final String[]territorial = {"territorial"};
  private final String[]religioso = {"religioso"};
  private final WarConflictRepository repository;

  public void generateConflictTypeVsNumberOfConflictsChart()
      throws IOException {
//    System.out.println("NAO IMPLEMENTADO: Conflict type vs number of conflicts chart");
    String[][] racialReport = getReport(repository::selectCountRacialConflicts, racial);
    String[][] economicReport = getReport(repository::selectCountEconomicConflicts, economico);
    String[][] territorialReport = getReport(repository::selectCountTerritorialConflicts, territorial);
    String[][] religiousReport = getReport(repository::selectCountReligiousConflicts, religioso);

    double[] racialData = TransformSelectToDoubleArray(racialReport);
    double[] economicData = TransformSelectToDoubleArray(economicReport);
    double[] territorialData = TransformSelectToDoubleArray(territorialReport);
    double[] religiousData = TransformSelectToDoubleArray(religiousReport);

    createChart(new double[][]{racialData, economicData, territorialData, religiousData} );

  }

  private String[][] getReport(Function<String[], String[][]> selector, String[] columns) {
    return selector.apply(columns);
  }

  private double[] TransformSelectToDoubleArray(String[][] result){
    return Arrays.stream(result).flatMap(Arrays::stream).mapToDouble(Double::valueOf).toArray();
  }

  private static void createChart(double[][] data) throws IOException {
    HistogramDataset dataset = new HistogramDataset();
    dataset.addSeries("Racial", data[0], 1);
    dataset.addSeries("Economico", data[1], 1);
    dataset.addSeries("Territorial", data[2], 1);
    dataset.addSeries("Religioso", data[3], 1);

    JFreeChart histogram = ChartFactory.createHistogram("Tipo de conflito e Número de conflitos",
        "y values", "x values", dataset);

    ChartUtils.saveChartAsPNG(new File("./histograma.png"), histogram, 600, 400);
  }


}
