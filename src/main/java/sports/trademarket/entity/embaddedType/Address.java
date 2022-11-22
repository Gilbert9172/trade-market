package sports.trademarket.entity.embaddedType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Address {

    @Column(name = "CITY")
    private String city;

    @Column(name = "MAIN_ADDRESS")
    private String mainAddress;

    @Column(name = "SUB_ADDRESS")
    private String subAddress;

    public String fullAddress() {
        return city + " " + mainAddress + " " + subAddress;
    }
}
