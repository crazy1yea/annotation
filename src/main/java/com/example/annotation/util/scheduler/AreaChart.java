package com.example.annotation.util.scheduler;

import java.io.IOException;
import java.io.InputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 折线图
 * 2017年11月10日 下午8:30:53
 */
public class AreaChart extends JFChart{

	@Override
	public InputStream createChart(DefaultCategoryDataset chartData,int height,int width) throws IOException {
		JFreeChart chart = ChartFactory.createAreaChart(
				"",//图表标题 
				"",//目录轴的显示标签
				"",//数值轴的显示标签
				chartData,//数据集
				PlotOrientation.VERTICAL,//图表方向
				true,//是否显示图例，对于简单的柱状图必须为false
				true,//是否生成提示工具 
				false //是否生成url链接 
			);
		commonSet(chart);
		
		/****** 图表区样式 ******/
		{
			// 获得图表对象
			CategoryPlot plot = chart.getCategoryPlot();

			// 前景透明度(面积区域)
			plot.setForegroundAlpha(0.3f);

			// 隐藏边框
			plot.setOutlineVisible(false);

			{
				// 设置X轴
				CategoryAxis domainAxis = plot.getDomainAxis();

				// domainAxis.setMaximumCategoryLabelWidthRatio(30);
				domainAxis.setLowerMargin(-0.05);// 左边距 边框距离
				domainAxis.setLowerMargin(-0.073);// 左边距 边框距离
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

			// rangeAxis.setAutoRangeMinimumSize(200D);
			// rangeAxis.setAutoTickUnitSelection(false);
			// rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			// rangeAxis.setLowerBound(5);
			// rangeAxis.setUpperBound(35);

			// 设置Y轴
			// NumberAxis rangeAxisY = (NumberAxis) plot.getRangeAxis();
			// rangeAxisY.setStandardTickUnits(NumberAxis.createIntegerTickUnits());//Y轴显示整数
			// rangeAxisY.setAutoRangeMinimumSize(1); //最小跨度
			// rangeAxisY.setUpperMargin(0.18);//上边距,防止最大的一个数据靠近了坐标轴。
			// if (ObjectUtil.isNotEmpty(xAxis)){
			// rangeAxisY.setLowerBound(xAxis.get(0)); //最小值显示0
			// rangeAxisY.setUpperBound(xAxis.get(xAxis.size() - 1)); //最小值显示0
			// }

			// rangeAxisY.setAutoRange(false); //不自动分配Y轴数据
			// rangeAxisY.setTickMarkStroke(new BasicStroke(1.6f)); // 设置坐标标记大小

		}
		/*
		String filePath = "src/main/resources/template/area_chart.jpg";
		File areaChart = new File(filePath);
		ChartUtils.saveChartAsJPEG(areaChart, chart, chartData.getWidth(), chartData.getHeight());
		return new FileInputStream(areaChart);
		*/
		return convert(chart.createBufferedImage(width, height));
	}

}
