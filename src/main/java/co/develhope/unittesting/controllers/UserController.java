package co.develhope.unittesting.controllers;

import co.develhope.unittesting.entities.User;
import co.develhope.unittesting.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        User foundUser = userService.create(user);
        if (foundUser != null) {
            return ResponseEntity.ok(foundUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> findAll() {
        List<User> foundUsers = userService.findAll();
        if (foundUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(foundUsers);
        }
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<User> modify(@PathVariable Long id, @RequestBody User user) {
        Optional<User> foundUser = userService.modify(id, user);
        if (foundUser.isPresent()) {
            return ResponseEntity.ok(foundUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {
        User foundUser = userService.delete(id);
        if (foundUser != null) {
            return ResponseEntity.ok(foundUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
