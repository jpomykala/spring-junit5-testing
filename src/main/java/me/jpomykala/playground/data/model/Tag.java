package me.jpomykala.playground.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.Set;

@Table(name = "tag")
@AllArgsConstructor
@Builder
@Entity
public class Tag {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Id
  private Long id;

  @Column(length = 100, unique = true)
  private String name;


  @Version
  private Long version;


  @ManyToMany(mappedBy = "tags")
  private Set<Post> posts;

  public Tag(String name) {
    this.name = name;
  }
}
