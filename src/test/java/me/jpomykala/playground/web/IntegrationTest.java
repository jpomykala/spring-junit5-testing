package me.jpomykala.playground.web;

import me.jpomykala.playground.web.dto.Dog;
import me.jpomykala.playground.web.dto.DogForm;
import me.jpomykala.playground.web.dto.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @LocalServerPort
  private int port;

  @AfterEach
  public void tearDown() {
    JdbcTestUtils.deleteFromTables(jdbcTemplate, "dog");
  }

  @Test
  public void createDog() {
    //given
    DogForm dogForm = new DogForm("Test", 10, Gender.MAN);

    //when
    String url = "http://localhost:" + port + "/dogs";
    ResponseEntity<String> response = testRestTemplate.postForEntity(url, dogForm, String.class);

    //then
    int countRowsInTable = JdbcTestUtils.countRowsInTable(jdbcTemplate, "dog");
    assertThat(countRowsInTable).isEqualTo(1);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  @Test
  @Sql("classpath:insert-dogs.sql")
  public void findDogs() {
    //when
    String dogName = "craig";
    String url = "http://localhost:" + port + "/dogs?name=" + dogName;
    ResponseEntity<Dog[]> response = testRestTemplate.exchange(url, HttpMethod.GET, null, Dog[].class);

    //then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(1);
    assertThat(response.getBody()[0]).isNotNull();
    assertThat(response.getBody()[0].getName()).containsIgnoringCase(dogName);
  }

}
