package sports.trademarket.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sports.trademarket.entity.commonEntity.CommonTimeEntity;
import sports.trademarket.entity.enumType.ContractStatus;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "OFFER")
public class Offer extends CommonTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "OFFER_ID")
    private Long offerId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "AGENT_ID")
    private Agent agent;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PLAYER_ID")
    private Player player;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "CONTRACT_ID")
    private Contract contract;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONTRACT_STATUS")
    private ContractStatus contractStatus;

}
