package sports.trademarket.entity;

import lombok.*;
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

    public Agency(String ceoName) {
        this.ceoName = ceoName;
    }

    //    @OneToMany(mappedBy = "agency")
//    private List<Agent> agents = new ArrayList<>();

//    @OneToMany(mappedBy = "agent")
//    private List<Player> players = new ArrayList<>();
}
