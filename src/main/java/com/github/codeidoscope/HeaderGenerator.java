package com.github.codeidoscope;

public interface HeaderGenerator {
    Header generate(String statusCode, String type, int length);
}
