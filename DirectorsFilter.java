import java.util.*;
/**
 * Write a description of DirectorsFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DirectorsFilter implements Filter{
    ArrayList<String> dirArr;
    
    public DirectorsFilter(String directors){
        dirArr = new ArrayList<String>();
        String [] parts = directors.split(",");
        for (int i =0; i < parts.length; i++){
            dirArr.add(parts[i]);
        }
    }

    @Override
    public boolean satisfies(String id){
        String multipleDirectors = MovieDatabase.getDirector(id);
        // If this movie has multiple Directors
        if (multipleDirectors.contains(",")){
            String [] parts = multipleDirectors.split(",");
            for (int i = 0; i<parts.length; i++){
                    parts[i] = parts[i].trim(); // delete trailing white spaces.
            }
            
            for (int i = 0; i<parts.length; i++){
                if (dirArr.contains(parts[i])){
                    return true;
                }
            }
        }
        // If this movie has a single director
        else {
            if (dirArr.contains(multipleDirectors)){
                return true;
            }
        }
        return false;
    }
}
