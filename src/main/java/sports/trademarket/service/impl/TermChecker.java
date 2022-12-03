package sports.trademarket.service.impl;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.lang.Math.*;

@Getter
@Component
@RequiredArgsConstructor
public class TermChecker {

    public int getTerm(LocalDateTime modifiedDt, LocalDateTime currentDt) {
        return (int) round(getSeconds(modifiedDt, currentDt) * 100) / 100;
    }

    private double getSeconds(LocalDateTime modifiedDt, LocalDateTime currentDt) {
        return ChronoUnit.SECONDS.between(modifiedDt, currentDt) / 86400.0;
    }
}