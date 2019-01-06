package me.jpomykala.playground.data;

import me.jpomykala.playground.data.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {


  List<Post> findAllByTitle(String title);


}
