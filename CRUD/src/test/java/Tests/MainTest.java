package Tests;

import org.junit.Test;

import java.util.*;

public class MainTest {
    @Test
    public void testMain() {
        Map<Integer, List> id1 = new HashMap<Integer, List>();
        List<String> list1 = new ArrayList<String>();

        list1.add("r1");
        list1.add("r4");

        List<String> list2 = new ArrayList<String>();
        list2.add("r2");
        list2.add("r5");

        List<String> list3 = new ArrayList<String>();
        list3.add("r3");
        list3.add("r6");

        id1.put(1, list1);
        id1.put(2, list2);
        id1.put(3, list3);
        id1.put(10, list2);
        id1.put(15, list3);
        Optional<List> o = id1.entrySet()
                .stream()
                .filter(e -> e.getKey() == 1)
                .map(Map.Entry::getValue)
                .findFirst();


    }
}
