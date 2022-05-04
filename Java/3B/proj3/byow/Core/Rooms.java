package byow.Core;

/** Data structures of the room.
 * @author Henry Kang*/
public class Rooms {

    /** x Position.*/
    private int xPos;
    /** y Position.*/
    private int yPos;
    /** Width.*/
    private int width;
    /** Height.*/
    private int height;


    /**Constructors.
     * @param xPosition xpos.
     * @param yPosition ypos.
     * @param valHeight height.
     * @param valWidth width.*/
    public Rooms(int xPosition, int yPosition, int valWidth, int valHeight) {
        xPos = xPosition;
        yPos = yPosition;
        width = valWidth;
        height = valHeight;
    }
    /**get the xPosition.
     * @return xPos.*/
    public int getXPos() {
        return xPos;
    }

    /**get the yPosition.
     * @return yPos.*/
    public int getYPos() {
        return yPos;
    }

    /**get the width.
     * @return width.*/
    public int getWidth() {
        return width;
    }
    /**get the height.
     * @return height.*/
    public int getHeight() {
        return height;
    }


}
