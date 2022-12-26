package sports.trademarket.entity.embaddedType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sports.trademarket.entity.enumType.ContractDivision;
import sports.trademarket.entity.enumType.CurrencyUnit;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

import static lombok.AccessLevel.*;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ContractCondition {

    @Column(name = "TRANSFER_FEE")
    private Long transferFee;

    @Column(name = "CURRENT_PAYMENT")
    private Long currentPayment;

    @Column(name = "EXPECDTED_PAYMENT")
    private Long expectedPayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_UNIT")
    private CurrencyUnit currencyUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONTRACT_DIVISION")
    private ContractDivision contractDivision;

    @Column(name = "CONTRACT_EXPIRY_DT")
    private LocalDate contractExpiryDt;

    private ContractCondition(Long transferFee, Long currentPayment, Long expectedPayment,
                             CurrencyUnit currencyUnit, ContractDivision contractDivision,
                             LocalDate contractExpiryDt) {
        this.transferFee = transferFee;
        this.currentPayment = currentPayment;
        this.expectedPayment = expectedPayment;
        this.currencyUnit = currencyUnit;
        this.contractDivision = contractDivision;
        this.contractExpiryDt = contractExpiryDt;
    }

    public static ContractCondition allContractCond(Long transferFee, Long currentPayment, Long expectedPayment,
                                                    CurrencyUnit currencyUnit, ContractDivision contractDivision,
                                                    LocalDate contractExpiryDt) {
        return new ContractCondition(transferFee, currentPayment, expectedPayment,
                                    currencyUnit, contractDivision, contractExpiryDt);
    }
}
