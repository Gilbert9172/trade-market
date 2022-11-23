package sports.trademarket.entity;

import lombok.*;
import sports.trademarket.entity.commonEntity.CommonTimeEntity;
import sports.trademarket.entity.embaddedType.Address;
import sports.trademarket.entity.enumType.CompanyType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @NotEmpty
    @Column(name = "CEO_NAME")
    public String ceoName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "COMPANY_TYPE")
    public CompanyType companyType;

    @Column(name = "CAREER")
    public int career;

    @NotNull
    @Embedded
    public Address address;

    @Column(name = "HOMEPAGE_URL")
    public String homepageUrl;

    @Column(name = "ACTIVE")
    @Builder.Default
    private int active = 1;

    //    @OneToMany(mappedBy = "agency")
//    private List<Agent> agents = new ArrayList<>();

//    @OneToMany(mappedBy = "agent")
//    private List<Player> players = new ArrayList<>();
}
