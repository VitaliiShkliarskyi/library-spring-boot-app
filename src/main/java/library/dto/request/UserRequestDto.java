package library.dto.request;

import library.lib.ValidPhoneNumber;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class UserRequestDto {
    @NotNull(message = "Username cannot be null")
    private String name;
    @ValidPhoneNumber
    private String phoneNumber;
}
