package sports.trademarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sports.trademarket.entity.enumType.CompanyType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgencyJoinDto {

    @NotEmpty
    private String agencyName;

    @NotEmpty
    private String ceoName;

    private CompanyType companyType;

    @NotEmpty
    private String city;

    @NotEmpty
    private String mainAddress;

    private String subAddress;

    @NotEmpty
    private String homepageUrl;

    private int career;

}
