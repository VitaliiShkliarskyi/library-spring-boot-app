package library.service;

import java.util.List;
import library.model.User;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User add(User user);

    List<User> getAll();

    List<User> getAll(Pageable pageable);

    User get(Long id);

    void delete(Long id);

    User update(User user);
}
