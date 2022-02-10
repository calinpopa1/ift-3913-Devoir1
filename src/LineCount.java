import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

 /**
 * @author Dina KADA, Calin POPA
 * Class calculating the different metrics
 */
public class LineCount {
	/**
	 * Counts the number of lines of the location
	 * @param location : file or package with the path location
	 * @return the number of lines of the location
	 * @throws IOException : exception in case there are issues with the reader
	 */
	public static float getNumberOfLines(String location)
			throws IOException {
		try (BufferedReader bRead = new BufferedReader(new FileReader(location))) {
			float count = 0;
			boolean commBegan = false;
			String line = null;

			while ((line = bRead.readLine()) != null) {
				line = line.trim();
				if ("".equals(line) || line.startsWith("//")) {
					continue;
				}
				if (commBegan) {
					if (commentEnded(line)) {
						line = line.substring(line.indexOf("*/") + 2).trim();
						commBegan = false;
						if ("".equals(line) || line.startsWith("//")) {
							continue;
						}
					} else
						continue;
				}
				
				count++;
				
				if (commentBegan(line)) {
					commBegan = true;
				}
			}
			return count;
		}
	}
	
	
	/**
	 * Checks whether or not a comment has begun in the line line
	 * @param line : line to check
	 * @return true if a comment has begun, else false
	 */
	private static boolean commentBegan(String line) {
		// Si line = /* */, la methode retourne faux
		// Si line = /* */ /*, la methode retourne vrai
		int i = line.indexOf("/*");
		if (i < 0) {
			return false;
		}
		int startIndex = line.indexOf("\"");
		if (startIndex != -1 && startIndex < i) {
			while (startIndex > -1) {
				line = line.substring(startIndex + 1);
				int endIndex = line.indexOf("\"");
				line = line.substring(endIndex + 1);
				startIndex = line.indexOf("\"");
			}
			return commentBegan(line);
		}
		return !commentEnded(line.substring(i + 2));
	}
	
	
	/**
	 * Checks whether or not a comment has ended in the line
	 * @param line : line to check
	 * @return true if a comment has ended, else false
	 */
	private static boolean commentEnded(String line) {
		// Si line = */ /* , la méthode retourne faux.
		// Si line = */ /* */, la méthode retourne vrai.
		int i = line.indexOf("*/");
		if (i < 0) {
			return false;
		} else {
			String subStr = line.substring(i + 2).trim();
			if ("".equals(subStr) || subStr.startsWith("//")) {
				return true;
			}
			if(commentBegan(subStr))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}

	
	/**
	 * Counts the number of comment lines in the location
	 * @param location : path of the file/package to check
	 * @return the number of comment lines in the location
	 * @throws FileNotFoundException : in case there is an error with the file with the path location 
	 */
	public static float getNumberOfCommentLines(String location) throws FileNotFoundException{
		BufferedReader bRead= new BufferedReader(new FileReader(location));
		String line = "";
		float count = 0;
		try {
        
			while ((line = bRead.readLine()) != null) {
			
				if (line.contains("//")) {
					count++;
				} else if (line.contains("/*")) {
					while (!line.contains("*/") && !(line = bRead.readLine()).contains("*/")){
						count++;
					};
				}
			}
			bRead.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return count;
	}

	/**
	 * Calculates the density of comments in the class
	 * @param location : path of the class to check
	 * @return the density of comments in the class
	 * @throws IOException : in case there is an error with the methods used
	 */
	public static float getClassCommentDensity(String location) throws IOException {

		float CommsC= getNumberOfCommentLines(location);
		float LinesC= getNumberOfLines(location);
		float densite= CommsC/LinesC;
		return densite;
	}

	
	/**
	 * Calculates the number of lines in the package located in location
	 * @param location : path of the package
	 * @return the number of lines in the package
	 * @throws IOException : in case there is an error with the method used
	 */
	public static float getNumberOfLinesPackage(String location) throws IOException{
		Path path=Paths.get(location);
		float count=0;
		try(Stream<Path> subPaths=Files.walk(path)){
			
			List<String> subPathList=subPaths.filter(Files::isRegularFile)
				.map(Objects::toString)
				.collect(Collectors.toList());

			for(int i=0;i<subPathList.size();i++){
				count += getNumberOfLines(subPathList.get(i));
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		return count;

	}


	/**
	 * Calculates the number of comment lines in the package location
	 * @param location : path of the package to check
	 * @return the number of comment lines in the package
	 * @throws IOException : in case there is an error with the method used
	 */
	public static float getNumberOfCommentLinesPackage(String location) throws IOException{
		Path path=Paths.get(location);
		float count=0;
		try(Stream<Path> subPaths=Files.walk(path)){
			
			List<String> subPathList=subPaths.filter(Files::isRegularFile)
				.map(Objects::toString)
				.collect(Collectors.toList());

			for(int i=0;i<subPathList.size();i++){
				count += getNumberOfCommentLines(subPathList.get(i));
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		return count;

	}


	/**
	 * Calculates the density of comments in the package location
	 * @param location : path of the package
	 * @return the density of comments in the package
	 * @throws IOException : in case there is an error with the methods used
	 */
	public static float getPackageCommentDensity(String location) throws IOException {

		float CommsP= getNumberOfCommentLinesPackage(location);
		float LinesP= getNumberOfLinesPackage(location);
		float densite= CommsP/LinesP;
	
		return densite;
	}
	
	/**
	 * Calculates the Weighted Methods per Class for the class located in path
	 * @param path : location of the class to check
	 * @return the WMC of the class
	 */
	public static int getWMC(String path) {
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
								line=br.readLine();
								continue;
							}
						}
					}
					if(line.contains("if")||line.contains("for")||line.contains("while")||line.contains("switch")) {
						if(line.contains("(")&&line.contains(")")) {
							total++;
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

	
	/**
	 * Calculates the Weighted Classes per Package of the package located at location
	 * @param location : path of the package
	 * @return the WCP of the package
	 * @throws IOException : in case there is an error with the methods used
	 */
	public static float getWCP(String location) throws IOException{
		Path path=Paths.get(location);
		float count=0;
		try(Stream<Path> subPaths=Files.walk(path)){
			
			List<String> subPathList=subPaths.filter(Files::isRegularFile)
				.map(Objects::toString)
				.collect(Collectors.toList());

			for(int i=0;i<subPathList.size();i++){
				count += getWMC(subPathList.get(i));
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		return count;

	}

	
	/**
	 * Calculates the BC for the class located at location
	 * @param location : path of the class to check
	 * @return the BC for the class
	 * @throws IOException : in case there is an error with the methods used
	 */
	public static float getClasse_BC(String location) throws IOException {

		float classe_DC= getClassCommentDensity(location);
		float WMC= getWMC(location);
		float degre= classe_DC/WMC;
	
		return degre;
	}

	
	/**
	 * Calculates the BC for the package located at location
	 * @param location : path of the package to check
	 * @return the BC for the package
	 * @throws IOException : in case there is an error with the methods used
	 */
	public static float getPaquet_BC(String location) throws IOException{
		float paquet_DC=getPackageCommentDensity(location);
		float WCP= getWCP(location);
		float degre= paquet_DC/WCP;

		return degre;
	}
	








}