package Sailkatzailea;

import java.io.PrintWriter;
import java.util.Random;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemovePercentage;

// TODO: Auto-generated Javadoc
/**
 * The Class LogisticRegression.
 */
public class LogisticRegression {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {        
       
		if (args.length==3) {
    	   
    	   DataSource source = new DataSource(args[0]);
           Instances data = source.getDataSet();
           data.setClassIndex(data.numAttributes()-1);
           
           //Ebaluazio ez zintzoa
           Logistic lrGuztiak= new Logistic();
           lrGuztiak.buildClassifier(data);
           
           //HoldOut 100 aldiz
           double max = 0;
           int bestRandomSeed=0;
           for (int i=0; i<100; i++) {
        	   double wfscore = holdOutEgin(i, data).weightedFMeasure();
        	   if (wfscore>max)	//weighted f-score parametroa ekortzeko
        		   max = wfscore;
        		   bestRandomSeed = i;
           }
           Evaluation eval2=holdOutEgin(bestRandomSeed, data);
           
           //logistic regression (crossvalidation)
           Logistic lrcross = new Logistic();
           
           
           //Ebaluaketak gorde
           PrintWriter pw = new PrintWriter(args[2]);	//ez zintzoa
           Evaluation eval1 = new Evaluation(data);
           eval1.evaluateModel(lrGuztiak, data);
           
          
           
           Evaluation eval3 = new Evaluation(data);		//crossvalidation
           eval3.crossValidateModel(lrcross, data, 10, new Random(1));
           
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
           
         //Gorde modeloa entrenamendu multzo osoa erabiliz
           SerializationHelper.write(args[1], lrGuztiak);

           
       }
       else {
    	   System.out.println("java -jar LogisticRegression.jar trainPath.arff LRpath.model irteerahelbidea");
       }

	}
	private static Evaluation holdOutEgin (int i, Instances data) throws Exception {
		//randomize egin
        Randomize filter = new Randomize();
        filter.setRandomSeed(i);
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
        
        //logistic regression entrenatu train azpimultzoarekin (holdout)
        Logistic lr = new Logistic();
        lr.buildClassifier(train);
        
        Evaluation eval2 = new Evaluation(train);	//ebaluazioak
        eval2.evaluateModel(lr, dev);
        
        return eval2;
	}

}
