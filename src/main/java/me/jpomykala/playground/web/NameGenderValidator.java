package me.jpomykala.playground.web;

import me.jpomykala.playground.web.dto.DogForm;
import me.jpomykala.playground.web.dto.Gender;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class NameGenderValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return DogForm.class.equals(clazz);
  }

  @Override
  public void validate(Object inputForm, Errors errors) {

    DogForm form = (DogForm) inputForm;

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "gender.empty");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty");

    Gender gender = form.getGender();
    String name = form.getName();

    if (gender == null || name == null) {
      return;
    }

    boolean isMan = gender.equals(Gender.MAN);
    boolean isWoman = gender.equals(Gender.WOMAN);

    boolean isNotAManName = isMan && name.endsWith("a");
    boolean isNotAWomanName = isWoman && !name.endsWith("a");

    if (isNotAManName || isNotAWomanName) {
      errors.rejectValue("gender", "name not match to gender");
    }
  }
}
