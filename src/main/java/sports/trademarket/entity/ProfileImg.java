package sports.trademarket.entity;

import lombok.Getter;
import sports.trademarket.entity.commonEntity.CommonTimeEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
@Table(name = "PROFILE_IMG")
public class ProfileImg extends CommonTimeEntity {

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

    @Column(name = "ACTIVE")
    private int active;

}
