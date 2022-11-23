package sports.trademarket.dto;

import lombok.Getter;
import lombok.Setter;
import sports.trademarket.entity.Agency;
import sports.trademarket.entity.embaddedType.Address;
import sports.trademarket.entity.enumType.CompanyType;

@Getter @Setter
public class AgencyDto {

    private String ceoName;
    private CompanyType companyType;
    private int career;
    private Address address;
    private String homepageUrl;
    private int active;

    private AgencyDto(Agency agency) {
        ceoName = agency.getCeoName();
        companyType = agency.getCompanyType();
        career = agency.getCareer();
        address = agency.getAddress();
        homepageUrl  = agency.getHomepageUrl();
        active = agency.getActive();
    }

    public static AgencyDto convert(Agency agency) {
        return new AgencyDto(agency);
    }

}
