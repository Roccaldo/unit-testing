package co.develhope.unittesting;

import co.develhope.unittesting.controllers.UserController;
import co.develhope.unittesting.entities.User;
import co.develhope.unittesting.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@WebMvcTest(UserController.class)
class UnitTestingApplicationTests {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void createUserTest() throws Exception {
//        MvcResult result = createAUser();
//
//
//        String userJSON = objectMapper.writeValueAsString(result);
//
//        this.mockMvc.perform(post("/user/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//
//        User userResponse = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
//        assertThat(userResponse.getName()).contains("Franco");
//    }

    @Test
    public void allUser() throws Exception {

        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "Rocco", "Ginnasio", "Rocco@gmail.com", 30));
        userList.add(new User(10L, "Bob", "Marley", "Bob@gmail.com", 57));

        given(userService.findAll()).willReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Rocco"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Bob"));
    }

//    @Test
//    void modifyStudent() throws Exception {
//        User user = new User(10L, "Fabio", "Lanterna", "fabio@gmail.com", 22);
//        user.setName("Manuel");
//        String userJSON = objectMapper.writeValueAsString(user);
//
//        MvcResult result = this.mockMvc.perform(put("/user/modify/" + user.getId()))
//                .andDo(print())
//                .andReturn();
//
//        assertThat(user.getName()).isEqualTo("Manuel");
//    }
}
