package me.jpomykala.playground.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jpomykala.playground.web.dto.Dog;
import me.jpomykala.playground.web.dto.DogForm;
import me.jpomykala.playground.web.dto.Gender;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DogController.class)
public class DogControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @MockBean
  private DogService dogService;


  @BeforeEach
  void setUp() {
    Dog responseDog = new Dog("craig", 10);
    responseDog.setId(1L);
    when(dogService.saveDog(any(DogForm.class))).thenReturn(responseDog);
    objectMapper = new ObjectMapper();
  }

  @Test
  public void createDog_validForm_created() throws Exception {
    //given
    DogForm form = new DogForm("Adam", 10, Gender.MAN);
    String requestBodyJson = objectMapper.writeValueAsString(form);

    mockMvc.perform(
            MockMvcRequestBuilders.post("/dogs")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .content(requestBodyJson)
    )
            .andDo(MockMvcResultHandlers.log())
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().string(
                    Matchers.containsString("/dogs/1")
            ));

  }

  @Test
  public void createDog_negativeAge_badRequest() throws Exception {
    //given
    DogForm form = new DogForm("Adam", -1, Gender.MAN);
    String requestBodyJson = objectMapper.writeValueAsString(form);

    mockMvc.perform(
            MockMvcRequestBuilders.post("/dogs")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .content(requestBodyJson)
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

  }

  @Test
  public void createDog_nullName_badRequest() throws Exception {
    //given
    DogForm form = new DogForm(null, 10, Gender.MAN);
    String requestBodyJson = objectMapper.writeValueAsString(form);

    mockMvc.perform(
            MockMvcRequestBuilders.post("/dogs")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .content(requestBodyJson)
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

  }

  @Test
  public void createDog_nameNotMatchToMan_badRequest() throws Exception {
    //given
    DogForm form = new DogForm("Anna", 10, Gender.MAN);
    String requestBodyJson = objectMapper.writeValueAsString(form);

    mockMvc.perform(
            MockMvcRequestBuilders.post("/dogs")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .content(requestBodyJson)
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

  }

  @Test
  public void createDog_nameNotMatchToWoman_badRequest() throws Exception {
    //given
    DogForm form = new DogForm("Jack", 10, Gender.WOMAN);
    String requestBodyJson = objectMapper.writeValueAsString(form);

    mockMvc.perform(
            MockMvcRequestBuilders.post("/dogs")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .content(requestBodyJson)
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

  }


}
