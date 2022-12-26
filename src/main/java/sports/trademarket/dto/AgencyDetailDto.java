package sports.trademarket.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import sports.trademarket.entity.Agency;
import sports.trademarket.entity.enumType.CompanyType;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@Getter @Setter
public class AgencyDetailDto {

    private String ceoName;
    private CompanyType companyType;
    private int career;
    private String city;
    private String mainAddress;
    @JsonInclude(Include.NON_NULL)
    private String subAddress;
    private String homepageUrl;
    private int active;

    private AgencyDetailDto(Agency agency) {
        ceoName = agency.getCeoName();
        companyType = agency.getCompanyType();
        career = agency.getCareer();
        city = agency.getAddress().getCity();
        mainAddress = agency.getAddress().getMainAddress();
        subAddress = agency.getAddress().getSubAddress();
        homepageUrl  = agency.getHomepageUrl();
        active = agency.getActive();
    }

    public static AgencyDetailDto toDto(Agency agency) {
        return new AgencyDetailDto(agency);
    }

}
