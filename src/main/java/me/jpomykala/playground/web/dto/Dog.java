package me.jpomykala.playground.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "dog")
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Dog {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Id
  private Long id;

  @Column(length = 100)
  private String name;

  private int age;


  public Dog(String name, int age) {
    this.name = name;
    this.age = age;
  }
}
