package com.example.annotation.util.scheduler;

import java.io.IOException;
import java.io.InputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 折线图 
 * 2017年11月10日 下午8:30:53
 */
public class LineChart extends JFChart {

	/**
	 * 2017年11月10日 下午8:51:32
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public InputStream createChart(DefaultCategoryDataset chartData,int height,int width) throws IOException {

		JFreeChart chart = ChartFactory.createLineChart(
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
		
		CategoryPlot plot = chart.getCategoryPlot();
		CategoryAxis domainAxis = plot.getDomainAxis();

		// domainAxis.setMaximumCategoryLabelWidthRatio(30);
		domainAxis.setLowerMargin(-0.05);// 左边距 边框距离
		domainAxis.setLowerMargin(-0.073);// 左边距 边框距离

		return convert(chart.createBufferedImage(width, height));

	}

}
