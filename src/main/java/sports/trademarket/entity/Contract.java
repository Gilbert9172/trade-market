package sports.trademarket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sports.trademarket.entity.enumType.ContractDivision;
import sports.trademarket.entity.enumType.CurrencyUnit;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "CONTRACT")
public class Contract {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CONTRACT_ID")
    private Long contractId;

    @Column(name = "PAYMENT")
    private Long payment;

    @Column(name = "TRANSFER_FEE")
    private Long transferFee;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONTRACT_DIVISION")
    private ContractDivision contractDivision;

    @Column(name = "CONTRACT_YEAR")
    private int contractYear;

    @Column(name = "CONTRACT_MONTH")
    private int contractMonth;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_UNIT")
    private CurrencyUnit currencyUnit;

    @Lob
    @Column(name = "OPTIONS")
    private String options;
}
