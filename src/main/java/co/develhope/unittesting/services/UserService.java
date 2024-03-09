package co.develhope.unittesting.services;


import co.develhope.unittesting.entities.User;
import co.develhope.unittesting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public User create(User user) {
        User createdUser = repo.save(user);
        if (createdUser.getId() == null) {
            return null;
        } else {
            return createdUser;
        }
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public Optional<User> modify(Long id, User user) {
        Optional<User> foundUser = repo.findById(id);
        if (foundUser.isPresent()) {
            foundUser.get().setName(user.getName());
            foundUser.get().setSurname(user.getSurname());
            foundUser.get().setEmail(user.getEmail());
            foundUser.get().setAge(user.getAge());
            repo.save(foundUser.get());
        } else {
            return Optional.empty();
        }
        return foundUser;
    }

    public User delete(Long id) {
        Optional<User> foundUser = repo.findById(id);
        if (foundUser.isPresent()) {
            repo.delete(foundUser.get());
            return foundUser.get();
        } else {
            return null;
        }
    }

    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }
}
