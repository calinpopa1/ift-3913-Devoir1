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
        
        
        

        System.out.println(LineCount.getNumberOfLines("C:/Users/calin/OneDrive/Desktop/javatest.java"));

        

        System.out.println(LineCount.getNumberOfCommentLines("C:/Users/calin/OneDrive/Desktop/javatest.java"));

        

        System.out.println(LineCount.getClassCommentDensity("C:/Users/calin/OneDrive/Desktop/javatest.java"));


        System.out.println(LineCount.getNumberOfLinesPackage("C:/Users/calin/OneDrive/Desktop/foldertest"));
        
        
        System.out.println(LineCount.getNumberOfCommentLinesPackage("C:/Users/calin/OneDrive/Desktop/foldertest"));


        System.out.println(LineCount.getPackageCommentDensity("C:/Users/calin/OneDrive/Desktop/foldertest"));


        
        



    }
}
