package sports.trademarket.dto;

import lombok.Getter;
import lombok.Setter;
import sports.trademarket.entity.Offer;
import sports.trademarket.entity.enumType.CurrencyUnit;

@Getter @Setter
public class OfferDto {

    private Long contractId;
    private String agentName;
    private String playerName;
    private Long transferFee;
    private Long payment;
    private CurrencyUnit unit;
    private int contractYear;
    private int contractMonth;
    private String options;

    private OfferDto(Offer offer) {
        contractId = offer.getContract().getContractId();
        agentName = offer.getAgent().getAgentName();
        playerName = offer.getPlayer().getName();
        transferFee = offer.getContract().getTransferFee();
        payment = offer.getContract().getPayment();
        unit = offer.getContract().getCurrencyUnit();
        contractYear = offer.getContract().getContractYear();
        contractMonth = offer.getContract().getContractMonth();
        options = offer.getContract().getOptions();
    }

    public static OfferDto toDto(Offer offer) {
        return new OfferDto(offer);
    }

}
