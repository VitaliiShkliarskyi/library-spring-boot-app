package library.dto.request;

import javax.validation.constraints.NotNull;
import library.lib.ValidPhoneNumber;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotNull(message = "Username cannot be null")
    private String name;
    @ValidPhoneNumber
    private String phoneNumber;
}
