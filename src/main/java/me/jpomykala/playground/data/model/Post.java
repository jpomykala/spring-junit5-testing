package me.jpomykala.playground.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "post")
@AllArgsConstructor
@Builder
@Entity
@Getter
public class Post {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Id
  private Long id;

  @Column(length = 300)
  private String title;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
          name = "post_tag",
          joinColumns = {
                  @JoinColumn(name = "post_id")
          },
          inverseJoinColumns = {
                  @JoinColumn(name = "tag_id")
          }
  )
  private Set<Tag> tags;

  @Version
  private Long version;

  public Post(String title, Set<Tag> tags) {
    this.title = title;
    this.tags = tags;
  }
}
