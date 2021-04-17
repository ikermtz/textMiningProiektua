package Clusterer;

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
			
			Classifier smo = new SMO();
			Evaluation eval = new Evaluation(data);
			eval.crossValidateModel(smo, data, 10, new Random(1));
			
			ThresholdCurve tc = new ThresholdCurve();
			int classIndex = 0;
			Instances result = tc.getCurve(eval.predictions(), classIndex);
			
			// plot curve
			ThresholdVisualizePanel vmc = new ThresholdVisualizePanel();
			vmc.setROCString("(Area under ROC = " + ThresholdCurve.getROCArea(result) + ")");
			vmc.setName(result.relationName());
			PlotData2D tempd = new PlotData2D(result);
			tempd.setPlotName(result.relationName());
			tempd.addInstanceNumberAttribute();
			
			// specify which points are connected
			boolean[] cp = new boolean[result.numInstances()];
			for (int n = 1; n < cp.length; n++)
				cp[n] = true;
			tempd.setConnectPoints(cp);

			// add plot
			vmc.addPlot(tempd);
			
			// display curve
			JFrame frameRoc = new javax.swing.JFrame("ROC Curve");
			frameRoc.setSize(1000, 700);
			frameRoc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frameRoc.getContentPane().add(vmc);
			frameRoc.setVisible(true);
			}
}

