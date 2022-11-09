package org.example.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class IOHelper {

  public static List<String> readDatabase() throws IOException {
    return Files.readAllLines(Path.of("src/main/resources/bd.txt"));
  }
}
