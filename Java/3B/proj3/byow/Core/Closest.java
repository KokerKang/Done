package byow.Core;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** The class to find the nearest rooms between two rooms.
 * @author Henry Kang*/
public class Closest {
    /** store input-list.*/
    private static ArrayList<Rooms> inputList;
    /** citation.
     * https://www.geeksforgeeks.org/returning-multiple-values-in-java/
     * */
    /** get the closest rooms info.
     * @return return the Rooms and room's list.
     * @param lst get the list for the closest list.*/
    public static List<Object> getSmallestInfo(ArrayList<Rooms> lst) {
        ArrayList<Double> distances = new ArrayList<>();
        inputList = new ArrayList<>(lst);
        for (Rooms i : inputList) {
            distances.add(helperDistance(i.getXPos(), i.getYPos(), 0, 0));
        }

        int index = distances.indexOf(Collections.min(distances));
        Rooms closest = inputList.get(index);

        inputList.remove(index);
        return Arrays.asList(inputList, closest);
    }


    /** get the closest rooms info.
     * @return return the Rooms and room's list.
     * @param lst get the list for the closest list.
     * @param startRoom Start rooms.
     * Overloading */
    public static List<Object> getSmallestInfo(ArrayList<Rooms> lst,
                                               Rooms startRoom) {
        ArrayList<Double> distances = new ArrayList<>();
        inputList = new ArrayList<>(lst);
        for (Rooms i : inputList) {
            distances.add(helperDistance(i.getXPos(), i.getYPos(),
                    startRoom.getXPos(), startRoom.getYPos()));
        }

        int index = distances.indexOf(Collections.min(distances));
        Rooms closest = inputList.get(index);
        inputList.remove(index);

        return Arrays.asList(inputList, closest);
    }

    /** Helper function to find closest distance.
     * @return distance.
     * @param x1 room 1 xPos
     * @param x2 room 2 xPos
     * @param y1 room 1 yPos
     * @param y2 room 2 yPos
     * */
    private static double helperDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
