package sports.trademarket.entity.embaddedType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sports.trademarket.entity.enumType.ContractDivision;
import sports.trademarket.entity.enumType.CurrencyUnit;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Embeddable
@AllArgsConstructor
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
    private ContractDivision contractDivision;

    @Column(name = "CONTRACT_EXPIRY_DT")
    private LocalDateTime contractExpiryDt;

}
