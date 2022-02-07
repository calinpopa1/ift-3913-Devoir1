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

/**rth
 * This class  counts the number of source code lines by excluding comments, in a Java file
 * The pseudocode is as below
 * 
 * Initial: Set count = 0, commentBegan = false
 * Start: Read line
 * Begin: If line is not null, goto Check, else goto End
 * Check: If line is a trivial line(after trimming, either begins with // or is ""), goto Start
 *        If commentBegan = true
 *             if comment has not ended in line 
 *                goto Start
 *              else 
 *                line = what remains in the line after comment ends
 *                commenBegan = false
 *                if line is trivial
 *                   goto Start
 * 		  If line is a valid source code line, count++
 *        If comment has begun in the line, set commentBegan = true
 *        goto Start
 * End: print count
 */

 
public class LineCount {
	public static float getNumberOfLines(String location)
			throws IOException {
		try (BufferedReader bReader = new BufferedReader(new FileReader(location))) {
			float count = 0;
			boolean commentBegan = false;
			String line = null;

			while ((line = bReader.readLine()) != null) {
				line = line.trim();
				if ("".equals(line) || line.startsWith("//")) {
					continue;
				}
				if (commentBegan) {
					if (commentEnded(line)) {
						line = line.substring(line.indexOf("*/") + 2).trim();
						commentBegan = false;
						if ("".equals(line) || line.startsWith("//")) {
							continue;
						}
					} else
						continue;
				}
				
				count++;
				
				if (commentBegan(line)) {
					commentBegan = true;
				}
			}
			return count;
		}
	}
	
	/**
	 * 
	 * @param line
	 * @return This method checks if in the given line a comment has begun and has not ended
	 */
	private static boolean commentBegan(String line) {
		// If line = /* */, this method will return false
		// If line = /* */ /*, this method will return true
		int index = line.indexOf("/*");
		if (index < 0) {
			return false;
		}
		int quoteStartIndex = line.indexOf("\"");
		if (quoteStartIndex != -1 && quoteStartIndex < index) {
			while (quoteStartIndex > -1) {
				line = line.substring(quoteStartIndex + 1);
				int quoteEndIndex = line.indexOf("\"");
				line = line.substring(quoteEndIndex + 1);
				quoteStartIndex = line.indexOf("\"");
			}
			return commentBegan(line);
		}
		return !commentEnded(line.substring(index + 2));
	}
	
	/**
	 * 
	 * @param line
	 * @return This method checks if in the given line a comment has ended and no new comment has not begun
	 */ 
	private static boolean commentEnded(String line) {
		// If line = */ /* , this method will return false
		// If line = */ /* */, this method will return true
		int index = line.indexOf("*/");
		if (index < 0) {
			return false;
		} else {
			String subString = line.substring(index + 2).trim();
			if ("".equals(subString) || subString.startsWith("//")) {
				return true;
			}
			if(commentBegan(subString))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}

	public static float getNumberOfCommentLines(String location) throws FileNotFoundException{
	BufferedReader bReader= new BufferedReader(new FileReader(location));
	String line = "";
    float count = 0;
    try {
        
        while ((line = bReader.readLine()) != null) {
			
			if (line.contains("//")) {
                count++;
            } else if (line.contains("/*")) {
                //count++;
                while (!line.contains("*/") && !(line = bReader.readLine()).contains("*/")){
					count++;
				};
            }
        }
        bReader.close();
    //} catch (FileNotFoundException e) {
    //    e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return count;
	}
	
	public static float getClassCommentDensity(String location) throws IOException {
		BufferedReader b1 = new BufferedReader(new FileReader(location));

		float CommsC= getNumberOfCommentLines(location);
		float LinesC= getNumberOfLines(location);
		float densite= CommsC/LinesC;
		
		
		//int lol= getNumberOfCommentLines(bReader);
		//bReader.reset();
		//int lol1 = getNumberOfLines(bReader);
		

		return densite;
	}

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

	public static float getPackageCommentDensity(String location) throws IOException {
		//BufferedReader b1 = new BufferedReader(new FileReader(location));

		float CommsP= getNumberOfCommentLinesPackage(location);
		float LinesP= getNumberOfLinesPackage(location);
		float densite= CommsP/LinesP;
		
		
		//int lol= getNumberOfCommentLines(bReader);
		//bReader.reset();
		//int lol1 = getNumberOfLines(bReader);
		

		return densite;
	}








}