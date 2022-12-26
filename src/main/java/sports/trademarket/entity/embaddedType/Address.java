package sports.trademarket.entity.embaddedType;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
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

    private Address(String city, String mainAddress, String subAddress) {
        this.city = city;
        this.mainAddress = mainAddress;
        this.subAddress = subAddress;
    }

    public static Address of(String city, String mainAddress, String subAddress) {
        return new Address(city, mainAddress, subAddress);
    }
}
