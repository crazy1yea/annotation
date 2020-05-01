package com.example.annotation.util.scheduler;

import java.awt.Color;
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
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
/**
 * 环状图
 */
public class RingChart{
	
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

	public InputStream createChart(DefaultPieDataset chartData, int width, int height) throws IOException {
		JFreeChart chart = ChartFactory.createRingChart("", // 图表标题
				chartData, // 数据集
				false, // 是否显示图例，对于简单的柱状图必须为false
				false, // 是否生成提示工具
				false // 是否生成url链接
		);

		// 整体画布背景色
		chart.setBorderVisible(false); // 边框
		RingPlot plot = (RingPlot) chart.getPlot();
		plot.setOutlineVisible(false);
		// {2}表示显示百分比
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}"));
		plot.setBackgroundPaint(new Color(253, 253, 253));
		plot.setOutlineVisible(false);
		// 设置标签样式
		plot.setLabelFont(new Font("宋体", Font.BOLD, 15));
		plot.setSimpleLabels(true);
		plot.setLabelLinkPaint(Color.WHITE);
		plot.setLabelOutlinePaint(Color.WHITE);
		plot.setLabelLinksVisible(false);
		plot.setLabelShadowPaint(null);
		plot.setLabelOutlinePaint(new Color(0, true));
		plot.setLabelBackgroundPaint(new Color(0, true));
		plot.setLabelPaint(Color.WHITE);

		plot.setSeparatorsVisible(true);
		plot.setSeparatorPaint(Color.WHITE);
		plot.setShadowPaint(new Color(253, 253, 253));
		plot.setSectionDepth(0.58);
		plot.setStartAngle(90);

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
