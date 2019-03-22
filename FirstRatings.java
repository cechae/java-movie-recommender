import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FirstRatings {
    public ArrayList<Movie> loadMovies(String filename){
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        
        ArrayList<Movie> list = new ArrayList<Movie>();
        for (CSVRecord record : parser){
            Movie m = new Movie(record.get(0), record.get(1), record.get(2),
            record.get(4),record.get(5), record.get(3), record.get(7), 
            Integer.parseInt(record.get(6)));
            list.add(m);
        }
        return list;
    }
    
    public void testLoadMovies(){
        ArrayList<Movie> movielist = loadMovies("ratedmoviesfull.csv");
        //Print the # of moviews and print each movie
        System.out.println("# of movies: " + movielist.size());
        
        // Determine how many movies incldue comedy genre
        int result = 0;
        for (Movie m : movielist){
            if (m.getGenres().indexOf("Comedy") != -1){
                result ++;
            }
        }
        System.out.println("how many includes comedy: " + result);
        
        // Determine how many movies are greater than 150 minutes
        int movie_150 = 0;
        for (Movie m : movielist){
            if (m.getMinutes() > 150){
                movie_150++;
            }
        }
        System.out.println("# of Movies >150 minutes is: " + movie_150);
        
        // Max num of movies by any director
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Movie m : movielist){
            String value = m.getDirector();
            
            // If multiple directors for this movie
            if (value.contains(",")){
                // split the string
                String[] parts = value.split(",");
                for (int i = 0; i<parts.length; i++){
                    parts[i] = parts[i].trim(); // delete trailing white spaces.
                }
                for (String name: parts){
                    // if name is already registered,
                    if (map.containsKey(name)){
                        map.put(name, map.get(name)+1);
                    } // if not put it into the map
                    else {
                        map.put(name, 1);
                    }
                }
            }
            // If only a single director for this movie
            else {
                if (map.containsKey(value)){
                        map.put(value, map.get(value)+1);
                    }
                    else {
                        map.put(value, 1);
                    }
            }
        }
        
        // Get the max # of movies by any director first
        int max = 0;
        for (String key: map.keySet()){
                if (map.get(key) > max){
                    max = map.get(key);
            }
        }
        System.out.println("the max number of films directed : " + max);
        
        // Find directors that have max # of movies directed
        ArrayList<String> maxDir = new ArrayList<String>();
        for (String key: map.keySet()){
            if (map.get(key) == max && !maxDir.contains(key)){
                    maxDir.add(key);
            }
        }
        // Print the names of these directors
        for (String s: maxDir){ 
            System.out.println(s);
        }
    }
    
    public ArrayList<Rater> loadRaters(String filename){
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<Rater> raterList = new ArrayList<Rater>();
        for (CSVRecord record: parser){
            // new user
            if (raterList.size() == 0){
                // new user
                Rater newRater = new EfficientRater(record.get(0));
                newRater.addRating(record.get(1), Integer.parseInt(record.get(2)));
                raterList.add(newRater);
            }
            else {
                // find an existing rater
                Rater existingRater = null;
                for (Rater rater : raterList){
                    if (rater.getID().equals(record.get(0))){
                        existingRater = rater;
                        rater.addRating(record.get(1), Double.parseDouble(record.get(2)));
                        break;
                        }
                }
                
                if (existingRater == null) {
                    Rater newPerson = new EfficientRater(record.get(0));
                    newPerson.addRating(record.get(1), Integer.parseInt(record.get(2)));
                    raterList.add(newPerson);
                    
                }
            }
        }
        return raterList;
    }
    
    public void testLoadRaters(){
        ArrayList<Rater> raters = loadRaters("ratings.csv");
        // total num of raters
        System.out.println("total number of raters: " + raters.size());
        
        /* COMMENT OUT PRINTING EVERY RATER
        for (Rater r: raters){
            System.out.println("ID : " + r.getID() + ", " + "Number of ratings: "
            + r.numRatings());
            ArrayList<String> movieNames = r.getItemsRated();
            for (String movie: movieNames){
                System.out.println("For movie #: " + movie + " " + "Rating is: "
                + r.getRating(movie));
            }
        }
        */
       
        // Find the num of ratings for a particular rater you specify
        String specifiedID = "193"; // change this
        for (Rater rater: raters){
            String id = rater.getID();
            if (id.equals(specifiedID)){
                System.out.println("Find Num of ratings for a particular rater"
                + "you specify: ID #" + specifiedID + " has " 
                + rater.numRatings() + " ratings. ");
                break;
            }
        }
        
        // Find max num of ratings 
        int maxNum = 0;
        for (Rater rater: raters){
            int n = rater.numRatings();
            if (n > maxNum){
                maxNum = n;
            }
        }
        
        // Determine these raters
        ArrayList<Rater> maxRaters = new ArrayList<Rater>();
        for (Rater rater: raters){
            if (rater.numRatings()==maxNum){
                maxRaters.add(rater);
            }
        }
        System.out.println("Max number of ratings is: " + maxNum);
        System.out.println("How many raters have this number of ratings: "
        + maxRaters.size() + " raters ");
        System.out.println("Determine who these raters are below: ");
        for (Rater r: maxRaters){
            System.out.println("ID: " + r.getID());
        }
        
        // Find num of ratings a particular movie has
        String specifiedMovieID = "1798709"; // change this
        int count = 0;
        for (Rater rater : raters){
            if (rater.hasRating(specifiedMovieID)){
                count++;
            }
        }
        System.out.println("The # of ratings a particular movie with ID: " 
        + specifiedMovieID + " has is: " + count);
        
        // Determine how many different movies have been rated by 
        // all these raters
        ArrayList<String> uniqueMovRated = new ArrayList<String>();
        for (Rater rater : raters){
            ArrayList<String> thislist = rater.getItemsRated();
            for (String item : thislist){
                if (!uniqueMovRated.contains(item)){
                    uniqueMovRated.add(item);
                }
            }
        }
        System.out.println("How many different movies have been rated by all "
        + "these raters: " + uniqueMovRated.size());
    }
}
