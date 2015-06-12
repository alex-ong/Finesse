
import java.awt.Font;
import java.io.*;
import java.util.Scanner;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alex
 */
class KeyBindMenu {

    private boolean exit = false;
    private int selection = 0;
    private String[] captions = {"Press Key to Bind", "Neutral 0"};
    private static final String[] keyBinds = {"Neutral", "Clockwise", "Anti-Clockwise", "180°"};
    private static final int startX = 50;
    private static final int startY = 100;
    private static final int lineHeight = 30;
    private static int currentBind = 0;

    private String bindToCaption() {
        if (currentBind < 40) {
            return keyBinds[currentBind / 10] + " "
                    + String.valueOf(currentBind % 10);
        } else if (currentBind == InputLoader.HOLD) {
            return "Hold";
        } else if (currentBind == InputLoader.UNDO) {
            return "Undo";
        } else if (currentBind == InputLoader.REVERSE_ORIENT) {
            return "Reverse Orientation";
        } else if (currentBind == InputLoader.REVERSE_ORIENT_ALT) {
            return "Reverse Orientation Alternative";
        } 
        return null;
    }

    private int currentBindToInputLoader(int cb) {
        if (cb < 40) {
            if (cb / 10 == 0) {
                cb += 20;
            } else if (cb / 20 == 0) {
                cb += 20;
            } else if (cb / 30 == 0) {
                cb -= 10;
            } else {
                cb -= 30;
            }
        }
        return cb;
    }

    TrueTypeFont font;

    public boolean exit() {
        return exit;
    }

    public void reset() {
        exit = false;
        selection = 0;
    }
    TextureHolder fTextures = null;

    public void setTextures(TextureHolder th) {
        fTextures = th;
    }

    public void render() {
        renderBackdrop();
        renderText();
    }

    private Color getColor(int i) {
        if (i == selection) {
            return Color.yellow;
        } else {
            return Color.white;
        }
    }

    private void renderText() {
        for (int i = 0; i < captions.length; i++) {
            font.drawString(startX, startY + i * lineHeight,
                    captions[i], getColor(i));
        }

    }

    public void loadSettings() {
        String filename = System.getProperty("user.dir") + "/keyboard.ini";
        File file = new File(filename);
        try {
            Scanner input = new Scanner(file);
            int i = 0;
            while (input.hasNext()) {
                String nextLine = input.nextLine();
                InputLoader.keyCodes[i] = Integer.parseInt(nextLine);
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void writeSettings() {
        String filename = System.getProperty("user.dir") + "/keyboard.ini";
        try {
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(filename), "UTF-8"));
            outputData(bw);
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void outputData(BufferedWriter bw) throws IOException {
        for (int i : InputLoader.keyCodes) {
            bw.write(Integer.toString(i));
            bw.newLine();
        }
    }

    public void init() {
	// load font from a .ttf file

        try {
            InputStream inputStream
                    = ResourceLoader.getResourceAsStream("dejavumonosansbold.ttf");
            Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont2 = awtFont2.deriveFont(20f); // set font size  

            font = new TrueTypeFont(awtFont2, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void renderBackdrop() {
        Color.white.bind();
        Texture texture = fTextures.getMainMenu();
        texture.bind(); // or GL11.glBind(texture.getTextureID());

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(0, 0);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(texture.getTextureWidth(), 0);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(texture.getTextureWidth(), texture.getTextureHeight());
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(0, texture.getTextureHeight());
        GL11.glEnd();
    }

    public void pollInput() {

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                int key = Keyboard.getEventKey();

                if (key == Keyboard.KEY_ESCAPE) {
                    for (int i = 0; i < 40; i++) {
                        System.out.println(Keyboard.getKeyName(InputLoader.keyCodes[i]));
                    }
                    exit = true;
                } else if (key == Keyboard.KEY_RETURN) {
                    //nothing
                } else {
                    System.out.print(key);
                    System.out.print(" ");
                    System.out.println(Keyboard.getKeyName(key));

                    InputLoader.keyCodes[currentBindToInputLoader(currentBind)] = key;

                    if (currentBind < InputLoader.keyCodes.length - 1) {
                        currentBind++;
                        captions[1] = bindToCaption();
                    } else {
                        writeSettings();
                        captions[1] = "All done!";
                        exit = true;
                    }

                }

            }

        }

    }

}
