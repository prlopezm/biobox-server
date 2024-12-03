package mx.com.tecnetia.orthogonal.utils.files;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public interface FisicalFileService {
	
    String save(@NotNull MultipartFile file, @NotEmpty String uploadPath);
    String save(@NotNull byte[] file, @NotEmpty String fileName, @NotEmpty String uploadPath);
    
}
