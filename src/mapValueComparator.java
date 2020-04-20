import java.util.Comparator;
import java.util.Map;

/**
 * This class provides ability to sort HashMap by the values
 */
public class mapValueComparator implements Comparator<Integer> {
    Map<Integer, Double> base;
    public mapValueComparator(Map<Integer, Double> base){
        this.base = base;
    }
    public int compare(Integer itemA, Integer itemB){
        if (base.get(itemA)>base.get(itemB)) {
            return 1;
        }
        else if (base.get(itemA)==base.get(itemB)){
            return 0;
        }else{
            return -1;
        }
    }


}
