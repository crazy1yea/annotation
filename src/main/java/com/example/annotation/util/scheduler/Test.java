package com.example.annotation.util.scheduler;

import java.awt.BasicStroke;
import java.awt.Font;

import javax.swing.JFrame;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.VerticalAlignment;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYSeries;

public class Test extends JFrame {

	private static final long serialVersionUID = -4546745976689029806L;
	public JFreeChart chart;
	public Test() {
		TableXYDataset dataset = createDataset();
		chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);
	}

	private JFreeChart createChart(TableXYDataset dataset) {

		JFreeChart chart = ChartFactory.createStackedXYAreaChart(
				"", // chart  title
				"", // domain axis label
				"", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // the plot orientation
				true, // legend
				true, // tooltips
				false // urls
		);
		// 整体画布背景色
		chart.setBackgroundPaint(ChartColor.WHITE);
		chart.setBorderVisible(false); // 边框

		/****** 图例样式 *****/
		{
			Font LegendFont = new Font("微软雅黑", Font.PLAIN, 12);
			// 图例字体
			LegendTitle legend = chart.getLegend(0);
			legend.setItemFont(LegendFont);
			// 图例位置
			legend.setVerticalAlignment(VerticalAlignment.TOP);
			legend.setPosition(RectangleEdge.TOP);
			legend.setBorder(0, 0, 0, 0); // 边框
		}
		// 去除图例
		chart.removeLegend();
		/****** 图表区样式 ******/
		{
			// 获得图表对象
			XYPlot plot = chart.getXYPlot();

			// 图表区域背景色
			plot.setBackgroundPaint(ChartColor.WHITE);
			// 设置表格线颜色及样式 - 横线
			plot.setRangeGridlinePaint(ChartColor.GRAY);
			plot.setRangeGridlineStroke(new BasicStroke(BasicStroke.CAP_BUTT));
			// 设置表格线颜色及样式 - 纵线
			plot.setDomainGridlinePaint(ChartColor.GRAY);
			plot.setDomainGridlineStroke(new BasicStroke(BasicStroke.JOIN_MITER));
			plot.setDomainGridlinesVisible(true);

			// 前景透明度(面积区域)
			plot.setForegroundAlpha(0.3f);

			// 隐藏边框
			plot.setOutlineVisible(false);

			{
				// 设置X轴
				// ValueAxis domainAxis = plot.getDomainAxis();
				// domainAxis.setLowerMargin(-0.05);// 左边距 边框距离
				// domainAxis.setLowerMargin(-0.073);// 左边距 边框距离
				// domainAxis.setUpperMargin(-0.04);// 右边距
				// 边框距离,防止最后边的一个数据靠近了坐标轴。
				// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);//
				// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45

				plot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 0D));
			}

			ValueAxis rangeAxis = plot.getRangeAxis();
			rangeAxis.setRange(0.0, 430);
			rangeAxis.setAutoRange(true);
			rangeAxis.setUpperMargin(0.18);
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());// Y轴显示整数
			rangeAxis.setAutoRangeMinimumSize(1);
		}
		return chart;
	}

	private TableXYDataset createDataset() {

		DefaultTableXYDataset dataset = new DefaultTableXYDataset();

		XYSeries s1 = new XYSeries("", true, false);
		s1.add(5.0, 1000);
		s1.add(10.0, 30);
		s1.add(15.0, 50);
		s1.add(20.0, 1000);
		dataset.addSeries(s1);

		return dataset;
	}

	public static void main(String[] args) {
		Test demo = new Test();
		demo.pack();
		demo.setVisible(true);
	}
}