package sports.trademarket.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import sports.trademarket.dto.AgencyJoinDto;
import sports.trademarket.entity.Agency;
import sports.trademarket.entity.embaddedType.Address;
import sports.trademarket.exceptions.spring.DuplicationException;
import sports.trademarket.repository.AgencyRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static sports.trademarket.entity.enumType.CompanyType.*;

@ExtendWith(MockitoExtension.class)
class AgencyServiceImplTest {

    @Mock
    private AgencyRepository agencyRepository;

    @Spy
    @InjectMocks
    private AgencyServiceImpl agencyServiceImpl;

    private AgencyJoinDto joinDto;
    private Agency agency;

    @BeforeEach
    void setTestData() {

        Address address = Address.of("seoul", "nowon", "lotteAPT");

        joinDto = new AgencyJoinDto(
                "참좋은 에이전시1",
                "토마스 킴",
                MID,
                "경기도",
                "의정부",
                "의정부역 1번 출구",
                "ChamJoeun@naver.com",
                7
        );

        agency = Agency.builder()
                .agencyId(1L)
                .agencyName("참좋은 에이전씨")
                .ceoName("gilbert")
                .companyType(MID)
                .address(address)
                .homepageUrl("test@naver.com")
                .career(10)
                .active(1).build();
    }

    @Test
    @DisplayName("Agency 등록")
    void agencyRegisterTest() throws Exception {
        //given
        given(agencyRepository.save(any())).willReturn(agency);

        // when
        agencyServiceImpl.registerAgency(joinDto);

        // then
         verify(agencyServiceImpl, times(1)).registerAgency(joinDto);
    }

    @Test
    @DisplayName("Agency 상세 조회")
    void findAgencyById() throws Exception {
        //given
        given(agencyRepository.findById(1L)).willReturn(Optional.of(agency));

        //when
        Agency findAgency = agencyServiceImpl.findAgencyById(agency.getAgencyId());

        //then
        assertThat(agency.getCeoName()).isEqualTo(findAgency.getCeoName());
    }

    @Test
    @DisplayName("Agency 중복 O")
    void duplicatedAgency() throws Exception {
        given(agencyRepository.findAgencyByUrl(anyString())).willThrow(DuplicationException.class);

        assertThatThrownBy(() -> agencyServiceImpl.registerAgency(joinDto))
                .isInstanceOf(DuplicationException.class);
    }

    @Test
    @DisplayName("Agency 중복 X")
    void notDuplicatedAgency() throws Exception {
        //given
        given(agencyRepository.save(any())).willReturn(agency);
        given(agencyRepository.findAgencyByUrl(anyString())).willReturn(Optional.empty());

        //when
        agencyServiceImpl.registerAgency(joinDto);

        //then
        verify(agencyServiceImpl, times(1)).registerAgency(joinDto);
    }
}