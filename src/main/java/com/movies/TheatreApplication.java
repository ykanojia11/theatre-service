package com.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.CommandLineRunner;
import com.movies.repository.TheatreRepository;
import com.movies.repository.MovieRepository;
import com.movies.repository.ShowRepository;
import com.movies.repository.entities.Theatre;
import com.movies.repository.entities.Movie;
import com.movies.repository.entities.Show;
import java.time.LocalDateTime;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class
})
@ComponentScan(basePackages = {"com.movies.*"})
public class TheatreApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TheatreApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
						.allowedOrigins("http://localhost:3000")
						.allowedMethods("*")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}

	@Bean
	public CommandLineRunner dataLoader(
			TheatreRepository theatreRepo,
			MovieRepository movieRepo,
			ShowRepository showRepo) {
		return args -> {
			Theatre theatre1 = Theatre.builder().name("PVR Plaza").city("Delhi").address("Connaught Place").build();
			theatre1 = theatreRepo.save(theatre1);

			Theatre theatre2 = Theatre.builder().name("PVR Priya").city("Delhi").address("Vasant Vihar").build();
			theatre2 = theatreRepo.save(theatre2);

			Movie movie1 = Movie.builder().title("Inception").language("English").genre("Sci-Fi").build();
			movie1 = movieRepo.save(movie1);

			Movie movie2 = Movie.builder().title("Dhamaal").language("Hindi").genre("Comedy").build();
			movie2 = movieRepo.save(movie2);


			Show show1 = Show.builder()
				.theatre(theatre1)
				.movie(movie1)
				.showTime(LocalDateTime.now().plusHours(3))
				.availableSeats(100)
				.build();

			Show show2 = Show.builder()
					.theatre(theatre1)
					.movie(movie2)
					.showTime(LocalDateTime.now().plusDays(1))
					.availableSeats(100)
					.build();

			Show show3 = Show.builder()
					.theatre(theatre2)
					.movie(movie1)
					.showTime(LocalDateTime.now().plusHours(3))
					.availableSeats(100)
					.build();

			Show show4 = Show.builder()
					.theatre(theatre2)
					.movie(movie2)
					.showTime(LocalDateTime.now().plusHours(3))
					.availableSeats(100)
					.build();
			showRepo.save(show1);
			showRepo.save(show2);
			showRepo.save(show3);
			showRepo.save(show4);
		};
	}
}
