package com.example.annotation.util.scheduler;

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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.VerticalAlignment;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 图表抽象 
 * 2017年11月10日 下午8:31:07
 */
public abstract class JFChart {

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
	 * 通用设置 
	 * 2017年11月10日 下午8:56:18
	 */
	protected void commonSet(JFreeChart chart) {
		// 整体画布背景色
		chart.setBackgroundPaint(ChartColor.WHITE);
		chart.setBorderVisible(false); // 边框

		/****** 图例样式 *****/
		LegendTitle legend = chart.getLegend();

		if (legend != null) {
			// 图例位置
			legend.setVerticalAlignment(VerticalAlignment.TOP);
			legend.setPosition(RectangleEdge.TOP);
			legend.setBorder(0, 0, 0, 0); // 边框
		}

		/****** 图表区样式 ******/
		{
			// 获得图表对象
			CategoryPlot plot = chart.getCategoryPlot();
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

			// 折点图标
			CategoryItemRenderer crenderer = plot.getRenderer();
			if (crenderer instanceof LineAndShapeRenderer) {
				LineAndShapeRenderer renderer = (LineAndShapeRenderer) crenderer;
				renderer.setDefaultShapesVisible(true);
				renderer.setUseFillPaint(true);

				// 设置y轴
				ValueAxis rangeAxis = plot.getRangeAxis();
				rangeAxis.setAutoRange(true);
				rangeAxis.setUpperMargin(0.20);
				rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());// Y轴显示整数
				rangeAxis.setAutoRangeMinimumSize(1); // 最小跨度
				// updated by caohuiwu,设置Y轴，当值都为0时
				if (plot.getDataRange(plot.getRangeAxis()).getUpperBound() <= 0) {
					plot.getRangeAxis().setRange(0.0, 1.0);
				}

				// domainAxis.setLowerMargin(-0.05);// 左边距 边框距离
				// domainAxis.setLowerMargin(0);// 左边距 边框距离
				// domainAxis.setUpperMargin(-0.01);// 右边距
				// 边框距离,防止最后边的一个数据靠近了坐标轴。
				// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);//
				// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
			} else if (crenderer instanceof BarRenderer) {
				// 柱子透明度
				plot.setForegroundAlpha(0.6f);

				// 柱子距离
				CategoryAxis domainAxis = plot.getDomainAxis();
				domainAxis.setUpperMargin(0.1);
				domainAxis.setLowerMargin(0.1);

				ValueAxis rangeAxis = plot.getRangeAxis();
				rangeAxis.setAutoRange(true);
				rangeAxis.setUpperMargin(0.20);
				rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());// Y轴显示整数
				rangeAxis.setAutoRangeMinimumSize(1); // 最小跨度
				// 隐藏数值坐标
				// plot.getRangeAxis().setVisible(false);
				// plot.getRangeAxis().setRange(0.0, 105.0);

				plot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 0D));

			}
			/*
			// 设置背景颜色
			plot.setBackgroundPaint(Color.WHITE);
			// 设置网格竖线颜色
			plot.setDomainGridlinePaint(Color.pink);
			// 设置网格横线颜色
			plot.setRangeGridlinePaint(Color.pink);
			plot.setNoDataMessage("暂无数据显示！");// 没有数据显示的时候显示这个提示
			// 取得横轴
			CategoryAxis categoryAxis = plot.getDomainAxis();
			// 设置横轴的字体
			categoryAxis.setLabelFont(new Font("宋体", Font.BOLD, 16));
			// 设置分类标签以45度倾斜
			categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);//
			// 设置分类标签字体
			categoryAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 18));
			// 取得纵轴
			NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
			numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			// 设置纵轴的字体
			numberAxis.setLabelFont(new Font("黑体", Font.PLAIN, 18));
			numberAxis.setUpperMargin(0.15);//设置最高数据显示与顶端的距离
			numberAxis.setLowerMargin(2);//设置最低的一个值与图片底端的距离
			// 设置背景透明度（0~1）
			plot.setBackgroundAlpha(1f);
			// 设置前景色透明度（0~1）
			plot.setForegroundAlpha(1f);
			// 获取显示线条的对象
			LineAndShapeRenderer lasp = (LineAndShapeRenderer) plot.getRenderer();
			// 设置拐点是否可见/是否显示拐点
			lasp.setDefaultShapesVisible(true);
			// 设置拐点不同用不同的形状
			lasp.setDrawOutlines(true);
			// 设置线条是否被显示填充颜色
			lasp.setUseFillPaint(true);
			// 设置拐点颜色
			lasp.setDefaultFillPaint(Color.blue);//蓝色
			// 设置折线加粗
			//lasp.setSeriesStroke(0, new BasicStroke(3F));
			lasp.setSeriesOutlineStroke(0, new BasicStroke(4.5F));//设置折点的大小
			lasp.setSeriesOutlineStroke(1, new BasicStroke(4.5F));
			// 设置折线拐点形状
			lasp.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
			lasp.setSeriesShape(1, new java.awt.geom.Ellipse2D.Double(-1D, -2D, 10D, 10D));
			*/
		}
	}

	/**
	 * BufferedImage to InputStream 
	 * 2017年11月10日 下午9:21:10
	 * @param imgbf
	 * @return
	 * @throws IOException
	 */
	protected InputStream convert(BufferedImage imgbf) throws IOException {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
			ImageIO.write(imgbf, "png", os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			return is;
		}
	}
	/**
	 * @param chartData
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public abstract InputStream createChart(DefaultCategoryDataset chartData,int width,int height) throws IOException;
}
