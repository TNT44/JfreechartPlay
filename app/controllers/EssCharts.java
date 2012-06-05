package controllers;

import play.*;
import play.mvc.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ShapeUtilities;

import models.*;

public class EssCharts extends Controller {

	public static void adate() throws IOException {

		BufferedImage bf = getChartViewer();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bf, "jpg", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		InputStream is = new ByteArrayInputStream(imageInByte);

		response.contentType = "image/jpg";
		renderBinary(is);
	}

	private static DefaultCategoryDataset getDataset() {
		// categories...
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		defaultcategorydataset.addValue(0.0D, "Series 1", "Jan");
		defaultcategorydataset.addValue(2D, "Series 1", "Feb");
		defaultcategorydataset.addValue(1.0D, "Series 1", "Mar");
		defaultcategorydataset.addValue(3D, "Series 1", "Apr");
		defaultcategorydataset.addValue(5D, "Series 1", "May");
		defaultcategorydataset.addValue(7D, "Series 1", "Jun");

		return defaultcategorydataset;
	}

	public static BufferedImage getChartViewer() {
		DefaultCategoryDataset xydataset = getDataset();
		BufferedImage chartImage = null;

		JFreeChart jfreechart = ChartFactory.createLineChart("Chart", "Month",
				"Values", xydataset, PlotOrientation.VERTICAL, false, false,
				false);
		//jfreechart.setBackgroundPaint(Color.cyan);
		jfreechart.setBackgroundPaint(Color.white);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();

		categoryplot.setBackgroundPaint(Color.lightGray);
		categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
		categoryplot.setDomainGridlinePaint(Color.white);
		categoryplot.setRangeGridlinePaint(Color.white);

		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		lineandshaperenderer.setSeriesShapesVisible(0, true);

		lineandshaperenderer
				.setSeriesShape(0, ShapeUtilities.createDiamond(4F));
		lineandshaperenderer.setDrawOutlines(true);
		lineandshaperenderer.setUseFillPaint(true);
		lineandshaperenderer.setBaseFillPaint(Color.white);

		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// OPTIONAL CUSTOMISATION COMPLETED.

		ChartRenderingInfo info = null;

		try {

			info = new ChartRenderingInfo(new StandardEntityCollection());
			chartImage = jfreechart.createBufferedImage(550, 350, info);

		} catch (Exception e) {
			// handel your exception here
		}

		return chartImage;
	}

}