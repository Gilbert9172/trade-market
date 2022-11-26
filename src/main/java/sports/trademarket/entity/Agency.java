package sports.trademarket.entity;

import lombok.*;
import sports.trademarket.dto.AgencyJoinDto;
import sports.trademarket.entity.commonEntity.CommonTimeEntity;
import sports.trademarket.entity.embaddedType.Address;
import sports.trademarket.entity.enumType.CompanyType;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter @Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Table(name = "AGENCY")
public class Agency extends CommonTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "AGENCY_ID")
    public Long agencyId;

    @Column(name = "AGENCY_NAME")
    public String agencyName;

    @Column(name = "CEO_NAME")
    public String ceoName;

    @Enumerated(EnumType.STRING)
    @Column(name = "COMPANY_TYPE")
    public CompanyType companyType;

    @Column(name = "CAREER")
    public int career;

    @Embedded
    public Address address;

    @Column(name = "HOMEPAGE_URL")
    public String homepageUrl;

    @Column(name = "ACTIVE")
    private int active;

//    @OneToMany(mappedBy = "agency")
//    private List<Agent> agents = new ArrayList<>();

//    @OneToMany(mappedBy = "agent")
//    private List<Player> players = new ArrayList<>();

    private Agency (AgencyJoinDto joinDto) {
        agencyName = joinDto.getAgencyName();
        ceoName = joinDto.getCeoName();
        companyType = joinDto.getCompanyType();
        career = joinDto.getCareer();
        address = Address.of(joinDto.getCity(),
                joinDto.getMainAddress(), joinDto.getSubAddress());
        homepageUrl = joinDto.getHomepageUrl();
        active = 1;
    }

    public static Agency toEntity(AgencyJoinDto joinDto) {
        return new Agency(joinDto);
    }

}
