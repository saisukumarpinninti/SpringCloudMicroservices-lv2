package sukumar.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import sukumar.moviecatalogservice.Models.Rating;
import sukumar.moviecatalogservice.Models.UserRating;

@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(@PathVariable("userid") String userId){
        UserRating r = restTemplate.getForObject("http://RATING-DATA-SERVICE/ratingsdata/users/" + userId, UserRating.class);
        return r;
    }

    //FallBack MeThods :

    public UserRating getFallbackUserRating(@PathVariable("userid") String userId){
        Rating r = new Rating(userId,0);
        UserRating u = new UserRating(r);
        return u;
    }
}
