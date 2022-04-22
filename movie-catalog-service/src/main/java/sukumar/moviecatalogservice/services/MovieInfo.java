package sukumar.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sukumar.moviecatalogservice.Models.CatalogItem;
import sukumar.moviecatalogservice.Models.Movie;
import sukumar.moviecatalogservice.Models.Rating;

@Service
public class MovieInfo {
    @Autowired
    private RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        Movie m = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movie/" + rating.getMovieid(), Movie.class);
        return new CatalogItem(m.getName(), m.getDescription(), rating.getRating());
    }
    public CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem(rating.getMovieid(), "Cant Find Server Error", rating.getRating());
    }
}
