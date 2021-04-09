package textMiningProiektua;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;

public class GetRaw {
	
	/*
	 * 2 Parametro behar ditu programak:
	 * 	1. Testuak(instantziak) dauden direktorioa
	 * 	2. Ateratzen den Arff-aren path-a
	 */
	
	public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length==0) {
			System.out.println("Gordinik datorren train fitxategia ala direktorioa emanda, train.arff\n"
					+ "lortu behar da");
			System.out.println("Aurrebaldintzak:");
			System.out.println("	*Parametro bat sartzea");
			System.out.println("Postbaldintzak:");
			System.out.println("	*Arff bat bueltan");
			System.out.println("Argumentuak: Direktorio edo fitxategiaren path-a, arff-a gordeko den path-a");
			System.out.println("java -jar GetRaw.jar rawPath arffPath.arff");
		}
		else if (args.length==2) {
			/*FileWriter fw= new FileWriter(args[1]);	
			PrintWriter pw = new PrintWriter(fw);
			
			String cmd = "java -cp "+ System.getProperty("user.dir")+"/jars/weka.jar" +" weka.core.converters.TextDirectoryLoader -dir "+args[0];
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec(cmd);
			pr.waitFor();
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			while ((line=buf.readLine())!=null) {
				pw.println(line);
				//System.out.println(line);
			
			}
			pw.close();*/
			
			TextToArffConverter(args[0], args[1]);
			
		}
		else System.out.println("Sintaxia txarto dago. Laguntza jasotzeko argumenturik ez erabili");

	}
	
	public static void TextToArffConverter(String Directory, String target) throws IOException {

		TextDirectoryLoader loader = new TextDirectoryLoader();
	    loader.setDirectory(new File(Directory));
	    Instances dataRaw = loader.getDataSet();
	    	    
	    FileWriter fw = new FileWriter (target);
	    PrintWriter writer = new PrintWriter(fw);
	    writer.println(dataRaw);
	    writer.close();
	}

}
