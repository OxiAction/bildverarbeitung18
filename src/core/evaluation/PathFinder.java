package core.evaluation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * TODO description
 */
public class PathFinder {

	/**
	 * Walks through sub folders and collects all (valid) image paths (absolute paths).
	 * For different OS compatibility, the absolute path uses "/" only.
	 * 
	 * @param sourceFolder
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> getPaths(String sourceFolder) throws IOException {
		ArrayList<String> paths = new ArrayList<String>();
		String[] validExtensions = new String[] { ".jpg", ".png", ".bmp" };

		try {
			Files.walk(Paths.get(sourceFolder)).forEach(path -> {
				String pathString = path.toString();

				for (String extension : validExtensions) {
					if (pathString.contains(extension)) {
//						Debug.log("adding path: " + pathString);
						paths.add(pathString.replace('\\', '/'));
						break;
					}
				}
			});

			return paths;
		} catch (IOException e) {
			throw new IOException("IOException: " + e);
		}
	}
}
