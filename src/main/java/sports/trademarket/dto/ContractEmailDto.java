package sports.trademarket.dto;

import lombok.Getter;
import lombok.Setter;
import sports.trademarket.entity.Agent;
import sports.trademarket.entity.Contract;
import sports.trademarket.entity.enumType.ContractDivision;
import sports.trademarket.entity.enumType.CurrencyUnit;

import javax.validation.constraints.Email;

@Getter @Setter
public class ContractEmailDto {

    private String agentName;

    @Email
    private String receiverEmail;

    private Long payment;

    private Long transferFee;

    private ContractDivision contractDivision;

    private int contractYear;

    private int contractMonth;

    private CurrencyUnit currencyUnit;

    private String options;

    private ContractEmailDto(String agentName, String receiverEmail, Contract contract) {
        this.agentName = agentName;
        this.receiverEmail = receiverEmail;
        payment = contract.getPayment();
        transferFee = contract.getTransferFee();
        contractDivision = contract.getContractDivision();
        contractYear = contract.getContractYear();
        contractMonth = contract.getContractMonth();
        currencyUnit = contract.getCurrencyUnit();
        options = contract.getOptions();
    }

    public static ContractEmailDto emailDto(String agentName, String receiverEmail, Contract contract) {
        return new ContractEmailDto(agentName, receiverEmail, contract);
    }
}
