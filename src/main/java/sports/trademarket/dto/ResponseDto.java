package sports.trademarket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ResponseDto<T> {

    private int status;
    private String msg;
    private T data;

    public ResponseDto(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseDto<T> of(int status, String msg, T data) {
        return new ResponseDto<T>(status, msg, data);
    }

}
