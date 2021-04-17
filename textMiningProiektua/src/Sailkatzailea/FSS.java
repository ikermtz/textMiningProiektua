package Sailkatzailea;

import java.io.File;
import java.io.PrintWriter;

import weka.attributeSelection.GainRatioAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
//import weka.attributeSelection.AttributeSelection;
import weka.filters.supervised.attribute.AttributeSelection;


public class FSS {

	public static void main(String[] args) throws Exception{
		if (args.length==3) {
			DataSource sauce = new DataSource(args[0]);
			Instances data = sauce.getDataSet();
			data.setClassIndex(data.numAttributes()-1);
			
			
			GainRatioAttributeEval gr = new GainRatioAttributeEval();	//InfoGainRatio atributu ebaluatzailea 
			
			Ranker ranker = new Ranker();		//atributuak rankeatzeko
			ranker.setThreshold(0.01);			//balio hau aldatzen probatu
			ranker.setNumToSelect(50);
			
			AttributeSelection as = new AttributeSelection();
			as.setInputFormat(data);
			as.setEvaluator(gr);
			as.setSearch(ranker);
			Instances filteredData = Filter.useFilter(data, as);
			
			ArffSaver saver = new ArffSaver();
			saver.setInstances(filteredData);
			saver.setFile(new File(args[1]));
			saver.writeBatch();
			
			PrintWriter pw = new PrintWriter(args[2]);				//hiztegia gordetzeko
			for (int i=0; i<filteredData.numAttributes()-1; i++) {
                pw.println(filteredData.attribute(i).name());
            }
            pw.close();
			
		}
		else {
			System.out.println("3 parametro behar dira eta zuk "+args.length+" jarri dituzu!!");
			System.out.println("java -jar FSS.jar trainPath.arff filteredPath.arff hiztegia");
		}

	}

}
