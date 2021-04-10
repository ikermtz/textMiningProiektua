package Sailkatzailea;

import java.io.PrintWriter;
import java.util.Random;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemovePercentage;

public class SVM {

	public static void main(String[] args) throws Exception {
		if (args.length==3) {
	    	   
	    	   DataSource source = new DataSource(args[0]);
	           Instances data = source.getDataSet();
	           data.setClassIndex(data.numAttributes()-1);
	           
	           //Ebaluazio ez zintzoa
	           LibSVM svmGuztiak= new LibSVM();
	           svmGuztiak.setCost(1);					 //parametroak gehitu
	           svmGuztiak.setGamma(1);
	           svmGuztiak.buildClassifier(data);
	           
	           //Gorde modeloa entrenamendu multzo osoa erabiliz
	           SerializationHelper.write(args[1], svmGuztiak);
	           
	           //HoldOut
	           
	           //randomize egin
	           Randomize filter = new Randomize();
	           filter.setRandomSeed(0);
	           filter.setInputFormat(data);
	           data = Filter.useFilter(data, filter);

	           //dev eta train instantzia azpimultzoak ateratzeko
	           RemovePercentage remove = new RemovePercentage();
	           remove.setInputFormat(data);
	           remove.setInvertSelection(false);
	           remove.setPercentage(30);
	           Instances train = Filter.useFilter(data, remove);	//train
	           
	           RemovePercentage remove1 = new RemovePercentage();
	           remove1.setInputFormat(data);
	           remove1.setPercentage(30);
	           remove1.setInvertSelection(true);
	           Instances dev = Filter.useFilter(data, remove1);		//dev
	           
	           //LibSVM entrenatu train azpimultzoarekin
	           LibSVM svm = new LibSVM();
	           svmGuztiak.setCost(1);					//parametroak gehitu
	           svmGuztiak.setGamma(1);
	           svm.buildClassifier(train);
	           
	           //Ebaluaketak gorde
	           PrintWriter pw = new PrintWriter(args[2]);	//ez zintzoa
	           Evaluation eval1 = new Evaluation(data);
	           eval1.evaluateModel(svmGuztiak, data);
	           
	           Evaluation eval2 = new Evaluation(train);	//holdout
	           eval2.evaluateModel(svm, dev);
	           
	           Evaluation eval3 = new Evaluation(data);		//crossvalidation
	           eval3.crossValidateModel(svmGuztiak, data, 10, new Random(1));
	           
	           pw.println("KALITATEAREN ESTIMAZIOA:");
	           pw.println();
	           pw.println("---------------------------------------------");
	           pw.println();
	           pw.println("Ebaluazio ez-zintzoa");
	           pw.println(eval1.toSummaryString());
	           pw.println(eval1.toClassDetailsString());
	           pw.println(eval1.toMatrixString());
	           pw.println();
	           pw.println("---------------------------------------------");
	           pw.println();
	           pw.println("Hold-Out");
	           pw.println(eval2.toSummaryString());
	           pw.println(eval2.toClassDetailsString());
	           pw.println(eval2.toMatrixString());
	           pw.println();
	           pw.println("---------------------------------------------");
	           pw.println();
	           pw.println("10-fold cross validation");
	           pw.println(eval3.toSummaryString());
	           pw.println(eval3.toClassDetailsString());
	           pw.println(eval3.toMatrixString());
	           
	           pw.close();

	           
	       }
	       else {
	    	   System.out.println("java -jar LibSVM.jar trainPath.arff svmpath.model Kalitatea.txt");
	       }

	}

}
