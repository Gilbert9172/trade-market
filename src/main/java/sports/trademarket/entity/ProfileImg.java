package sports.trademarket.entity;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import sports.trademarket.exceptions.spring.IllegalFileNameException;
import sports.trademarket.exceptions.spring.SaveFileException;

import javax.persistence.*;

import java.io.IOException;
import java.util.Optional;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static sports.trademarket.exceptions.spring.ErrorConstants.fileSaveFail;
import static sports.trademarket.exceptions.spring.ErrorConstants.illegalFileName;
import static sports.trademarket.utililty.FileUtil.*;

@Entity
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "PROFILE_IMG")
public class ProfileImg {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "PROFILE_IMG_ID")
    private Long profileImgId;

    @Column(name = "ORIGINAL_FILE_NM")
    private String originalFileNm;

    @Column(name = "ORIGINAL_FILE_EXT")
    private String originalFileExt;

    @Column(name = "SAVED_FILE_PATH")
    private String savedFilePath;

    @Column(name = "SAVED_FILE_NM")
    private String savedFileNm;

    public String getFullImgPath() {
        return savedFilePath+"/"+savedFileNm;
    }

    public static ProfileImg saveProfileImg(MultipartFile file, String savePath) {

        String orgFileName = Optional.ofNullable(file.getOriginalFilename())
                .orElseThrow(() -> new IllegalFileNameException(illegalFileName));

        try {

            String savedFileName = generateServerFileNm(orgFileName);

            ProfileImg img = ProfileImg.builder()
                    .originalFileNm(orgFileName)
                    .originalFileExt(extractExt(orgFileName))
                    .savedFileNm(savedFileName)
                    .savedFilePath(savePath)
                    .build();

            save(savePath, savedFileName, file);
            return img;

        } catch (IOException e) {
            throw new SaveFileException(fileSaveFail);
        }
    }

}
