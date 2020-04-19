import java.util.Comparator;
import java.util.Map;

public class mapValueComparator implements Comparator<Integer> {
    Map<Integer, Double> base;
    public mapValueComparator(Map<Integer, Double> base){
        this.base = base;
    }
    public int compare(Integer itemA, Integer itemB){
        if (this.base.get(itemA)>=base.get(itemB)) {
            return 1;
        }
        else{
            return -1;
        }
    }


}
