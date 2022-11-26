package sports.trademarket.service;

import sports.trademarket.dto.AgencyJoinDto;
import sports.trademarket.entity.Agency;

public interface AgencyService {

    void registerAgency(AgencyJoinDto joinDto);

    Agency findAgencyById(Long id);

}
