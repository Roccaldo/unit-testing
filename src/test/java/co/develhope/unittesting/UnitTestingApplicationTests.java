package co.develhope.unittesting;


import co.develhope.unittesting.controllers.UserController;
import co.develhope.unittesting.entities.User;
import co.develhope.unittesting.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitTestingApplicationTests {
    @Autowired
    private UserController studentController;

    @Autowired
    private UserService studentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void createStudent() throws Exception {
        User user = new User(1L, "Rocco", "Ginnasio", "Rocco@gmail.com", 30);
        String studentJSON = objectMapper.writeValueAsString(user);

        MvcResult resultActions = this.mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON)).andDo(print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Order(2)
    void updateStudentById() throws Exception {
        Long userId = 1L;
        User updatedStudent = new User(userId, "Alice", "Smith", "alice@example.com", 5);
        String studentJSON = objectMapper.writeValueAsString(updatedStudent);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.put("/user/modify/" + userId)
                        .contentType(MediaType.APPLICATION_JSON).content(studentJSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.surname").value("Smith"))
                .andExpect(jsonPath("$.email").value("alice@example.com"))
                .andExpect(jsonPath("$.age", is(5)))
                .andReturn();
    }

    @Test
    @Order(3)
    void getAllStudents() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/user/all"))
                .andDo(print()).andReturn();

        List<User> userFromResponseList = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(userFromResponseList.size()).isNotZero();
    }

    @Test
    @Order(4)
    void getStudent() throws Exception {
        Long studentId = 1L;
        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/user/find/" + studentId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(studentId)).andReturn();
    }

    @Test
    @Order(5)
    void deleteStudente() throws Exception {
        Long studentId = 1L;
        MvcResult result = mockMvc.perform(delete("/user/del/" + studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Order(6)
    void contextLoads() {
        assertThat(studentController).isNotNull();
    }
}
