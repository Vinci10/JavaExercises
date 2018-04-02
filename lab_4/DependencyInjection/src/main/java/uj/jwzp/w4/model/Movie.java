package uj.jwzp.w4.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public class Movie {
    private final String title;
    private final int year;
    private final String director;
}
