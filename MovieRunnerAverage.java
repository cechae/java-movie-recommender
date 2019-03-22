import java.util.*;
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovieRunnerAverage {

    private int getMinIndex(ArrayList<Rating> ratings, int from){
        int minIndex = from;
        for (int i = from+1; i<ratings.size(); i++){
            // if this rating is smaller than the minIndex, update it.
            if (ratings.get(i).compareTo(ratings.get(minIndex)) == -1){
                minIndex = i;
            }
        }
        return minIndex;
    }
    
    public void printAverageRatings(){
        SecondRatings sr = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        
        // print the # of movies and # of raters from two files
        System.out.println("# of movies loaded: " + sr.getMovieSize());
        System.out.println("# of raters loaded: " + sr.getRaterSize());
        
        // Print a list of movies and their average ratings 
        // for all movies that have at least a specified num of ratings
        int minimalRatings = 12;
        
        ArrayList<Rating> ratings = sr.getAverageRatings(minimalRatings);
        System.out.println("before sorting, array size: " + ratings.size());
        for (int i = 0; i<ratings.size(); i++){
            int minIdx = getMinIndex(ratings, i);
            // if already sorted
            if (minIdx == i){
                continue;
            }
            else {
                Rating org = ratings.get(i);
                Rating minRating = ratings.get(minIdx);
                ratings.set(i, minRating);
                ratings.set(minIdx, org);
            }
        }
        
        // after sorting, whats the size
        System.out.println("after sorting, array size: " + ratings.size());
        // with sorted ratings, display the output
        for (Rating rating: ratings){
            String title = rating.getItem();
            String name = sr.getTitle(title);
            System.out.println(rating.getValue() + " " + name);
        }
    }
    
    public void getAverageRatingOneMovie(){
        SecondRatings sr = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        // Print out avg ratings for a specific movie title
        
        String title = "Vacation";
        String movieID = sr.getID(title);
        
        int minimalRatings = 50;
        ArrayList<Rating> ratings = sr.getAverageRatings(minimalRatings);
        System.out.println("how many movies have 50 or more ratings: " + ratings.size());
        /*
        for (Rating rating: ratings){
            
            if (rating.getItem().equals(movieID)){
                System.out.println("Avg rating for: " + title + " is: "
                + rating.getValue());
            }
        }
        */
    }
    
    
    
}
