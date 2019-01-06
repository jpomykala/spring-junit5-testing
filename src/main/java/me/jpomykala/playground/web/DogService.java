package me.jpomykala.playground.web;

import me.jpomykala.playground.web.dto.Dog;
import me.jpomykala.playground.web.dto.DogForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class DogService {

  private final DogRepository dogRepository;

  @Autowired
  public DogService(DogRepository dogRepository) {
    this.dogRepository = dogRepository;
  }

  public Dog saveDog(@NotNull DogForm form) {
    String name = form.getName();
    int age = form.getAge();
    Dog dogResponse = new Dog(name, age);
    return dogRepository.save(dogResponse);
  }


  public List<Dog> findDogByName(@NotNull String dogName) {
    return dogRepository.findAllByName(dogName);
  }

}
