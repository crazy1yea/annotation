package com.example.annotation.util.scheduler;

import java.io.IOException;
import java.io.InputStream;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 柱状图 
 * 2017年11月10日 下午8:30:53
 */
public class BarChartX extends JFChart {

	/**
	 * 2017年11月10日 下午8:51:23
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@Override
	public InputStream createChart(DefaultCategoryDataset chartData,int height,int width) throws IOException {

		// 创建柱状图，正常显示，不显示图例
		JFreeChart chart = ChartFactory.createBarChart(
				"水果产量图",//图表标题 
				"水果",//目录轴的显示标签
				"产量",//数值轴的显示标签
				chartData,//数据集
				PlotOrientation.VERTICAL,//图表方向
				true,//是否显示图例，对于简单的柱状图必须为false
				false,//是否生成提示工具 
				false //是否生成url链接 
			);
		commonSet(chart);

		/****** 图表区样式 ******/
		{
			// 获得图表对象
			CategoryPlot plot = chart.getCategoryPlot();

			// 柱子距离
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setUpperMargin(0.08);
			domainAxis.setLowerMargin(0.08);

			// 隐藏数值坐标
			// plot.getRangeAxis().setVisible(false);

			// 显示数值
			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			// renderer.setBaseItemLabelsVisible(true);
			// renderer.setBaseItemLabelGenerator(new
			// StandardCategoryItemLabelGenerator());
			// renderer.setBasePositiveItemLabelPosition(new
			// ItemLabelPosition(ItemLabelAnchor.OUTSIDE1,
			// TextAnchor.BASELINE_RIGHT));

			// 柱子宽度
			renderer.setMaximumBarWidth(0.08);
			// 柱子颜色
			renderer.setSeriesPaint(0, ChartColor.LIGHT_BLUE);
			renderer.setSeriesPaint(1, ChartColor.LIGHT_CYAN);
			renderer.setSeriesPaint(2, ChartColor.LIGHT_GREEN);
			// 不显示阴影
			renderer.setShadowVisible(false);
			// 去除水平柱状图上方的阴影
			renderer.setBarPainter(new StandardBarPainter());

		}

		return convert(chart.createBufferedImage(width, height));
	}

}
