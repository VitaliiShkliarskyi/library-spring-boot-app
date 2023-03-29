package library.service;

import java.util.List;
import library.model.User;
import library.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).toList();
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't get user by id " + id));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }
}
