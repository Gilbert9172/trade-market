package sports.trademarket.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sports.trademarket.entity.Agency;
import sports.trademarket.entity.embaddedType.Address;
import sports.trademarket.entity.enumType.CompanyType;
import sports.trademarket.exceptions.security.NoSuchUserException;
import sports.trademarket.repository.AgencyRepository;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AgencyRepositoryTest {

    @Autowired AgencyRepository agencyRepository;

    @Test
    @DisplayName("Agency 등록")
    void registerAgencyTest() throws Exception {
        //given
        Address address = Address.of("Seoul", "nowon", "lotter");

        Agency agency = Agency.builder()
                .ceoName("Gilbert")
                .companyType(CompanyType.MID)
                .address(address)
                .career(10).build();

        //when
        Agency registerAgency = agencyRepository.save(agency);
        Agency findAgency =  agencyRepository.findById(registerAgency.getAgencyId())
                .orElseThrow(NullPointerException::new);

        //then
        assertThat(agency.getCeoName()).isEqualTo(findAgency.ceoName);
        assertThat(agency.getActive()).isEqualTo(1);

    }

    @Test
    @DisplayName("Agency 상세 조회")
    void findAgencyById() throws Exception {
        //given
        Long agencyId = 1L;

        //when
        Agency findAgency =  agencyRepository.findById(agencyId)
                .orElseThrow(() -> new NoSuchUserException("no such user"));

        //then
        assertThat(findAgency.getCeoName()).isEqualTo("gilbert");
    }

}