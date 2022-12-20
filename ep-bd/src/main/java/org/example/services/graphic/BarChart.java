package org.example.services.graphic;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;

public class BarChart extends JFrame {

  public BarChart(String title, String categoryLabel, String valueLabel,
      DefaultCategoryDataset dataset) {
  super(title);

  JFreeChart barChart = ChartFactory.createBarChart(title, categoryLabel,
      valueLabel, dataset, PlotOrientation.VERTICAL, true, true, false);

  ChartPanel panel = new ChartPanel(barChart);

  setContentPane(panel);
  }
}
