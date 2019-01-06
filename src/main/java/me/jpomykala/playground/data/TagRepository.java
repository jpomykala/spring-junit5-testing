package me.jpomykala.playground.data;

import me.jpomykala.playground.data.model.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Integer> {
}
