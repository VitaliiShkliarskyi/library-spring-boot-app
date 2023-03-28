package library.dto.response;

import lombok.Data;

@Data
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private Long userId;
    private boolean taken;
}
