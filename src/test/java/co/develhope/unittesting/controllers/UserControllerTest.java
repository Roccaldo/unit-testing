package co.develhope.unittesting.controllers;

import co.develhope.unittesting.entities.User;
import co.develhope.unittesting.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void createUserTest() throws Exception {
        User user = new User(188L, "Rocco", "Ginnasio", "Rocco@gmail.com", 30);
        given(userService.create(any(User.class))).willReturn(user);

        String userJSON = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(188))
                .andExpect(jsonPath("$.name").value("Rocco"));

    }

    @Test
    public void testFindAll() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "Rocco", "Ginnasio", "Rocco@gmail.com", 30));
        userList.add(new User(10L, "Bob", "Marley", "Bob@gmail.com", 57));

        given(userService.findAll()).willReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Rocco"))
                .andExpect(jsonPath("$[1].id").value(10))
                .andExpect(jsonPath("$[1].name").value("Bob"));
    }

    @Test
    public void testModifyUser() throws Exception {
        User user = new User(1L, "Alice", "Smith", "alice@example.com", 30);

        when(userService.modify(eq(1L), any(User.class))).thenReturn(Optional.of(user));

        String requestBody = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/user/modify/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.surname").value("Smith"))
                .andExpect(jsonPath("$.email").value("alice@example.com"))
                .andExpect(jsonPath("$.age", is(30)));
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = new User(1L, "Alice", "Smith", "alice@example.com", 30);

        when(userService.delete(1L)).thenReturn(user);

        mockMvc.perform(delete("/user/del/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }
}