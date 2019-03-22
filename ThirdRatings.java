import java.util.*;
/**
 * Write a description of ThirdRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }
    public ThirdRatings(String ratingsfile){
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters(ratingsfile);
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
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        
        for (String movieID : movies){
            double avg = getAverageByID(movieID, minimalRaters);
            if (avg != 0.0){
                Rating r = new Rating(movieID,avg);
                ratings.add(r);
            }
        }
        return ratings;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        // return all movies that have minRaters & satisfy filters
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        ArrayList<String> movieIDs = MovieDatabase.filterBy(filterCriteria);
        
        // calculate avg ratings & put them into ratings array.
        for (String id : movieIDs){
            double avg = getAverageByID(id, minimalRaters);
            if (avg != 0.0){
                Rating rating = new Rating(id, avg);
                ratings.add(rating);
            }
        }
        return ratings;
    }
    
}
