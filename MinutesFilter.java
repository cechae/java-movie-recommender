
/**
 * Write a description of MinutesFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MinutesFilter implements Filter{
    private int myMin;
    private int myMax;
    
    public MinutesFilter(int minimumMin, int maxMin){
        myMin = minimumMin;
        myMax = maxMin;
    }
    
    @Override
    public boolean satisfies(String id){
        int runningTime = MovieDatabase.getMinutes(id);
        return (runningTime >= myMin && runningTime <= myMax);
    }
}
