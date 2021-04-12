package Sailkatzailea;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import java.io.PrintWriter;
import java.util.Random;

public class ParametroEkorketa {
    public static void main(String[] args) throws Exception {
    	
    	DataSource dataSource = new DataSource(args[0]);
    	Instances data = dataSource.getDataSet();
    	data.setClassIndex(data.numAttributes()-1);
    	double maximoa = 0.0;
    	PrintWriter pw = new PrintWriter((args[1]));
    	pw.println();
    	pw.println("SMO parametro ekorketa");
    	pw.println("2 parametro optimizatuko ditugu:");
    	pw.println("1- C");
    	pw.println("2- Kernel.gamma");
    	pw.println("Ebaluazio metrika: Accuracy");
    	pw.println();
    	
    	SMO smo = new SMO();
    	double oinarria=10;
    	double cmax=0.0;
    	double gammamax=0.0;
    	RBFKernel kernel= new RBFKernel();
    	
        for (double exp =-3.0; exp<4.0; exp+=1.0) {
        	double c=Math.pow(oinarria,exp);
        	smo.setC(c);
        	for (double exp1 =-3.0; exp1<4.0; exp1+=1.0) {
            	double gamma=Math.pow(oinarria,exp1);
            	kernel.setGamma(gamma);
            	smo.setKernel(kernel);
        		smo.buildClassifier(data);
        		Evaluation eval= new Evaluation(data);
        		eval.crossValidateModel(smo, data, 10, new Random(1));

        		System.out.println();

        		System.out.println(eval.pctCorrect());

        		if (eval.pctCorrect() > maximoa) {
        			maximoa = eval.pctCorrect();
        			cmax=c;
        			gammamax=gamma;
        		}
        	}
        }
        pw.println("C hoberena:");
        pw.println(cmax);
        pw.println("Gamma hoberena:");
        pw.println(gammamax);
        pw.println("Accuracy:");
        pw.println(maximoa);
        pw.close();
    }
}
