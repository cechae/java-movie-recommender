
/**
 * Write a description of GenreFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GenreFilter implements Filter {
    private String myGenre;
    
    public GenreFilter(String genre){
        myGenre = genre;
    }

    @Override
    public boolean satisfies(String id){
        String multipleGenres = MovieDatabase.getGenres(id);
        // If this movie has multiple genres
        if (multipleGenres.contains(",")){
            String [] parts = multipleGenres.split(",");
            for (int i = 0; i<parts.length; i++){
                    parts[i] = parts[i].trim(); // delete trailing white spaces.
            }
            // Return true if one of the parts contains myGenre.
            for (String name: parts){
                if (name.equals(myGenre)){
                    return true;
                } 
            }
        }
        // If this movie has a single genre
        else {
            if (multipleGenres.equals(myGenre)){
                return true;
            }
        }
        return false;
    }
}
