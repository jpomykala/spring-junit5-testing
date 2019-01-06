package me.jpomykala.playground.data;

import me.jpomykala.playground.data.model.Post;
import me.jpomykala.playground.data.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SearchingPostTest {

  @Autowired
  private EntityManager em;

  @Autowired
  private PostRepository postRepository;

  @Test
  public void findPostByTitle_withTags_usingRepo() {
    //given
    String searchedTitle = "Amazing post title!";
    Set<Tag> tags = TestTagBuilder.getTagSet("a", "b", "c");
    em.persist(new Post(searchedTitle, tags));
    em.flush();

    //when
    List<Post> results = postRepository.findAllByTitle(searchedTitle);

    //then
    assertThat(results).isNotNull();
    assertThat(results).hasSize(1);
    assertThat(results.get(0).getTitle()).isEqualTo(searchedTitle);
    assertThat(results.get(0).getTags()).isNotNull();
    assertThat(results.get(0).getTags()).isNotEmpty();
  }

  @Test
  public void findPostByTitle_withTags_usingEM() {
    //given
    String searchedTitle = "Amazing post title!";
    Set<Tag> tags = TestTagBuilder.getTagSet("a", "b", "c");
    em.persist(new Post(searchedTitle, tags));
    em.flush();

    //when
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

    CriteriaQuery<Post> postQuery = criteriaBuilder.createQuery(Post.class);
    Root<Post> selectFields = postQuery.from(Post.class);

    String FIELD_NAME = "title";
    ParameterExpression<String> titleParameter = criteriaBuilder.parameter(String.class);

    Path<Object> searchOnField = selectFields.get(FIELD_NAME);
    Predicate wherePredicate = criteriaBuilder.equal(searchOnField, titleParameter);
    postQuery
            .select(selectFields)
            .where(wherePredicate);

    TypedQuery<Post> typedQuery = em.createQuery(postQuery);
    typedQuery.setParameter(titleParameter, searchedTitle);
    List<Post> results = typedQuery.getResultList();

    //then
    assertThat(results).isNotNull();
    assertThat(results).hasSize(1);
    assertThat(results.get(0).getTitle()).isEqualTo(searchedTitle);
    assertThat(results.get(0).getTags()).isNotNull();
    assertThat(results.get(0).getTags()).isNotEmpty();
  }
}
