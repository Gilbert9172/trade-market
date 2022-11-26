package sports.trademarket.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

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

}
