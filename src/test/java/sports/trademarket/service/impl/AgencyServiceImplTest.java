package sports.trademarket.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sports.trademarket.entity.Agency;
import sports.trademarket.entity.embaddedType.Address;
import sports.trademarket.entity.enumType.CompanyType;
import sports.trademarket.repository.AgencyRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AgencyServiceImplTest {

    @Mock
    private AgencyRepository agencyRepository;

    @InjectMocks
    private AgencyServiceImpl agencyServiceImpl;

    @Test
    @DisplayName("Agency 등록")
    void agencyRegisterTest() throws Exception {

        Address address = new Address("Seoul", "nowon", "lotter");

        Agency agency = Agency.builder()
                .ceoName("Gilbert")
                .companyType(CompanyType.MID)
                .address(address)
                .career(10).build();

        //given
        given(agencyRepository.save(agency)).willReturn(agency);

        // when
        Agency registerAgency = agencyServiceImpl.registerAgency(agency);

        // then
        assertThat(agency.getCeoName()).isEqualTo(registerAgency.getCeoName());
    }

    @Test
    @DisplayName("Agency 상세 조회")
    void findAgencyById() throws Exception {
        Address address = new Address("seoul", "nowon", "lotteAPT");
        Agency agency = Agency.builder()
                .ceoName("gilbert")
                .companyType(CompanyType.MID)
                .address(address)
                .homepageUrl("test@naver.com")
                .career(10).build();

        //given
        given(agencyRepository.findById(1L)).willReturn(Optional.of(agency));

        //when
        Agency findAgency = agencyServiceImpl.findAgencyById(1L);

        //then
        assertThat(agency.getCeoName()).isEqualTo(findAgency.getCeoName());

    }


}