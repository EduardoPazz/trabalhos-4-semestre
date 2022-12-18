package org.example.services;

class Parser {
  static String parseString(String input) {
    return input.isBlank() ? null : input;
  }

  static Integer parseInteger(String input) {
    return input.isBlank() ? null : Integer.valueOf(input);
  }

  static Boolean parseBoolean(String input) {
    return input.isBlank() ? null : Boolean.valueOf(input);
  }
}
