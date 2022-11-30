package sports.trademarket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sports.trademarket.entity.enumType.ContractDivision;
import sports.trademarket.entity.enumType.CurrencyUnit;
import sports.trademarket.entity.enumType.SkillLevel;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRegisterDto {

    private Long agentId;
    private Long teamId;
    private String fifaRegNum;
    private String name;
    private int age;
    private String preferFoot;
    private SkillLevel skillLevel;
    private int goal;
    private int assist;
    private int clear;
    private int cleanSheet;
    private Long positionId;
    private Long transferFee;
    private Long currentPayment;
    private Long expectedPayment;
    private CurrencyUnit unit;
    private ContractDivision division;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate contractExpiryDt;

}
