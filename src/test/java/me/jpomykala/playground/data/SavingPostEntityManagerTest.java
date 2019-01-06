package me.jpomykala.playground.data;

import me.jpomykala.playground.data.model.Post;
import me.jpomykala.playground.data.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SavingPostEntityManagerTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private EntityManager entityManager;

  @Test
  public void savePost_withoutTags_usingEM() {
    //given
    Post post = new Post("Test", Set.of());

    //when
    entityManager.persist(post);

    //then
    int postCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "post");
    assertThat(postCount).isEqualTo(1);
  }

  @Test
  public void savePost_withAttachedTags_usingEM() {
    //given
    Set<Tag> tags = TestTagBuilder.getTagSet("a", "b", "c");
    tags.forEach(tag -> entityManager.persist(tag));
    Post post = new Post("Test", tags);

    //when
    entityManager.persist(post);

    //then
    int postCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "post");
    int tagCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "tag");

    assertThat(tagCount).isEqualTo(3);
    assertThat(postCount).isEqualTo(1);
  }

  @Test
  public void savePost_withDetachedTags_usingEM() {
    //given
    Set<Tag> tags = TestTagBuilder.getTagSet("a", "b", "c");
    Post post = new Post("Test", tags);

    //when
    entityManager.persist(post);

    //then
    int postCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "post");
    int tagCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "tag");

    assertThat(tagCount).isEqualTo(3);
    assertThat(postCount).isEqualTo(1);
  }

  @Test
  public void savePost_withAttachedDuplicatedTags() {
    //given
    List<Tag> tags = TestTagBuilder.getTagList("a", "b", "c");
    tags.forEach(tag -> entityManager.persist(tag));

    Post postOne = new Post("test_one", Set.of(tags.get(0), tags.get(1)));
    Post postTwo = new Post("test_two", Set.of(tags.get(1), tags.get(2)));

    //when
    entityManager.persist(postOne);
    entityManager.persist(postTwo);

    //then
    int postCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "post");
    int tagCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "tag");
    assertThat(tagCount).isEqualTo(3);
    assertThat(postCount).isEqualTo(2);
  }

  @Test
  public void savePost_withDetachedDuplicatedTags() {
    //given
    List<Tag> tags = TestTagBuilder.getTagList("a", "b", "c");

    Post postOne = new Post("test_one", Set.of(tags.get(0), tags.get(1)));
    Post postTwo = new Post("test_two", Set.of(tags.get(1), tags.get(2)));

    //when
    entityManager.persist(postOne);
    entityManager.persist(postTwo);

    //then
    int postCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "post");
    int tagCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "tag");
    assertThat(tagCount).isEqualTo(3);
    assertThat(postCount).isEqualTo(2);
  }
}
