package library.util;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

class SortingServiceImplTest {
    private static SortingService sortingService;

    @BeforeAll
    static void beforeAll() {
        sortingService = new SortingServiceImpl();
    }

    @Test
    void createSortObject_Ok() {
        String sortBy = "name:DESC;phoneNumber:ASC;";
        Sort actual = sortingService.create(sortBy);
        List<Sort.Order> orders = actual.toList();
        Assertions.assertNotNull(actual);
        Assertions.assertFalse(actual.isEmpty());
        Assertions.assertEquals(2, orders.size());
        Assertions.assertEquals(Sort.Direction.DESC, orders.get(0).getDirection());
        Assertions.assertEquals("name", orders.get(0).getProperty());
        Assertions.assertEquals(Sort.Direction.ASC, orders.get(1).getDirection());
        Assertions.assertEquals("phoneNumber", orders.get(1).getProperty());
    }
}
