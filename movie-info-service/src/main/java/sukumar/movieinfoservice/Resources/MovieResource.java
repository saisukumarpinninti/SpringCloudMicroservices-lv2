package sukumar.movieinfoservice.Resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sukumar.movieinfoservice.Movie;

@RestController
@RequestMapping("/movie")
public class MovieResource {

    @Value("${api.key}")
    private String apiKey;
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/{movieid}")
    public Movie getMovieInfo(@PathVariable("movieid") String movieid){
        int mid = Integer.valueOf(movieid);
        MovieSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/"+mid+"?api_key="+apiKey, MovieSummary.class);
        return new Movie(movieid, movieSummary.getTitle(), movieSummary.getOverview());
    }
}
