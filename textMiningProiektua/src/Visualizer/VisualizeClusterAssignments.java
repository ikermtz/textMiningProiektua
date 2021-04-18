package Visualizer;

import java.util.Random;

import javax.swing.JFrame;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;

public class VisualizeClusterAssignments {
	
		public static void main(String args[]) throws Exception {
			
			DataSource source = new DataSource(args[0]);
			Instances data= source.getDataSet();
			data.setClassIndex(data.numAttributes()-1);
	
			// plot curve
			ThresholdVisualizePanel vmc = new ThresholdVisualizePanel();
			vmc.setROCString("(Area under ROC = " + ThresholdCurve.getROCArea(data) + ")");
			vmc.setName(data.relationName());
			PlotData2D tempd = new PlotData2D(data);
			tempd.addInstanceNumberAttribute();

			vmc.addPlot(tempd);
			
			// display curve
			JFrame frameRoc = new javax.swing.JFrame("ROC Curve");
			frameRoc.setSize(1000, 700);
			frameRoc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frameRoc.getContentPane().add(vmc);
			frameRoc.setVisible(true);
			}
}

