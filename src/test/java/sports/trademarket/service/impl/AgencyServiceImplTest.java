package sports.trademarket.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import sports.trademarket.dto.AgencyJoinDto;
import sports.trademarket.entity.Agency;
import sports.trademarket.entity.Agent;
import sports.trademarket.entity.ProfileImg;
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

    @Spy
    @InjectMocks
    private AgencyServiceImpl agencyServiceImpl;

    @Test
    @DisplayName("Agency 등록")
    void agencyRegisterTest() throws Exception {

        Agency agency = testAgencyData();
        AgencyJoinDto agencyJoinDto = testAgecyJoinData();

        //given
        given(agencyRepository.save(any())).willReturn(agency);

        // when
        agencyServiceImpl.registerAgency(agencyJoinDto);

        // then
         verify(agencyServiceImpl, times(1)).registerAgency(agencyJoinDto);
    }

    @Test
    @DisplayName("Agency 상세 조회")
    void findAgencyById() throws Exception {
        Agency agency = testAgencyData();

        //given
        given(agencyRepository.findById(1L)).willReturn(Optional.of(agency));

        //when
        Agency findAgency = agencyServiceImpl.findAgencyById(1L);

        //then
        assertThat(agency.getCeoName()).isEqualTo(findAgency.getCeoName());
    }

    private AgencyJoinDto testAgecyJoinData() {
        return new AgencyJoinDto(
                "참좋은 에이전시1",
                "토마스 킴",
                CompanyType.MID,
                "경기도",
                "의정부",
                "의정부역 1번 출구",
                "ChamJoeun@naver.com",
                7
        );
    }

    private Agency testAgencyData() {
        return Agency.builder()
                .agencyId(1L)
                .agencyName("참좋은 에이전씨")
                .ceoName("gilbert")
                .companyType(CompanyType.MID)
                .address(testAddressData())
                .homepageUrl("test@naver.com")
                .career(10)
                .active(1).build();
    }

    private Agency testAgencyData2() {
        return Agency.builder()
                .agencyId(2L)
                .agencyName("참좋은 에이전씨2")
                .ceoName("gilbert2")
                .companyType(CompanyType.BIG)
                .address(testAddressData())
                .homepageUrl("test2@naver.com")
                .career(20)
                .active(1).build();
    }

    private Address testAddressData() {
        return Address.of("seoul", "nowon", "lotteAPT");
    }

    private Agent testAgentData() {
        return Agent.builder()
                .agentId(1L)
                .agentName("gilbert")
                .email("gilbert@naver.com")
                .phone("01012344321")
                .profileImg(testProfileData())
                .agency(testAgencyData())
                .career(3)
                .password("hashedPassword")
                .active(1)
                .build();
    }

    private ProfileImg testProfileData() {
        return new ProfileImg(1L, "orgName",".png",
                "savedPath", "savedFileName");
    }
}