
import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    public SecondRatings(String moviefile, String ratingsfile){
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies(moviefile);
        myRaters = fr.loadRaters(ratingsfile);
        
    }
    
    public int getMovieSize(){
        return myMovies.size();
    }
    
    public int getRaterSize(){
        return myRaters.size();
    }
    
    private double getAverageByID(String id, int minimalRaters){
        double sum = 0.0;
        double n = 0.0;
        for (Rater rater : myRaters){
            if (rater.hasRating(id)){
                sum += rater.getRating(id);
                n++;
            }
        }
        
        // if there are at least minimalRaters ratings, calculate the average rating
        if (n >= minimalRaters){
            double avg = sum/n;
            return avg;
        }
        
        // else return 0.0
        return 0.0;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        // find avg rating for each movie that has been rated by
        // at least minimalRaters
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        for (Movie movie : myMovies){
            String id = movie.getID();
            double avg = getAverageByID(id, minimalRaters);
            if (avg != 0.0){
                Rating r = new Rating(id,avg);
                ratings.add(r);
            }
        }
        return ratings;
    }
    
    public String getTitle(String id){
        for (Movie movie : myMovies){
            if (movie.getID().equals(id)){
                return movie.getTitle();
            }
        }
        return "ID was not found";
    }
    
    public String getID(String title){
        for (Movie movie : myMovies){
            if (movie.getTitle().equals(title)){
                return movie.getID();
            }
        }
        return "NO SUCH TITLE";
    }
    
    
}
