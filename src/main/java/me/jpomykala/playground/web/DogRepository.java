package me.jpomykala.playground.web;

import me.jpomykala.playground.web.dto.Dog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends PagingAndSortingRepository<Dog, Long> {

  List<Dog> findAllByName(String name);

}
