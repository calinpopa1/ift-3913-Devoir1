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
 * Cette méthode compte le nombre de lignes de codes d'une classe java en excluant les commentaires. Elle prend 
 * en entrée le chemin (Path) du fichier java. Cette méthode inclue les sous-méthoodes commentBegan et commentEnded
 */

 public class LineCount {
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
	
	
	//Cette méthode vérifie si un commentaire a commencé et si il n'est pas encore terminé
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
	
	
	// Cette methode vérifie si un commentaire est terminé et si il n'ya pas un autre commentaire qui a commencé.
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

	//Cette fonction vérifie chaque ligne d'une classe java et retourne le nombre
	//de commentaires de la classe. Cette méthode prend le chemin de la classe en entrée
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


	//Cette méthode retourne la densité de commentaire d'une classe.
	public static float getClassCommentDensity(String location) throws IOException {

		float CommsC= getNumberOfCommentLines(location);
		float LinesC= getNumberOfLines(location);
		float densite= CommsC/LinesC;
		return densite;
	}

	//Cette methode retourne le nombre de lignes de code d'un paquet. On doit lui donner le chemin
	//du paquet en entrée.
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

	//Cette méthode retourne le nombre de commentaires d'un paquet.
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

	//Cette méthode retourne la densité de commentaire d'un paquet.
	public static float getPackageCommentDensity(String location) throws IOException {

		float CommsP= getNumberOfCommentLinesPackage(location);
		float LinesP= getNumberOfLinesPackage(location);
		float densite= CommsP/LinesP;
	
		return densite;
	}

	
	/*
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

	public static float getClasse_BC(String location) throws IOException {

		float classe_DC= getClassCommentDensity(location);
		float WMC= getWMC(location);
		float degre= classe_DC/WMC;
	
		return degre;
	}

	public static float getPaquet_BC(String location) throws IOException{
		float paquet_DC=getPackageCommentDensity(location);
		float WCP= getWCP(location);
		float degre= paquet_DC/WCP;

		return degre;
	}
	*/








}