package sports.trademarket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sports.trademarket.dto.AgencyJoinDto;
import sports.trademarket.entity.Agency;
import sports.trademarket.exceptions.spring.DuplicationException;
import sports.trademarket.exceptions.spring.NoSuchDataException;
import sports.trademarket.repository.AgencyRepository;
import sports.trademarket.service.AgencyService;

import static sports.trademarket.exceptions.spring.ErrorConstants.duplicatedAgency;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AgencyServiceImpl implements AgencyService {

    private final AgencyRepository agencyRepository;

    @Override
    @Transactional
    public void registerAgency(AgencyJoinDto joinDto) {
        checkDuplicatedAgency(joinDto);
        agencyRepository.save(Agency.toEntity(joinDto));
    }

    private void checkDuplicatedAgency(AgencyJoinDto joinDto) {
        agencyRepository.findAgencyByUrl(joinDto.getHomepageUrl())
                .ifPresent(agency -> {
                    throw new DuplicationException(duplicatedAgency);
                });
    }

    @Override
    public Agency findAgencyById(Long id) {
        return agencyRepository.findById(id)
                .orElseThrow(NoSuchDataException::new);
    }
}
