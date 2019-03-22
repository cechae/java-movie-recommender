import java.util.*;
/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovieRunnerSimilarRatings {

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
    
    private ArrayList<Rating> sortLowToHigh(ArrayList<Rating> unsorted){
        
        ArrayList<Rating> answer = unsorted;
        // Sort from low to high
        for (int i = 0; i<answer.size(); i++){
            int minIdx = getMinIndex(answer, i);
            // if already sorted
            if (minIdx == i){
                continue;
            }
            else {
                Rating org = answer.get(i);
                Rating minRating = answer.get(minIdx);
                answer.set(i, minRating);
                answer.set(minIdx, org);
            }
        }
        return answer;
    }
    
    public void printAverageRatings(){
        FourthRatings tr = new FourthRatings();
        
        // print the # of movies and # of raters from two files
        System.out.println("read data for: " + RaterDatabase.size() + " raters");
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("# of movies loaded: " + MovieDatabase.size());
        
        // Print a list of movies and their average ratings 
        // for all movies that have at least a specified num of ratings
        int minimalRatings = 35;
        ArrayList<Rating> ratings = tr.getAverageRatings(minimalRatings);
        System.out.println("Found: " + ratings.size() + " movies with minRatings " 
        + minimalRatings);
        
        ArrayList<Rating> sortedRatings = sortLowToHigh(ratings);
        // with sorted ratings, display the output
        for (Rating rating: sortedRatings){
            String id = rating.getItem();
            String name = MovieDatabase.getTitle(id);
            System.out.println(rating.getValue() + " " + name);
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        // CHANGE!
        String genre = "Drama";
        int year = 1990;
        AllFilters allF = new AllFilters();
        
        GenreFilter genreFilter = new GenreFilter(genre);
        allF.addFilter(genreFilter);
        YearAfterFilter yearFilter = new YearAfterFilter(year);
        allF.addFilter(yearFilter);
        
        FourthRatings tr = new FourthRatings(); // CHANGE!
        // print the # of movies and # of raters from two files
        System.out.println("Read data for: " + RaterDatabase.size() + " raters");
        
        MovieDatabase.initialize("ratedmoviesfull.csv"); // CHANGE!
        System.out.println("Read data for: " + MovieDatabase.size() + " movies");
        
        // Print a list of movies and their average ratings 
        // for all movies that have at least a specified num of ratings
        int minimalRatings = 8; // CHANGE!
        
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRatings, allF);
        System.out.println("Found: " + ratings.size() + " movies with minRatings & filter ");
        
        ArrayList<Rating> sortedRatings = sortLowToHigh(ratings);
        // with sorted ratings, display the output
        for (Rating rating: sortedRatings){
            String id = rating.getItem();
            String name = MovieDatabase.getTitle(id);
            System.out.println(rating.getValue() + " " + MovieDatabase.getYear(id)
            + " " + name + "\n" + "\t"
            + MovieDatabase.getGenres(id));
        }
    }
    
    
    public void printSimilarRatings(){
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        String raterID = "71";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        ArrayList<Rating> moviesScored = fr.getSimilarRatings(raterID,numSimilarRaters,minimalRaters);
        
        for (Rating r:moviesScored){
            String movieID = r.getItem();
            String title = MovieDatabase.getTitle(movieID);
            System.out.println(r.toString() + title);
        }
    }
    
    public void printSimilarRatingsByGenre(){
        String genre = "Mystery";
        GenreFilter genreFilter = new GenreFilter(genre);
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        String raterID = "964";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        ArrayList<Rating> moviesScored = fr.getSimilarRatingsByFilter(raterID,
        numSimilarRaters,minimalRaters, genreFilter);
        for (Rating r:moviesScored){
            String movieID = r.getItem();
            String title = MovieDatabase.getTitle(movieID);
            System.out.println(r.toString() + title);
            System.out.println("\t" + genre);
        }
    }
    
    public void printSimilarRatingsByDirector(){
        String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        DirectorsFilter dirFilter = new DirectorsFilter(directors);
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        String raterID = "120";
        int numSimilarRaters = 10;
        int minimalRaters = 2;
        ArrayList<Rating> moviesScored = fr.getSimilarRatingsByFilter(raterID,
        numSimilarRaters,minimalRaters, dirFilter);
        
        for (Rating r:moviesScored){
            String movieID = r.getItem();
            String title = MovieDatabase.getTitle(movieID);
            System.out.println(r.toString() + title);
            System.out.println("\t" + MovieDatabase.getDirector(movieID));
        }
    }
    
    public void printSimilarRatingsByGenreAndMinutes(){
        String genre = "Drama";
        int minTime = 80;
        int maxTime = 160;
        GenreFilter genFilter = new GenreFilter(genre);
        MinutesFilter minutesFilter = new MinutesFilter(minTime, maxTime);
        AllFilters allFilter = new AllFilters();
        allFilter.addFilter(genFilter);
        allFilter.addFilter(minutesFilter);
        
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        
        String raterID = "168";
        int numSimilarRaters = 10;
        int minimalRaters = 3;
        
        ArrayList<Rating> moviesScored = fr.getSimilarRatingsByFilter(raterID,
        numSimilarRaters,minimalRaters, allFilter);
        for (Rating r:moviesScored){
            String movieID = r.getItem();
            String title = MovieDatabase.getTitle(movieID);
            System.out.println(r.toString() + title);
            System.out.println("RunningTime: " + MovieDatabase.getMinutes(movieID) 
            + MovieDatabase.getGenres(movieID));
        }
    }
    
    
    public void printSimilarRatingsByYearAfterAndMinutes(){
        int year = 1975;
        int minTime = 70;
        int maxTime = 200;
        YearAfterFilter yearFilter = new YearAfterFilter(year);
        MinutesFilter minutesFilter = new MinutesFilter(minTime, maxTime);
        AllFilters allFilter = new AllFilters();
        allFilter.addFilter(yearFilter);
        allFilter.addFilter(minutesFilter);
        
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        
        String raterID = "314";
        int numSimilarRaters = 10;
        int minimalRaters = 5;
        
        ArrayList<Rating> moviesScored = fr.getSimilarRatingsByFilter(raterID,
        numSimilarRaters,minimalRaters, allFilter);
        for (Rating r:moviesScored){
            String movieID = r.getItem();
            String title = MovieDatabase.getTitle(movieID);
            System.out.println(r.toString() + title);
            System.out.println("year: " + MovieDatabase.getYear(movieID) 
            + " RunningTime: " + MovieDatabase.getMinutes(movieID));
        }
    }
    
}
