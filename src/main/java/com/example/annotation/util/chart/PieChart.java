package com.example.annotation.util.chart;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
/**
 * 圆饼图
 */
public class PieChart {
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
	
	public InputStream createChart(DefaultPieDataset chartData, int height, int width) throws IOException {
		JFreeChart chart = ChartFactory.createPieChart("", // 图表标题
				chartData, // 数据集
				false, // 是否显示图例，对于简单的柱状图必须为false
				false, // 是否生成提示工具
				false // 是否生成url链接
		);
		
		

		// 整体画布背景色
		chart.setBorderVisible(false); // 边框
		PiePlot plot = (PiePlot) chart.getPlot();
		// 设置标签字体
		plot.setStartAngle(new Float(3.14f / 2f));
		// 设置plot的前景色透明度
		plot.setForegroundAlpha(0.7f);
		// 设置plot的背景色透明度
		plot.setBackgroundAlpha(0.0f);
		// 设置标签生成器(默认{0})
		// {0}:key {1}:value {2}:百分比 {3}:sum
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1}占{2})"));
		plot.setOutlineVisible(false);
		
		return convert(chart.createBufferedImage(width, height));
	}
	/**
	 * BufferedImage to InputStream 
	 * 2017年11月10日 下午9:21:10
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
