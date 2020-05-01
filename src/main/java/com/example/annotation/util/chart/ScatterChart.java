package com.example.annotation.util.chart;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYDataset;

/**
 * 散点图 2019年4月1日
 */
public class ScatterChart {
	static {
		// 创建主题样式
		StandardChartTheme mChartTheme = new StandardChartTheme("CN");

		// 设置标题字体
		mChartTheme.setExtraLargeFont(new Font("微软雅黑", Font.PLAIN, 16));

		// 设置轴向字体
		mChartTheme.setLargeFont(new Font("微软雅黑", Font.PLAIN, 12));

		// 设置图例字体
		mChartTheme.setRegularFont(new Font("微软雅黑", Font.PLAIN, 12));

		// 应用主题样式
		ChartFactory.setChartTheme(mChartTheme);
	}

	/**
	 * 散点图 2019年4月1日- 上午11:33:06
	 * 
	 * @param chartData
	 * @param height
	 * @param width
	 * @return
	 * @throws IOException
	 */
	public InputStream createChart(XYDataset chartData, int height, int width) throws IOException {

		JFreeChart chart = ChartFactory.createScatterPlot("", // 图表标题
				"", // 目录轴的显示标签
				"", // 数值轴的显示标签
				chartData, // 数据集
				PlotOrientation.VERTICAL, // 图表方向
				true, // 是否显示图例，对于简单的柱状图必须为false
				true, // 是否生成提示工具
				false // 是否生成url链接
		);
		LegendTitle legend = chart.getLegend();
		if (legend != null) {
			legend.setItemFont(new Font("宋体", Font.BOLD, 20));
		}
		XYPlot plot = chart.getXYPlot();
		// 图表区域背景色
		plot.setBackgroundPaint(ChartColor.WHITE);
		// 设置表格线颜色及样式 - 横线
		plot.setRangeGridlinePaint(ChartColor.GRAY);
		plot.setRangeGridlineStroke(new BasicStroke(BasicStroke.CAP_BUTT));
		// false隐藏边框
		plot.setOutlineVisible(false);
		// 设置表格线颜色及样式 - 纵线
		plot.setDomainGridlinePaint(ChartColor.GRAY);
		plot.setDomainGridlineStroke(new BasicStroke(BasicStroke.JOIN_MITER));
		plot.setDomainGridlinesVisible(true);
		
		XYItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
		renderer.setSeriesShape(1, new java.awt.geom.Ellipse2D.Double(-1D, -2D, 10D, 10D));
		
		return convert(chart.createBufferedImage(width, height));

	}

	/**
	 * BufferedImage to InputStream 
	 * 2017年11月10日 下午9:21:10
	 * 
	 * @param imgbf
	 * @return
	 * @throws IOException
	 */
	private InputStream convert(BufferedImage imgbf) throws IOException {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			ImageIO.write(imgbf, "png", os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			return is;
		}
	}

}
