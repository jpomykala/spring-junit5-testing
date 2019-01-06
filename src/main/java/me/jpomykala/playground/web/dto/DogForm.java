package me.jpomykala.playground.web.dto;


import javax.validation.constraints.*;


public class DogForm {

  @NotEmpty
  @Size(min = 2, max = 50, message = "dog name must be between 2 and 50")
  private String name;

  @Min(value = 0, message = "minimum age must be at least 0")
  @Max(30)
  private int age;

  @NotNull
  private Gender gender;

  public DogForm(String name, int age, Gender gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public Gender getGender() {
    return gender;
  }
}
