package sports.trademarket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sports.trademarket.dto.AgencyJoinDto;
import sports.trademarket.entity.Agency;
import sports.trademarket.exceptions.spring.NoSuchDataException;
import sports.trademarket.repository.AgencyRepository;
import sports.trademarket.service.AgencyService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AgencyServiceImpl implements AgencyService {

    private final AgencyRepository agencyRepository;

    @Override
    @Transactional
    public void registerAgency(AgencyJoinDto joinDto) {
        Agency agency = Agency.toEntity(joinDto);
        agencyRepository.save(agency);
    }

    @Override
    public Agency findAgencyById(Long id) {
        return agencyRepository.findById(id)
                .orElseThrow(NoSuchDataException::new);
    }
}
