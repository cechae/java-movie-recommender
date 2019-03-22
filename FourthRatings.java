import java.util.*;
/**
 * Write a description of FourthRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FourthRatings {

    private double getAverageByID(String id, int minimalRaters){
        double sum = 0.0;
        double n = 0.0;
        
        for (Rater rater : RaterDatabase.getRaters()){
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
    
    private double dotProduct(Rater me, Rater r){
        // First get the ratings for me and the rater r
        ArrayList<String> myList = me.getItemsRated();
        ArrayList<String> otherList = r.getItemsRated();
        // Find movies that they both rated
        ArrayList<String> movieIDsBothRated = new ArrayList<String>();
        for (String movieID : myList){
            if (otherList.contains(movieID)){
                movieIDsBothRated.add(movieID);
            }
        }
        ArrayList<Double> myScores = new ArrayList<Double>();
        ArrayList<Double> otherScores = new ArrayList<Double>();
        
        // Translate a rating to -5 to 5 scale and put them into score array.
        for (String movieID : movieIDsBothRated){
            myScores.add(me.getRating(movieID) - 5.0);
            otherScores.add(r.getRating(movieID) - 5.0);
        }
        
        double dotProduct = 0.0;
        for (int i = 0; i<myScores.size(); i++){
            dotProduct += myScores.get(i) * otherScores.get(i);
        }
        return dotProduct;
    }
    
    
    // This method computes similarity rating for each rater in RaterDatabase
    // returns arrayList of Rating sorted by ratings from highest to lowest rating
    // including onely those with positive similarity rating
    private ArrayList<Rating> getSimilarities(String myID){
        ArrayList<Rater> allRaters = RaterDatabase.getRaters();
        Rater me = RaterDatabase.getRater(myID);
        ArrayList<Rating> answer = new ArrayList<Rating>();
        for (Rater r : allRaters){
            // Exclude me
            if (r.getID().equals(myID)){
                continue;
            }
            double dotP = dotProduct(me, r);
            // ONLY include positive dotProduct values
            if (dotP >= 0){
                Rating newRating = new Rating(r.getID(), dotP);
                answer.add(newRating);
            }
        }
        // Sort the array from highest to lowest rating.
        Collections.sort(answer, Collections.reverseOrder());
        
        // Rating has <rater's ID, dot product comparison btwn rater and parameter rater
        return answer;
    }
    
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRater){
        ArrayList<Rating> answer = new ArrayList<Rating>();
        ArrayList<Rating> positiveRaters = getSimilarities(id);
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        
        for (String movieID : movies){
            double sum = 0.0;
            double n = 0.0;
            for (int i = 0; i < numSimilarRaters; i++){
                Rating r = positiveRaters.get(i);
                double similarity = r.getValue();
                String raterID = r.getItem();
                Rater person = RaterDatabase.getRater(raterID);
                if (person.hasRating(movieID)){
                    double score = person.getRating(movieID);
                    double weightedAvg = score * similarity;
                    sum += weightedAvg;
                    n++;
                }
            }
            // ONLY include if the movie has at least minimalRaters ratings 
            if (n >= minimalRater){
                double w_average = sum/n;
                Rating r = new Rating(movieID, w_average);
                answer.add(r);
            }
        }
        // sort
        Collections.sort(answer, Collections.reverseOrder());
        return answer;
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRater,
    Filter filterCriteria){
        ArrayList<Rating> answer = new ArrayList<Rating>();
        ArrayList<Rating> positiveRaters = getSimilarities(id);
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        
        for (String movieID : movies){
            double sum = 0.0;
            int n = 0;
            for (int i = 0; i < numSimilarRaters; i++){
                Rating r = positiveRaters.get(i);
                double similarity = r.getValue();
                String raterID = r.getItem();
                Rater person = RaterDatabase.getRater(raterID);
                
                if (person.hasRating(movieID)){
                    double score = person.getRating(movieID);
                    double weightedAvg = score * similarity;
                    sum += weightedAvg;
                    n++;
                }
            }
            // ONLY include if the movie has at least minimalRaters ratings 
            if (n >= minimalRater){
                double w_average = sum/n;
                Rating r = new Rating(movieID, w_average);
                answer.add(r);
            }
        }
        // sort
        Collections.sort(answer, Collections.reverseOrder());
        return answer;
    }
    
}
