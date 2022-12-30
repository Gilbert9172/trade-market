package sports.trademarket.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sports.trademarket.entity.Agency;
import sports.trademarket.entity.embaddedType.Address;
import sports.trademarket.entity.enumType.CompanyType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@Disabled
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AgencyRepositoryTest {

    @Autowired AgencyRepository agencyRepository;

    private Agency agency;


    @BeforeEach
    void setTestData() {
        Address address = Address.of("Seoul", "nowon", "lotter");
        agency = Agency.builder()
                .agencyId(1L)
                .ceoName("Gilbert")
                .companyType(CompanyType.MID)
                .address(address)
                .homepageUrl("test@gmail.com")
                .career(10)
                .active(1)
                .build();
    }

    @Test
    @DisplayName("Agency 등록")
    void registerAgency() throws Exception {
        // given
        Agency givenAgency = agency;

        // when
        Agency registerAgency = agencyRepository.save(givenAgency);

        // then
        assertThat(agency.getCeoName()).isEqualTo(registerAgency.ceoName);
        assertThat(agency.getActive()).isEqualTo(1);

    }

    @Test
    @DisplayName("Agency 상세 조회")
    void findAgency() throws Exception {
        //given
        Agency registerAgency = agencyRepository.save(agency);

        //when
        Agency findAgency =  agencyRepository.findById(registerAgency.getAgencyId())
                .orElseThrow(NullPointerException::new);

        //then
        assertThat(agency.getCeoName()).isEqualTo(findAgency.ceoName);
        assertThat(agency.getActive()).isEqualTo(1);

    }

    @Test
    @DisplayName("Agency 중복 O")
    void duplicatedAgency() throws Exception {
        //given
        Agency registerAgency = agencyRepository.save(agency);

        //when
        Optional<Agency> findAgency = agencyRepository.findAgencyByUrl(registerAgency.getHomepageUrl());

        //then
        assertThat(findAgency).isNotEmpty();
    }

    @Test
    @DisplayName("Agency 중복 X")
    void notDuplicatedAgency() throws Exception {
        //given
        String homepageUrl = "test@naver.com";

        //when
        Optional<Agency> findAgency = agencyRepository.findAgencyByUrl(homepageUrl);

        //then
        assertThat(findAgency).isEmpty();

    }

}
