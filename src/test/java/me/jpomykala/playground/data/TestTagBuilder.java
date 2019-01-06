package me.jpomykala.playground.data;

import me.jpomykala.playground.data.model.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestTagBuilder {

  public static Set<Tag> getTagSet(String... tagNames) {
    return Arrays.stream(tagNames)
            .map(Tag::new)
            .collect(Collectors.toSet());
  }

  public static List<Tag> getTagList(String... tagNames) {
    return Arrays.stream(tagNames)
            .map(Tag::new)
            .collect(Collectors.toList());
  }
}
