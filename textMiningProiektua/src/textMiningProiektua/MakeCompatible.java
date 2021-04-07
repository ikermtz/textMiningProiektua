package textMiningProiektua;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.FixedDictionaryStringToWordVector;
import weka.filters.unsupervised.attribute.Reorder;

public class MakeCompatible {
	
	public static void main(String[] args) throws Exception {
		
		if(args.length  !=3) {
			System.out.println("Ez duzu arguments atala behar bezala bete!");
		
			//Arguments atalean 3 parametro ezberdin behar ditu programa honek
			//1.parametroa jada exisistitzen den .arff fitxategia izango da (dev multzoa lortzeko)
			//2. parametroa TransformRaw-etik lortzen dugun hiztegia izango da
			//3. parametroa programa honek sortuko duen .arff fitxategia gordetzeko helbidea izango da
		
		
		}
		else{
			String arffIn= args[0];
			String dictionary= args[1];
			String arffOut= args[2];
			
			//dev multzoa lortu (arguments atalean sartzen den lehenengo parametroa args[0])
			DataSource source=null;
			if (arffIn.contains(".csv")) convertCSVtoArff(arffIn);
			
			try {
				source = new DataSource(arffIn);
			} catch (Exception e) {
				System.out.println("ERROREA - Sarrerako fitxategiaren helbidea okerra da");
			} 
			Instances dev= source.getDataSet();
			
			//dev multzoaren klasea definitu
			if(dev.classIndex()==-1)
				dev.setClassIndex(dev.numAttributes()-1);
			
				
			
			//Orain parametro bezala (dictionary) lortu dugun hiztegia dev ean sartuko dugu FixedDictionaryStringToWordVector erabiliz
			FixedDictionaryStringToWordVector hiztegia= new FixedDictionaryStringToWordVector();
			hiztegia.setDictionaryFile(new File(dictionary));
			hiztegia.setInputFormat(dev);
			dev=Filter.useFilter(dev, hiztegia);
			
			
			//Atributuak reordenartu, klasea amaieran agertu dadin, horretarako reorder filtroa erabiliko dugu
			Reorder reorder = new Reorder();
			reorder.setAttributeIndices("2-" + dev.numAttributes() + ",1");   
			reorder.setInputFormat(dev);
			dev = Filter.useFilter(dev, reorder);
			
		
			//arff fitxategia berria sortu arguments atalean sartu dugun helbidean (arffOut)
			ArffSaver arffSaver = new ArffSaver();
			arffSaver.setInstances(dev);
			arffSaver.setDestination(new File(arffOut));
			arffSaver.setFile(new File(arffOut.toString()+".arff"));
			arffSaver.writeBatch();	
		
	
		}		

	}
	private static void convertCSVtoArff(String filename) throws Exception {

		// load CSV
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(filename));

		// CSV uses no header
		String[] options = new String[1];
		options[0] = "-H";
		loader.setOptions(options);

		Instances data = loader.getDataSet();

		// save ARFF
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);

		filename = filename.replace(".csv", ".arff");

		// saver.setDestination(new File(filename));
		saver.setFile(new File(filename));
		saver.writeBatch();

	}
}



