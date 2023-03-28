package library.util;

import org.springframework.data.domain.Sort;

public interface SortingService {
    Sort create(Integer page, Integer size, String sort);
}
