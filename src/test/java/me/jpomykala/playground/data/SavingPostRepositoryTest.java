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

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SavingPostRepositoryTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private TagRepository tagRepository;

  @Test
  public void savePost_withoutTags_usingRepo() {
    //given
    Post post = new Post("Test", Set.of());

    //when
    postRepository.save(post);

    //then
    int postCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "post");
    assertThat(postCount).isEqualTo(1);
  }

  @Test
  public void savePost_withAttachedTags_usingRepo() {
    //given
    Set<Tag> tags = TestTagBuilder.getTagSet("a", "b", "c");
    tags.forEach(tag -> tagRepository.save(tag));
    Post post = new Post("Test", tags);

    //when
    postRepository.save(post);

    //then
    int postCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "post");
    int tagCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "tag");

    assertThat(tagCount).isEqualTo(3);
    assertThat(postCount).isEqualTo(1);
  }

  @Test
  public void savePost_withDetachedTags_usingRepo() {

    //given
    Set<Tag> tags = TestTagBuilder.getTagSet("a", "b", "c");
    Post post = new Post("Test", tags);

    //when
    postRepository.save(post);

    //then
    int postCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "post");
    int tagCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "tag");

    assertThat(tagCount).isEqualTo(3);
    assertThat(postCount).isEqualTo(1);
  }

}
