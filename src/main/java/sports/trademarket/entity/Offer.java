package sports.trademarket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sports.trademarket.entity.commonEntity.CommonTimeEntity;
import sports.trademarket.entity.enumType.ContractStatus;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static sports.trademarket.entity.enumType.ContractStatus.NEGOTIATING;

@Entity
@Getter @Builder
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

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "CONTRACT_ID")
    private Contract contract;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONTRACT_STATUS")
    private ContractStatus contractStatus;

    public Offer(LocalDateTime createdDt, LocalDateTime modifiedDt, Offer offer) {
        super(createdDt, modifiedDt);
        this.offerId = offer.getOfferId();
        this.agent = offer.getAgent();
        this.player = offer.getPlayer();
        this.contract = offer.getContract();
        this.contractStatus = offer.getContractStatus();
    }

    public void updateOffer(Contract newCond) {
        this.contract = newCond;
    }

    public static Offer createOffer(Agent agent, Player player, Contract contract) {
        return Offer.builder()
                .agent(agent).player(player).contract(contract)
                .contractStatus(NEGOTIATING).build();
    }
}
