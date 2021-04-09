package textMiningProiektua;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.SparseToNonSparse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Locale;

public class TransformRaw {
    public static void main(String[] args) throws Exception {
        if (args.length != 5) {
            System.out.println("Ez duzu arguments atala behar bezala bete!");
            System.out.println("Erabilera:");
            System.out.println("java -jar TransformRaw.jar train.arff hiztegia IDFTF(YES/NO) TFTF(YES/NO) SPARSE(YES/NO) ");

        }else{
            /*
            for(String s : args){
                System.out.println(s);
            }
            */
            ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(args[0]); //Instantziak kargatzen ditugu
            Instances train = dataSource.getDataSet();
            train.setClassIndex(0);
            File fitxategia = new File(args[1]);  //hiztegia sortzen dugu

            StringToWordVector filter = new StringToWordVector();
            filter.setInputFormat(train);
            filter.setLowerCaseTokens(true);
            filter.setDictionaryFileToSaveTo(fitxategia); //hiztegia zein fitxategitan gordeko den zehaztu beharra dago
            filter.setIDFTransform(args[2].toLowerCase(Locale.ROOT).equals("yes")); //Si queremos usar IDFTF
            filter.setTFTransform(args[3].toLowerCase(Locale.ROOT).equals("yes"));  //si queremos usar TFTF
            train = Filter.useFilter(train,filter);

            if(args[4].toLowerCase(Locale.ROOT).equals("no")){ //si queremos que sea non sparse (diria que no aporta nada pero dar la opcion es gratis)
                SparseToNonSparse stnp = new SparseToNonSparse();
                stnp.setInputFormat(train);
                train = Filter.useFilter(train, stnp);
                BufferedWriter bw= new BufferedWriter(new FileWriter(args[1]));
                for(int i=0;i<train.numAttributes()-1;i++){ //guardamos el diccionario manualmente ya que el filtro no tiene la opcion (y por eso creo que no aporta nada)
                    Attribute a=train.attribute(i);
                    bw.newLine();
                    bw.write(a.name());
                }
                bw.flush();
                bw.close();
            }

            ArffSaver as = new ArffSaver();
            as.setInstances(train);
            as.setFile(new File("BoW"+args[0]));
            as.writeBatch();
        }
    }
}