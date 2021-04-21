package DatuenErrepresentazioa;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Reorder;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.SparseToNonSparse;

public class TransformRaw {
	
	/*
	 * 3 Parametro behar ditu programak:
	 * 	1. Testuak(instantziak) dauden direktorioa
	 * 	2. Ateratzen den Arff-aren path-a
	 */
	
    /**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("");
            System.out.println("");
	    	System.out.println(args.length + " parametro sartu dituzu");
            System.out.println("java -jar TransformRaw.jar rawData.arff dictionary -I/--tfidf -N/--nonsparse transformed.arff");
        }
        
        else if(args.length<3 || args.length>5) System.out.println("Sintaxia txarto dago. Laguntza jasotzeko argumenturik ez erabili");
        
        else{
            
            List<String> list = Arrays.asList(args);
            
            DataSource dataSource = new DataSource(args[0]);
            Instances data = dataSource.getDataSet();
            data.setClassIndex(data.numAttributes()-1);

            
            StringToWordVector filter = new StringToWordVector();	//Raw-tik bektore formatura
            filter.setInputFormat(data);   
            filter.setLowerCaseTokens(true);
            filter.setDictionaryFileToSaveTo(new File(args[1]));	//hiztegia ez da behar hemen lortzea
            if (list.contains("-I") || list.contains("--tfidf")) filter.setIDFTransform(true); //tfidf aukera gehitzeko
            data = Filter.useFilter(data,filter);
            
            if (list.contains("-N") || list.contains("--nonsparse")) {	//nonsparse-ra pasatzeko
                SparseToNonSparse filter2 = new SparseToNonSparse();
                filter2.setInputFormat(data);
                data = Filter.useFilter(data, filter2);
            }
            
            Reorder reorder = new Reorder();
            reorder.setAttributeIndices("2,last-1");
            reorder.setInputFormat(data);
            data = Filter.useFilter(data, reorder);

            ArffSaver as = new ArffSaver();
            as.setInstances(data);
            as.setFile(new File(args[4]));
            as.writeBatch();
            
        }
    }
}