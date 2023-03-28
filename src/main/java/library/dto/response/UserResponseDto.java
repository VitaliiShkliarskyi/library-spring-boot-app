package library.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private List<Long> bookIds;
}
