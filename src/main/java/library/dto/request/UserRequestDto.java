package library.dto.request;

import java.util.List;
import library.lib.ValidPhoneNumber;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class UserRequestDto {
    @NotNull(message = "Username cannot be null")
    private String name;
    @ValidPhoneNumber
    private String phoneNumber;
    private List<Long> bookIds;
}
