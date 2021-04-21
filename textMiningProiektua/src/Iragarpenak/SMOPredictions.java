package Iragarpenak;

import java.io.PrintWriter;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;


public class SMOPredictions {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main (String[] args) throws Exception, IllegalArgumentException{
		
        if (args.length!=3) {
        	
        	System.out.println("3 argumentu sartu behar dituzu");
            System.out.println("1. argumentua -> .arff fitxategia");
            System.out.println("2. argumentua -> modeloa (deserialize)");
            System.out.println("3. argumentua -> irteera fitxategia");
            System.out.println("java -jar SMOPredictions.jar test.arff classifier.model predictions.txt");
            
        }
        else{
            DataSource source=new DataSource(args[0]);
            Instances data=source.getDataSet();
            data.setClassIndex(data.numAttributes()-1);
            
            Classifier model = (Classifier) SerializationHelper.read(args[1]);
            Evaluation eval = new Evaluation (data);
            eval.evaluateModel(model, data);
            
            PrintWriter pw = new PrintWriter (args[2]);
            pw.println("Iragarpenak: ");
            pw.println();
            
            for(int i=0; i< eval.predictions().size();i+=1){
                double predicted = eval.predictions().get(i).predicted();
                pw.println((i+1)+"\t"+data.instance(i).stringValue(data.classIndex())+"\t"+data.classAttribute().value((int)predicted)+"\n");
            }
            
            pw.println("\n"+eval.toClassDetailsString());
            pw.println("\n"+eval.toSummaryString());
            pw.println("\n"+eval.toMatrixString());

            long Hasiera = System.currentTimeMillis();       
            Thread.sleep(1000);           
            long Amaiera = System.currentTimeMillis();           
            double Denbora = (double) ((Amaiera - Hasiera)/1000);      
            pw.println("Exekuzio denbora: "+ Denbora +" segundu");
            pw.close();
            
        }
	}
}