package sports.trademarket.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sports.trademarket.entity.enumType.ContractDivision;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
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

    @Lob
    @Column(name = "OPTIONS")
    private String options;
}
