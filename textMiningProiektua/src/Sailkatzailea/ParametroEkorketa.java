package Sailkatzailea;

import java.io.PrintWriter;
import java.util.Random;

import com.sun.javadoc.Tag;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.net.search.global.GeneticSearch;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;

public class ParametroEkorketa {
	
	private static PrintWriter pw;
	private static Instances data;
	
    public static void main(String[] args) throws Exception {
    	
    	
    	//Aldatu  behar dut, arff helbidea txarto sartzen deneko kasuan
    	DataSource source = new DataSource(args[0]);
    	data = source.getDataSet();
        data.setClassIndex(data.numAttributes()-1);
    	double maximoa = 0.0;
    	pw = new PrintWriter((args[1]));
    	pw.println();
    	pw.println("SMO parametro ekorketa");
    	pw.println("2 parametro optimizatuko ditugu:");
    	pw.println("1- C");
    	pw.println("2- Kernel.gamma");
    	pw.println("Ebaluazio metrika: Klase minoritarioaren fMeasure");
    	pw.println();
    	
    	SMO smo = new SMO();
    	double oinarria=10;
    	double cmax=0.0;
    	double gammamax=0.0;
    	RBFKernel kernel= new RBFKernel();
    	int i = klaseminoritarioa();
        for (double exp =-3.0; exp<4.0; exp+=1.0) {
        	double c=Math.pow(oinarria,exp);
        	smo.setC(c);
        	for (double exp1 =-3.0; exp1<4.0; exp1+=1.0) {
            	double gamma=Math.pow(oinarria,exp1);
            	kernel.setGamma(gamma);
            	smo.setKernel(kernel);
        		Evaluation eval= new Evaluation(data);
        		eval.crossValidateModel(smo, data, 10, new Random(1));

        		System.out.println(exp1);

        		System.out.println(eval.fMeasure(i));

        		if (eval.fMeasure(i) > maximoa) {
        			maximoa = eval.fMeasure(i);
        			cmax=c;
        			gammamax=gamma;
        		}
        	}
        }
        pw.println("C hoberena:");
        pw.println(cmax);
        pw.println("Gamma hoberena:");
        pw.println(gammamax);
        pw.println("Klase minoritarioaren fMeasure hoberena:");
        pw.println(maximoa);
        pw.close();
        
    }
    private static int klaseminoritarioa() throws Exception{
    	
    		//Klase minoritarioaren indizea itzultzen du
    		pw.println(data.attribute(data.numAttributes()-1).name()+" atributu nominala da eta hauek dira ezaugarriak:");
            int[] counts = data.attributeStats(data.numAttributes()-1).nominalCounts;
            int min = 0; //Hemen klase minoritarioaren posizioa gordeko da
            for(int j=0; j<counts.length; j++){
                if(counts[min] > counts[j]) {
                	min = j;
                }
                pw.println(data.attribute(data.numAttributes()-1).value(j) + " -> " + counts[j] + " | MAIZTASUNA -> " + (float)counts[j]/data.attributeStats(data.numAttributes()-1).totalCount);
            }
            pw.println("BALIO MINIMOA: " + data.attribute(data.numAttributes()-1).value(min) + " -> " + counts[min] + "\n");
            return min;
    }   
    	
}
