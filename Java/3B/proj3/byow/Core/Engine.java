package byow.Core;

//import byow.InputDemo.InputSource;
//import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

//import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;



/** Engine class.
 * @author Henry Kang 3035182008
 * @author TaeMin Kim 3035074875
 * */
public class Engine {
    /** TERenderer.*/
    private TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */

    /** Width.*/
    public static final int WIDTH = 55;
    /** Height.*/
    public static final int HEIGHT = 55;

    /** input number.*/


    private String language = "English";


    private MapGenerator mg = new MapGenerator();
    //InputSource inputSource = new KeyboardInputSource();

    /**
     * Method used for exploring a fresh world.
     * This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        String number;
        drawMainMenu();
        char c = 'q';
        if (c == 'n') {
            String seedNum = solicitNCharsInput();
            number = seedNum.replaceAll("[^0-9.]", "");
            long seed = Long.parseLong(number);
            mg.init(seed);
            String moveKeys = movement();
            moveKeys = moveKeys.replaceAll("[^wasd]", "");
            save(seed + moveKeys);
            interactWithKeyboard();
        } else if (c == 'l') {
            String keys = load();
            String seed = keys.replaceAll("[^0-9.]", "");
            long longSeed = Long.parseLong(seed);
            String movingKeys = keys.replaceAll("[^a-z]", "");
            solicitKeysInput(longSeed, movingKeys);
            String moveKeys = movement();
            moveKeys = moveKeys.replaceAll("[^wasd]", "");
            save(seed + movingKeys + moveKeys);
            interactWithKeyboard();
        } else if (c == 'g') {
            interactWithKeyboard();
        } else if (c == 'r') {
            String keys = load();
            String seed = keys.replaceAll("[^0-9.]", "");
            long longSeed = Long.parseLong(seed);
            String movingKeys = keys.replaceAll("[^a-z]", "");
            solicitKeysReplayInput(longSeed, movingKeys);
            String moveKeys = movement();
            moveKeys = moveKeys.replaceAll("[^wasd]", "");
            save(seed + movingKeys + moveKeys);
            interactWithKeyboard();
        } else if (c == 'q') {
            System.exit(0);
        } else {
            interactWithKeyboard();
        }
    }






    public void drawFrame(String s) {
        //StdDraw.clear(Color.BLACK);
        //StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        //StdDraw.setFont(fontBig);
        //StdDraw.text(this.WIDTH / 2, this.HEIGHT / 2, s);
        Font fontSmall = new Font("Monaco", Font.BOLD, 20);
        //StdDraw.setFont(fontSmall);
        //StdDraw.show();
    }

    public String solicitNCharsInput() {

        String userInput = "";
        while (!userInput.matches(".*?[sS]")) {
            //if (StdDraw.hasNextKeyTyped()) {
            //char current = StdDraw.nextKeyTyped();
            //userInput += current;
            //this.drawFrame(userInput);
            //}
        }


        return userInput;
    }

    public void solicitKeysInput(long seed, String keys) {
        mg.init(seed);
        keys = keys.toLowerCase();
        for (int i = 0; i < keys.length(); i++) {
            if (keys.charAt(i) == 'w') {
                mg.moveUpWithoutShow();
            } else if (keys.charAt(i) == 's') {
                mg.moveDownWithoutShow();
            } else if (keys.charAt(i) == 'a') {
                mg.moveLeftWithoutShow();
            } else if (keys.charAt(i) == 'd') {
                mg.moveRightWithoutShow();
            }
        }
    }

    public void solicitKeysReplayInput(long seed, String keys) {
        mg.init(seed);
        keys = keys.toLowerCase();
        for (int i = 0; i < keys.length(); i++) {
            if (keys.charAt(i) == 'w') {
                mg.moveUpReplay();
            } else if (keys.charAt(i) == 's') {
                mg.moveDownReplay();
            } else if (keys.charAt(i) == 'a') {
                mg.moveLeftReplay();
            } else if (keys.charAt(i) == 'd') {
                mg.moveRightReplay();
            }
        }
    }


    public String movement() {
        String userInput = "";
        return userInput;
    }


    public void save(String movementKeys) {
        File f = new File("./savefile.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream file = new FileOutputStream(f);
            ObjectOutputStream objectOut = new ObjectOutputStream(file);
            objectOut.writeUTF(movementKeys);
            objectOut.close();
            file.close();
        } catch (IOException ex) {
            System.out.print(ex);
            System.exit(0);
        }
    }

    public char language() {
        char lan = 'e';
        return lan;
    }

    public String load() {
        File f = new File("./savefile.txt");
        String loadWorld = "";
        if (f.exists()) {
            try {
                FileInputStream file = new FileInputStream(f);
                ObjectInputStream objectOut = new ObjectInputStream(file);
                loadWorld = objectOut.readUTF();
                objectOut.close();
                return loadWorld;
            } catch (IOException ex) {
                //StdDraw.clear(Color.BLACK);
                //StdDraw.setPenColor(Color.WHITE);
                Font fontBig = new Font("Monaco", Font.BOLD, 20);
                //StdDraw.setFont(fontBig);
                //StdDraw.text(this.WIDTH / 2, 35, "There is no saved file.");
                //StdDraw.show();
                //StdDraw.pause(1000);
                interactWithKeyboard();
            }
        }
        return loadWorld;
    }

    private void drawMainMenu() {
        if (language.equals("English")) {
            String temp = "0";
        } else if (language.equals("Spanish")) {
            String temp = "0";
        } else if (language.equals("Korean")) {
            String temp = "0";
        }
    }




    /**
     * Method used for autograding and testing your code.
     * The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas",
     * "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed
     * these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should
     * cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"),
     * we expect the game to run the first
     * 7 commands (n123sss) and then quit and save.
     * If we then do interactWithInputString("l"),
     * we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        /** passed in as an argument, and return a 2D tile representation of the
         world that would have been drawn if the same inputs had been given
         to interactWithKeyboard().
        See proj3.byow.InputDemo for a demo
         of how you can make a nice clean interface
         that works for many different input types.
         */

        TETile[][] finalWorldFrame = null;
        String lowerInput = input.toLowerCase();
        if (lowerInput.charAt(0) == 'n') {
            String withoutN = lowerInput.substring(1);
            String stringSeed = withoutN.replaceAll("[^\\d]", "");
            long seed = Long.parseLong(stringSeed);

            String keyMoving = withoutN.replaceAll("[\\d]", "");
            keyMoving = keyMoving.substring(1);


            //has :q
            if (keyMoving.matches(".*:[q]")) {
                String withoutQ = keyMoving.replaceAll("[:q]", "");
                solicitKeysInput(seed, withoutQ);
                save(seed + withoutQ);
                //interactWithKeyboard();
            }
            solicitKeysInput(seed, keyMoving);
            finalWorldFrame = mg.getWorld();
            //String moveKeys = movement();
            //moveKeys = moveKeys.replaceAll("[^wasd]", "");
            //save(seed + keyMoving + moveKeys);
            //interactWithKeyboard();
            save(seed + keyMoving);
        } else if (lowerInput.charAt(0) == 'l') {
            String loadKeys = load();
            loadKeys = loadKeys.replaceAll(":q", "");
            String stringSeed = loadKeys.replaceAll("[^\\d]", "");
            long seed = Long.parseLong(stringSeed);
            String movingKeys = loadKeys.replaceAll("[^a-z]", "");

            String inputMoving = lowerInput.substring(1);

            //has :q
            if (inputMoving.matches(".*:[q]")) {
                String withoutQ = inputMoving.replaceAll("[:q]", "");
                solicitKeysInput(seed, movingKeys + withoutQ);
                save(seed + movingKeys + withoutQ);
                //interactWithKeyboard();
            }

            solicitKeysInput(seed, movingKeys + inputMoving);
            finalWorldFrame = mg.getWorld();
            //String moveKeys = movement();
            //moveKeys = moveKeys.replaceAll("[^wasd]", "");
            //save(seed + movingKeys + inputMoving + moveKeys);
            //interactWithKeyboard();
            save(seed + movingKeys + inputMoving);
        } else {
            String number = input.replaceAll("[^\\d]", "");
            long seed = Long.parseLong(number);
            mg.init(seed);
            finalWorldFrame = mg.getWorld();
        }

        return finalWorldFrame;

    }






    public static void main(String[] args) {

        Engine engine = new Engine();
        engine.interactWithInputString("n1234ww");

    }
}
