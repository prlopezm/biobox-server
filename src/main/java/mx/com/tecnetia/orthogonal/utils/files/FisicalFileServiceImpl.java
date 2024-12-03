package mx.com.tecnetia.orthogonal.utils.files;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FisicalFileServiceImpl implements FisicalFileService {

    @Override
    public String save(@NotNull MultipartFile file, @NotEmpty String uploadPath) {
        var name = file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank() ? file.getOriginalFilename() : "";
        if (Objects.equals(name, "")) {
            throw new IllegalArgumentException("Falta el nombre del archivo");
        }
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
            Path resolve = root.resolve(file.getOriginalFilename().trim().replaceAll("\\s", ""));
            if (resolve.toFile().exists()) {
                throw new IllegalArgumentException("El archivo ya existe: " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), resolve, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo guardar el archivo subido. Error: " + e.getLocalizedMessage());
        }
        return uploadPath + "/" + file.getOriginalFilename();
    }

    @Override
    public String save(@NotNull byte[] file, String fileName, String uploadPath) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
            Path resolve = root.resolve(fileName.trim().replaceAll("\\s", ""));
            if (resolve.toFile().exists()) {
                throw new IllegalArgumentException("El archivo ya existe: " + fileName);
            }
            Files.copy(new ByteArrayInputStream(file), resolve, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se pudo guardar el archivo subido. Error: " + e.getLocalizedMessage());
        }
        return uploadPath + "/" + fileName;
    }
}
