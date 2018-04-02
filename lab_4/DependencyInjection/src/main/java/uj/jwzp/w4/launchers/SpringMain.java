package uj.jwzp.w4.launchers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uj.jwzp.w4.logic.MovieLister;
import uj.jwzp.w4.model.Movie;

@Slf4j
public class SpringMain {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("uj.jwzp.w4.logic");
        MovieLister lister = (MovieLister) ctx.getBean("movieLister", args[1]);

        lister.moviesDirectedBy("Hoffman").stream()
                .map(Movie::toString)
                .forEach(log::info);
    }
}
