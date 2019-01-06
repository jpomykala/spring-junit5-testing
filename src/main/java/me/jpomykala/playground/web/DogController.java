package me.jpomykala.playground.web;

import me.jpomykala.playground.web.dto.Dog;
import me.jpomykala.playground.web.dto.DogForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DogController {

  private final DogService dogService;
  private Logger log = LoggerFactory.getLogger(DogController.class);

  @Autowired
  public DogController(DogService dogService) {
    this.dogService = dogService;
  }

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(new NameGenderValidator());
  }

  @PostMapping("/dogs")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity createDog(@Valid @RequestBody DogForm form, BindingResult bindingResult) {

    int errorCount = bindingResult.getErrorCount();
    if (bindingResult.hasErrors()) {
      log.warn("Ouch! Form has {} error(s)", errorCount);
      List<FieldError> fieldErrors = bindingResult.getFieldErrors();
      fieldErrors.stream().map(FieldError::toString).forEach(log::warn);
      return ResponseEntity.badRequest().body(fieldErrors);
    }
    Dog dog = dogService.saveDog(form);
    return ResponseEntity.status(HttpStatus.CREATED).body("/dogs/" + dog.getId());
  }

  @GetMapping("/dogs")
  public List<Dog> findDogs(@RequestParam String name) {
    return dogService.findDogByName(name);
  }

}
