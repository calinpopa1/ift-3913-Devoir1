import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Dina KADA, Calin POPA
 * Class handling the exit flows with the .csv
 */
public class Sorties {
	
	/**
	 * Creates a .csv exit file containing the metrics of the package/class given
	 * @param entryPath: path of the package/class to analyse
	 * @param exitPath: path of the directory where the .csv will be
	 * @param type: true if a file is given, false it's a package
	 */
	public static String exitFile(String entryPath, String exitPath, boolean type) {		
		Path entry = Paths.get(entryPath);
		Path exit = Paths.get(exitPath);
		float plocTot=0;
		float pclocTot=0;
		if(type) {
			try {
				//sortie sera vers exit et on ajoute la 1ère ligne avec un saut de ligne
				BufferedWriter writer=Files.newBufferedWriter(exit);
				writer.write("chemin, class, classe_LOC, classe_CLOC, classe_DC, WMC, classe_BC");
				writer.newLine();

				float cloc=LineCount.getNumberOfLines(entryPath);
				float ccloc=LineCount.getNumberOfCommentLines(entryPath);
				float cdc=LineCount.getClassCommentDensity(entryPath);
				int wmc=LineCount.getWMC(entryPath);
				float cbc=LineCount.getClasse_BC(entryPath);
				
				StringJoiner sj=new StringJoiner(""+",");
				sj.add(entryPath.toString()); //adds chemin
				sj.add(entry.getFileName().toString()); //adds class name
				sj.add(String.valueOf(cloc));
				sj.add(String.valueOf(ccloc));
				sj.add(String.valueOf(cdc));
				sj.add(String.valueOf(wmc));
				sj.add(String.valueOf(cbc));
				
				//writes the line in the BufferedWriter
				writer.write(sj.toString());
				
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			try(DirectoryStream<Path> contents=Files.newDirectoryStream(entry)){
				for(Path element : contents) {
					if(!element.isAbsolute()) { //si element n'est pas juste un file
						plocTot+=LineCount.getNumberOfLinesPackage(element.toString());
						pclocTot+=LineCount.getNumberOfCommentLinesPackage(element.toString());
						
					}else if(element.getFileName().toString().contains(".java")) { //si element est une classe, interface ou enum
						plocTot+=LineCount.getNumberOfLines(element.toString());
						pclocTot+=LineCount.getNumberOfCommentLines(element.toString());
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BufferedWriter writer;
			try {
				writer = Files.newBufferedWriter(exit);
				writer.write("chemin, paquet, paquet_LOC, paquet_CLOC, paquet_DC, WCP, paquet_BC");
				writer.newLine();
				
				float wcp=LineCount.getWCP(entryPath);
				float pbc=LineCount.getPaquet_BC(entryPath);
				
				StringJoiner sj=new StringJoiner(""+',');
				sj.add(entry.toString()); //adds chemin
				sj.add(entry.getFileName().toString()); //adds name
				sj.add(String.valueOf(plocTot));
				sj.add(String.valueOf(pclocTot));
				sj.add(String.valueOf(pclocTot/plocTot));
				sj.add(String.valueOf(wcp));
				sj.add(String.valueOf(pbc));			
				writer.write(sj.toString());
				writer.newLine();
				
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return null;
		
	}
	
}
