package library.dto.request;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class BookRequestDto {
    @NotNull(message = "Book title cannot be null")
    private String title;
    @NotNull(message = "Book author cannot be null")
    private String author;
}
