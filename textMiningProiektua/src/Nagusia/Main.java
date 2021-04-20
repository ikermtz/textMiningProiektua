package Nagusia;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import DatuenErrepresentazioa.GetRaw;
import DatuenErrepresentazioa.TransformRaw;

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main {
	
	private static Scanner sc = new Scanner(System.in);
	private static String workspace = System.getProperty("user.home")+"/text_mining";
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main (String args[]) throws Exception{
		String workspace = System.getProperty("user.home")+"/text_mining";//añadir /?
		
		if (args.length != 1) {
			System.out.println("Defektuzko direktorioa erabiliko da (sortuko da ez existitzekotan): "+ workspace);
			System.out.println("Beste direktorioren bat nahiago izatekotan parametro gisa pasa diezaiokezu programa exekutatzerakoan: java -jar Main.jar lantokiarenHelbidea");
			System.out.println();
			pressEnterToContinue();
			if (Files.notExists(Paths.get(workspace)))
				karpetaSortu(workspace);
		}
		
		else {
			workspace = args[0];
			System.out.println("Hurrengo direktorioa erabiliko da: "+ workspace); //pedir /?
			pressEnterToContinue();
		}
		
		System.out.println("Ongi etorri!");
		System.out.println();		
		
		String aukera;
		boolean bukatu=false;
		while (!bukatu) {
			menuaPantailaratu();
			aukera=sc.next();
			
			 switch (aukera) 
		        {
		            case "1": aukera1();  
		                    break;
		            case "2": aukera2();
		                    break;
		            case "3":  ;
		                    break;
		            case "4":  ;
		                    break;
		            case "5":  ;
		                    break;
		            case "6":  ;
                    		break;
		            case "7":  ;
                    		break;
		            case "8": 	;
		            		break;    
		            case "9":  bukatu= true;
                    		break;
		            default: System.out.println("Sartu duzun aukera ez da existitzen");
		            		pressEnterToContinue();
		                     break;
		        }
		}
		
	}
	
	private static void menuaPantailaratu() {
		System.out.println("\nAUKERAK:\n");
		System.out.println("1. Fitxategietatik arff gordina atera");	//getRaw
		System.out.println("2. Arff gordina errepresentazio bektorialera pasa");
		System.out.println("3. Atributu hautapena egin");
		System.out.println("4. Parametro ekorketa egin SMV modeloa eraikitzeko");
		System.out.println("5. Logistic Regression sailkatzailea entrenatu");
		System.out.println("5. Support Vector Machine(SVM) sailkatzailea entrenatu");
		System.out.println("6. Testa konpatibilizatu train formatuarekin");
		System.out.println("7. Iragarpenak egin");
		System.out.println("8. Developer Mode");
		System.out.println("9. Exit");
	}
	
	
	 private static void pressEnterToContinue()
	 { 
	        System.out.println("Sakatu enter tekla jarraitzeko...");
	        try
	        {
	            System.in.read();
	        }  
	        catch(Exception e)
	        {}  
	 }
	 
	 private static void karpetaSortu(String path) {
		 File file = new File(path);

	      boolean bool = file.mkdir();
	      if(bool){
	         System.out.println("Direktorioa ongi sortu da");
	      }else{
	         System.out.println("Ez da lortu direktorioa sortzea, barkatu eragozpenak");
	      }
	 }
	 
	 private static void aukera1() throws IOException, InterruptedException {
		 System.out.println("\nSartu testu fitxategiak dauden direktorioaren path-a");
		 String directorypath = sc.next();
		 String [] parametroak = {directorypath,workspace+"/gordina.arff"};
		 
		 GetRaw.main(parametroak);
	 }
	 private static void aukera2() throws Exception {
		 String [] parametroak = {workspace+"/gordina.arff",workspace+"/rawDictionary.txt","","",workspace+"/bektoreak.arff"};
		 System.out.println("\nTFIDF erabili nahi? Bai/Ez (defektuz BoW)");
		 if (sc.next().toLowerCase().equals("bai"))	parametroak[2] = "-I";
		 System.out.println("\nNonSparse formatua erabili nahi? Bai/Ez (defektuz Sparse)");
		 if (sc.next().toLowerCase().equals("bai"))	parametroak[3] = "-N";
		 //for (String x : parametroak) System.out.println(x);	//parametroak ikusteko
		
		 TransformRaw.main(parametroak);

	 }
	 

}
