package library.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRequestDto {
    @NotNull(message = "Book title cannot be null")
    private String title;
    @NotNull(message = "Book author cannot be null")
    private String author;
}
