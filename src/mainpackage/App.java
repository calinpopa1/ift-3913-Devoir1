package mainpackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Dina KADA, Calin POPA
 * Class used to test the functions
 */
public class App {
    /**
     * Main method
     * @param args : arguments of the command line
     * @throws Exception : many exceptions
     */
    public static void main(String[] args) throws Exception {
        if(args.length!=2) {
        	System.out.println("Arguments = [chemin entrée] [chemin sortie]");
        }
        
        if(args[0].contains(".java")) {
        	Sorties.exitFile(args[0], args[1]+"classes.csv", true);
        }else {
        	Sorties.exitFile(args[0], args[1]+"paquets.csv", false);
        }
        /*
        System.out.println(LineCount.getNumberOfLines("C:/Users/calin/OneDrive/Desktop/javatest.java"));

        

        System.out.println(LineCount.getNumberOfCommentLines("C:/Users/calin/OneDrive/Desktop/javatest.java"));

        

        System.out.println(LineCount.getClassCommentDensity("C:/Users/calin/OneDrive/Desktop/javatest.java"));


        System.out.println(LineCount.getNumberOfLinesPackage("C:/Users/calin/OneDrive/Desktop/foldertest"));
        
        
        System.out.println(LineCount.getNumberOfCommentLinesPackage("C:/Users/calin/OneDrive/Desktop/foldertest"));


        System.out.println(LineCount.getPackageCommentDensity("C:/Users/calin/OneDrive/Desktop/foldertest"));
        
        
        
    	
    	Sorties.exitFile("C:\\Users\\dinak\\eclipse-workspace\\TP1_IFT3913\\foldertest\\javatest1.java", "C:\\Users\\dinak\\OneDrive\\Bureau\\classes.csv", true);
    	
    	Sorties.exitFile("C:\\Users\\dinak\\eclipse-workspace\\TP1_IFT3913\\foldertest", "C:\\Users\\dinak\\OneDrive\\Bureau\\paquets.csv", false);
        
    	System.out.println(LineCount.getWCP("C:\\Users\\dinak\\eclipse-workspace\\TP1_IFT3913\\foldertest"));
    	
    	System.out.println("classe_BC "+LineCount.getClasse_BC("C:\\Users\\dinak\\eclipse-workspace\\TP1_IFT3913\\foldertest\\javatest1.java"));
    	
    	System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\dinak\\eclipse-workspace\\TP1_IFT3913\\foldertest"));
        
        

        
        
        System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\annotations"));
    
        System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\api"));
        
        System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\axis"));
    
        System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\block"));
    
        System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\date"));

        System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\encoders"));

        System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\entity"));
    
        System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\event"));
    
        System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\imagemap"));
    
        System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\internal"));
    
        System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\labels"));
    
        System.out.println("paquet_BC "+LineCount.getPaquet_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\legend")+"\n");
    
    
        

        System.out.println("classe_BC "+LineCount.getClasse_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\ChartColor.java"));
    
        System.out.println("classe_BC "+LineCount.getClasse_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\ChartElement.java"));
        
        System.out.println("classe_BC "+LineCount.getClasse_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\ChartElementVisitor.java"));
    
        System.out.println("classe_BC "+LineCount.getClasse_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\ChartFactory.java"));
    
        System.out.println("classe_BC "+LineCount.getClasse_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\ChartHints.java"));

        System.out.println("classe_BC "+LineCount.getClasse_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\ChartRenderingInfo.java"));

        System.out.println("classe_BC "+LineCount.getClasse_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\ChartTheme.java"));
    
        System.out.println("classe_BC "+LineCount.getClasse_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\ChartUtils.java"));
    
        System.out.println("classe_BC "+LineCount.getClasse_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\Drawable.java"));
    
        System.out.println("classe_BC "+LineCount.getClasse_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\JFreeChart.java"));
    
        System.out.println("classe_BC "+LineCount.getClasse_BC("C:\\Users\\calin\\OneDrive\\Desktop\\ifreechart-src\\main\\java\\org\\jfree\\chart\\package-info.java"));
        */
        
        
    
    }
}
