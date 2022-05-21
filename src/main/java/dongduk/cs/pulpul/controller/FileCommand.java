package dongduk.cs.pulpul.controller;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileCommand {
	String path;
	MultipartFile file;
	MultipartFile[] files;
}
