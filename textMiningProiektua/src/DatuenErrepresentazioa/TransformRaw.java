package DatuenErrepresentazioa;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Reorder;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.SparseToNonSparse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class TransformRaw {
	
	/*
	 * 3 Parametro behar ditu programak:
	 * 	1. Testuak(instantziak) dauden direktorioa
	 * 	2. Ateratzen den Arff-aren path-a
	 */
	
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("");
            System.out.println("");
            System.out.println("java -jar TransformRaw.jar rawData.arff dictionary -I/--tfidf -N/--nonsparse ");
        }
        
        else if(args.length<2 || args.length>4) System.out.println("Sintaxia txarto dago. Laguntza jasotzeko argumenturik ez erabili");
        
        else{
            
            List<String> list = Arrays.asList(args);
            
            DataSource dataSource = new DataSource(args[0]);
            Instances data = dataSource.getDataSet();
            data.setClassIndex(data.numAttributes()-1);

            
            StringToWordVector filter = new StringToWordVector();	//Raw-tik bektore formatura
            filter.setInputFormat(data);   
            filter.setLowerCaseTokens(true);
            filter.setDictionaryFileToSaveTo(new File(args[1]));
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
            as.setFile(new File("/home/jorge/transformed.arff")); //TODO hau aldatu behar da
            as.writeBatch();
            
            System.out.println("holaquetal");//hau kendu
        }
    }
}