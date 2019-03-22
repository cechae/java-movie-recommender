import java.util.*;
/**
 * Write a description of MovieRunnerWithFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovieRunnerWithFilter {
    
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
        ThirdRatings tr = new ThirdRatings("ratings.csv");
        
        // print the # of movies and # of raters from two files
        System.out.println("read data for: " + tr.getRaterSize() + " raters");
        
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
    
    
    public void printAverageRatingsByYear(){
        
        int year = 2000; // CHANGE!
        
        Filter yearFilter = new YearAfterFilter(year);
        ThirdRatings tr = new ThirdRatings("ratings.csv"); // CHANGE!
        
        // print the # of movies and # of raters from two files
        System.out.println("Read data for: " + tr.getRaterSize() + " raters");
        
        MovieDatabase.initialize("ratedmoviesfull.csv"); // CHANGE!
        
        System.out.println("Read data for: " + MovieDatabase.size() + " movies");
        
        // Print a list of movies and their average ratings 
        // for all movies that have at least a specified num of ratings
        int minimalRatings = 20;
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRatings, yearFilter);
        
        System.out.println("Found: " + ratings.size() + " movies with minRatings & filter ");
        
        ArrayList<Rating> sortedRatings = sortLowToHigh(ratings);
        
        // with sorted ratings, display the output
        for (Rating rating: sortedRatings){
            String id = rating.getItem();
            String name = MovieDatabase.getTitle(id);
            System.out.println(rating.getValue() + " " + MovieDatabase.getYear(id) + " " + name);
        }
    }
    
    
    
    public void printAverageRatingsByGenre(){
        String genre = "Comedy"; // CHANGE!
        Filter genreFilter = new GenreFilter(genre);
        ThirdRatings tr = new ThirdRatings("ratings.csv"); // CHANGE!
        // print the # of movies and # of raters from two files
        System.out.println("Read data for: " + tr.getRaterSize() + " raters");
        
        MovieDatabase.initialize("ratedmoviesfull.csv"); // CHANGE!
        System.out.println("Read data for: " + MovieDatabase.size() + " movies");
        
        // Print a list of movies and their average ratings 
        // for all movies that have at least a specified num of ratings
        int minimalRatings = 20;
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRatings, genreFilter);
        System.out.println("Found: " + ratings.size() + " movies with minRatings & filter ");
        
        ArrayList<Rating> sortedRatings = sortLowToHigh(ratings);
        // with sorted ratings, display the output
        for (Rating rating: sortedRatings){
            String id = rating.getItem();
            String name = MovieDatabase.getTitle(id);
            System.out.println(rating.getValue() + " " + name + "\n" + "\t"
            + MovieDatabase.getGenres(id));
        }
        
    }
    
    public void printAverageRatingsByMinutes(){
        // CHANGE!
        int min = 105;
        int max = 135;
        Filter minFilter = new MinutesFilter(min, max);
        ThirdRatings tr = new ThirdRatings("ratings.csv"); // CHANGE!
        // print the # of movies and # of raters from two files
        System.out.println("Read data for: " + tr.getRaterSize() + " raters");
        
        MovieDatabase.initialize("ratedmoviesfull.csv"); // CHANGE!
        System.out.println("Read data for: " + MovieDatabase.size() + " movies");
        
        // Print a list of movies and their average ratings 
        // for all movies that have at least a specified num of ratings
        int minimalRatings = 5; // CHANGE!
        
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRatings, minFilter);
        System.out.println("Found: " + ratings.size() + " movies with minRatings & filter ");
        
        ArrayList<Rating> sortedRatings = sortLowToHigh(ratings);
        // with sorted ratings, display the output
        for (Rating rating: sortedRatings){
            String id = rating.getItem();
            String name = MovieDatabase.getTitle(id);
            System.out.println(rating.getValue() + " " + " Time: " + MovieDatabase.getMinutes(id) + " "
            + name);
        }
        
    }
    
    public void printAverageRatingsByDirectors(){
        // CHANGE!
        String dirNames = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        Filter dirFilter = new DirectorsFilter(dirNames);
        
        ThirdRatings tr = new ThirdRatings("ratings.csv"); // CHANGE!
        // print the # of movies and # of raters from two files
        System.out.println("Read data for: " + tr.getRaterSize() + " raters");
        
        MovieDatabase.initialize("ratedmoviesfull.csv"); // CHANGE!
        System.out.println("Read data for: " + MovieDatabase.size() + " movies");
        
        // Print a list of movies and their average ratings 
        // for all movies that have at least a specified num of ratings
        int minimalRatings = 4; // CHANGE!
        
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRatings, dirFilter);
        System.out.println("Found: " + ratings.size() + " movies with minRatings & filter ");
        
        ArrayList<Rating> sortedRatings = sortLowToHigh(ratings);
        // with sorted ratings, display the output
        for (Rating rating: sortedRatings){
            String id = rating.getItem();
            String name = MovieDatabase.getTitle(id);
            System.out.println(rating.getValue() + " " + name + "\n" + "\t"
            + MovieDatabase.getDirector(id));
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
        
        ThirdRatings tr = new ThirdRatings("ratings.csv"); // CHANGE!
        // print the # of movies and # of raters from two files
        System.out.println("Read data for: " + tr.getRaterSize() + " raters");
        
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
    
    public void printAverageRatingsByDirectorsAndMinutes(){
        // CHANGE!
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        int minTime = 90;
        int maxTime = 180;
        AllFilters allF = new AllFilters();
        DirectorsFilter dirFilter = new DirectorsFilter(directors);
        allF.addFilter(dirFilter);
        MinutesFilter minFilter = new MinutesFilter(minTime, maxTime);
        allF.addFilter(minFilter);
        
        ThirdRatings tr = new ThirdRatings("ratings.csv"); // CHANGE!
        // print the # of movies and # of raters from two files
        System.out.println("Read data for: " + tr.getRaterSize() + " raters");
        
        MovieDatabase.initialize("ratedmoviesfull.csv"); // CHANGE!
        System.out.println("Read data for: " + MovieDatabase.size() + " movies");
        
        // Print a list of movies and their average ratings 
        // for all movies that have at least a specified num of ratings
        int minimalRatings = 3; // CHANGE!
        
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRatings, allF);
        System.out.println("Found: " + ratings.size() + " movies with minRatings & filter ");
        
        ArrayList<Rating> sortedRatings = sortLowToHigh(ratings);
        // with sorted ratings, display the output
        for (Rating rating: sortedRatings){
            String id = rating.getItem();
            String name = MovieDatabase.getTitle(id);
            System.out.println(rating.getValue() + " " + "RunningTime: " 
            + MovieDatabase.getMinutes(id) + " " + name + "\n" + "\t"
            + MovieDatabase.getDirector(id));
        }
    }
    
    
    
}
