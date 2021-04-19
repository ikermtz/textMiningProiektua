package Nagusia;

import java.io.File;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main {
	
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main (String args[]) throws Exception{
		String workspace = System.getProperty("user.home")+"/text_mining";//añadir /?
		System.out.println(workspace);//Hau ezabatu
		if (args.length != 1) {
			System.out.println("Defektuzko direktorioa erabiliko da: "+ workspace);
			System.out.println("Beste direktorioren bat nahiago izatekotan parametro gisa pasa diezaiokezu programa exekutatzerakoan");
			System.out.println("java -jar Main.jar lantokiarenHelbidea");
			pressAnyKeyToContinue();
			karpetaSortu(workspace);
		}
		
		else {
			workspace = args[0];
			System.out.println("Hurrengo direktorioa erabiliko da: "+ workspace); //pedir /?
		}
		pressAnyKeyToContinue();
		
		System.out.println("Ongi etorri!");
		pressAnyKeyToContinue();
		
		/*
		String aukera;
		
		while (true) {
			menuaPantailaratu();
			aukera=sc.next();
			
			 switch (aukera) 
		        {
		            case "1":  
		            	
		                     break;
		            case "2":  dayString = "Martes";
		                     break;
		            case "3":  dayString = "Miercoles";
		                     break;
		            case "4":  dayString = "Jueves";
		                     break;
		            case "5":  dayString = "Viernes";
		                     break;
		            default: System.out.println("");;
		                     break;
		        }
		}*/
		
	}
	
	private static void menuaPantailaratu() {
		System.out.println("Aukerak:");
		System.out.println("1. Fitxategietatik arff gordina atera");	//getRaw
		System.out.println("2. Arff gordina errepresentazio bektorialera pasa");
		System.out.println("3. Atri");
	}
	
	
	 private static void pressAnyKeyToContinue()
	 { 
	        System.out.println("Sakatu edozein tekla jarraitzeko...");
	        try
	        {
	            System.in.read();
	        }  
	        catch(Exception e)
	        {}  
	 }
	 
	 private static void karpetaSortu(String path) {
		 File file = new File(path);
	      //Creating the directory
	      boolean bool = file.mkdir();
	      if(bool){
	         System.out.println("Direktorioa ongi sortu da");
	      }else{
	         System.out.println("Ez da lortu direktorioa sortzea, barkatu eragozpenak");
	      }
	 }
	 
	 private static void aukera1() {
//		sc
//		String [] haha = {"",workspace+"/gordina.arff"}
//     	GetRaw.main(args);
	 }

}
