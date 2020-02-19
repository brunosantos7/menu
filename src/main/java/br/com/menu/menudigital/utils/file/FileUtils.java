package br.com.menu.menudigital.utils.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	
	public static String saveOnDisk(MultipartFile file, Long restaurantId) throws IOException {
		
		Path path = Paths.get(String.format("images/%s", restaurantId));

		try {
			Files.createDirectories(path);
			
			String filename = file.getOriginalFilename();

			String completeFilename = StringUtils.cleanPath(filename.substring(0, filename.indexOf('.')) + "-" + Instant.now().getNano() + ".png");

			Files.copy(file.getInputStream(), path.resolve(completeFilename));
			return path.resolve(completeFilename).toString();

		} catch (IOException e) {
			throw new IOException("Erro ao salvar imagem no disco.", e);
		}
	}
	
	public static void deleteFromDisk(String filePath) throws IOException {

			Path path = Paths.get(filePath);
		
			if (path.toFile().exists()) {
				FileSystemUtils.deleteRecursively(path);
			}
		
	}

}
