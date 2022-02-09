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
 * 
 */

/**
 * @author dinak
 *
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
				
				StringJoiner sj=new StringJoiner(""+",");
				sj.add(entryPath.toString()); //adds chemin
				sj.add(entry.getFileName().toString()); //adds class name
				sj.add(String.valueOf(cloc));
				sj.add(String.valueOf(ccloc));
				sj.add(String.valueOf(cdc));
				//add WMC
				//add BC
				
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
						//cptFile++;
						plocTot+=LineCount.getNumberOfLinesPackage(element.toString());
						pclocTot+=LineCount.getNumberOfCommentLinesPackage(element.toString());
						//add the ones from part 3
						
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
				
				StringJoiner sj=new StringJoiner(""+',');
				sj.add(entry.toString()); //adds chemin
				sj.add(entry.getFileName().toString()); //adds name (?? hopefully)
				sj.add(String.valueOf(plocTot));
				sj.add(String.valueOf(pclocTot));
				sj.add(String.valueOf(pclocTot/plocTot));
				//add the rest of the first package				
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
	
	
	/** 
	 * 
	 * @param path
	 * @param type
	 * @return
	 */
	/**
	 * @param path
	 * @param type
	 * @return
	 */
	public static int getWMC(String path, boolean type) {
		int total=0;
		Path pathArg=Paths.get(path);
		//if(type) {
			try {
				BufferedReader br=Files.newBufferedReader(pathArg);
				String line=br.readLine();
				while(line!=null) {
					if(line.contains("public")||line.contains("protected")||line.contains("private")||line.contains("static")) {
						if(line.contains("int")||line.contains("String")||line.contains("float")||line.contains("void")||line.contains("char")||line.contains("double")) {
							if(line.contains("(")&&line.contains(")")) {
								total++;
								System.out.println(line);
								line=br.readLine();
								continue;
							}
						}
					}
					if(line.contains("if")||line.contains("for")||line.contains("while")||line.contains("switch")) {
						if(line.contains("(")&&line.contains(")")) {
							total++;
							System.out.println(line);
						}
						
					}
					
					
					line=br.readLine();
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		}else {
//			try(DirectoryStream<Path> contents=Files.newDirectoryStream(pathArg)){
//				for(Path element : contents) {
//					if(!element.isAbsolute()) { //si element n'est pas juste un file
//						total+=complexiteMcCabe(element.toString(),false);
//					}else if(element.getFileName().toString().contains(".java")) { //si element est une classe, interface ou enum
//						total+=complexiteMcCabe(element.toString(),true);
//					}
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		return total;
	}
	
}
