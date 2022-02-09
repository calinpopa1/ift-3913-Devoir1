import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        
        
        

        /*System.out.println(LineCount.getNumberOfLines("C:/Users/calin/OneDrive/Desktop/javatest.java"));

        

        System.out.println(LineCount.getNumberOfCommentLines("C:/Users/calin/OneDrive/Desktop/javatest.java"));

        

        System.out.println(LineCount.getClassCommentDensity("C:/Users/calin/OneDrive/Desktop/javatest.java"));


        System.out.println(LineCount.getNumberOfLinesPackage("C:/Users/calin/OneDrive/Desktop/foldertest"));
        
        
        System.out.println(LineCount.getNumberOfCommentLinesPackage("C:/Users/calin/OneDrive/Desktop/foldertest"));


        System.out.println(LineCount.getPackageCommentDensity("C:/Users/calin/OneDrive/Desktop/foldertest"));


        */
    	
    	Sorties.exitFile("C:\\Users\\dinak\\eclipse-workspace\\TP1_IFT3913\\foldertest\\javatest1.java", "C:\\Users\\dinak\\OneDrive\\Bureau\\classes.csv", true);
    	
    	Sorties.exitFile("C:\\Users\\dinak\\eclipse-workspace\\TP1_IFT3913\\foldertest", "C:\\Users\\dinak\\OneDrive\\Bureau\\paquets.csv", false);
        
    	System.out.println(LineCount.getWCP("C:\\Users\\dinak\\eclipse-workspace\\TP1_IFT3913\\foldertest"));
    	
    	System.out.println("classe_BC "+LineCount.getClasse_BC("C:\\Users\\dinak\\eclipse-workspace\\TP1_IFT3913\\foldertest\\javatest1.java"));
    	
    	System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\dinak\\eclipse-workspace\\TP1_IFT3913\\foldertest"));


    }
}
