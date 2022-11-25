package sports.trademarket.utililty;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    public static void save(String folderName, String fileName, MultipartFile file) throws IOException {

        makeFolder(folderName);

        File fullFile = new File(folderName + File.separator + fileName);
        file.transferTo(fullFile);
    }

    public static void makeFolder(String path) {
        File folder = new File(path);
        if (!folder.exists()) folder.mkdirs();
    }

    public static String generateServerFileNm(String fileName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(new Date()) + extractExt(fileName);
    }

    public static String extractExt(String fileName) {
        int ext = fileName.lastIndexOf(".");
        return fileName.substring(ext).toLowerCase();
    }
}
