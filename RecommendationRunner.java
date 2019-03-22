import java.util.*;
import static java.lang.Math.min;
import java.util.Random;
/**
 * Write a description of RecommendationRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RecommendationRunner implements Recommender {
    // This method returns a list of 10 movieIDs
    public ArrayList<String> getItemsToRate(){
        FourthRatings fr = new FourthRatings();
        int minRaters = 20;
        
        // filter movies - with 20 or more ratings
        ArrayList<Rating> filtered = fr.getAverageRatings(minRaters);
        // Store movieIDs of filtered movies
        ArrayList<String> movieIDs = new ArrayList<String>();
        for (Rating r: filtered){
            movieIDs.add(r.getItem()); 
        }
        
        // Randomly choose only 10 movies from the filtered movie list.
        ArrayList<String> trimMov = new ArrayList<String>();
        for (int i = 0; i<10; i++){
            // Make this random!
            Random rand = new Random();
            int randomIdx = rand.nextInt(movieIDs.size());
            // if already in there, get a next random number.
            while (trimMov.indexOf(movieIDs.get(randomIdx)) != -1){
                randomIdx = rand.nextInt(movieIDs.size());
            }
            trimMov.add(movieIDs.get(randomIdx));
        }
        // return the list of movies that user will need to rate.
        return trimMov;
    }
    
    // Display the top 10 movies excluding movies the user has already rated.
    public void printRecommendationsFor(String webRaterID){
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        int minimalRatings = 1; //ONLY include movies at least 5 ratings
        ArrayList<Rating> ratings = fr.getSimilarRatings(webRaterID, 2, minimalRatings);
        Collections.sort(ratings, Collections.reverseOrder()); // highest to lowest score
        
        // get already watched movies.
        Rater rater = RaterDatabase.getRater(webRaterID);
        ArrayList<String> watchedMov = rater.getItemsRated();
        
        // Exclude the already rated movies:
        //   If Rec movies size is bigger than rated movie size,
        if (ratings.size() > 10){
            for (int i = 0; i<watchedMov.size(); i++){
                //int idx = ratings.indexOf(watchedMov.get(i));
                if (ratings.indexOf(watchedMov.get(i)) != -1){
                    ratings.remove(ratings.indexOf(watchedMov.get(i)));
                }
            }
        }
        //   If Rec movies size is smaller than rated movie size,
        else {
            for (int i = 0; i<watchedMov.size(); i++){
                //int idx = ratings.indexOf(watchedMov.get(i));
                if (ratings.indexOf(watchedMov.get(i)) != -1){
                    ratings.remove(ratings.indexOf(watchedMov.get(i)));
                }
            }
        }
        
        // Truncate list to only top 10 elements
        System.out.println("Recommendation size before truncating: " + ratings.size());
        List<Rating> subItems = ratings;
        if (subItems.size() > 10){
            subItems = subItems.subList(0, 10);
        }
        
        StringBuilder buf = new StringBuilder();
        buf.append("\n" +
                   "<html>" +
                   "<h1> Your Result: Top 10 Recommended movies for you. </h1>" +
                   "<body>" +
                   "<table>"+
                   "<tr>" +
                   "<th>Number           </th>"+
                   "<th>Movie            </th>" +
                   "<th>Country          </th>" +
                   "<th>Year      </th>" +
                   "</tr>");
        for (int i = 0; i<subItems.size(); i++){
            buf.append("<tr><td>");
            Rating r = subItems.get(i);
            String title = MovieDatabase.getTitle(r.getItem());
            int year = MovieDatabase.getYear(r.getItem());
            String country = MovieDatabase.getCountry(r.getItem());
            buf.append(i+1)
            .append("</td><td>")
            .append(title)
            .append("</td><td>")
            .append(country)
            .append("</td><td>")
            .append(year)
            .append("</td><td>");
        }
        buf.append("</table>" + 
                   "</body>" +
                   "</html>");
        String html = buf.toString();
        System.out.println(html);
    }
}
