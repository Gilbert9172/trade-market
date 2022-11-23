package sports.trademarket.service;

import sports.trademarket.entity.Agency;

public interface AgencyService {

    Agency registerAgency(Agency agency);

    Agency findAgencyById(Long id);

}
