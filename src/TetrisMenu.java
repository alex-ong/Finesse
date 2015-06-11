
import java.awt.Font;
import java.io.InputStream;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
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
class TetrisMenu {

    private boolean gameStart = false;
    private int selection = 0;
    private static final String[] strings = {"Start Game", "Options", "Replay", "Quit"};
    private static boolean MouseDown = false;
    private static final int startX = 300;
    private static final int startY = 200;
    private static final int lineHeight = 30;
    private static final int NUM_SELECTIONS = strings.length;
    public static final int START_GAME = 0;
    public static final int OPTIONS = 1;
    public static final int REPLAY = 2;
    public static final int QUIT = 4;
    TrueTypeFont font;

    public boolean start() {
        return gameStart;
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
        for (int i = 0; i < strings.length; i++) {
            font.drawString(startX, startY + i * lineHeight,
                    strings[i], getColor(i));
        }
    }

    public void init() {
	// load font from a .ttf file

        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("dejavumonosansbold.ttf");
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

                switch (key) {
                    case Keyboard.KEY_RETURN:
                        gameStart = true;
                        break;
                    case Keyboard.KEY_DOWN:
                        selection++;
                        if (selection >= NUM_SELECTIONS) {
                            selection = 0;
                        }
                        break;
                    case Keyboard.KEY_UP:
                        selection--;
                        if (selection < 0) {
                            selection = NUM_SELECTIONS - 1;
                        }
                        break;
                    case Keyboard.KEY_ESCAPE:
                        selection = NUM_SELECTIONS - 1;
                        break;
                }

            }

        }

        while (Mouse.next()) {
            if (Mouse.getEventButton() == 0) {
                if (Mouse.getEventButtonState() && MouseDown == false) {
                    setSelection(Mouse.getX(), Mouse.getY());
                    MouseDown = true;
                } else {
                    MouseDown = false;
                }
            }

        }

    }

    //mouse input, set selection
    void setSelection(int x, int y) {
        //invert that shit.
        y = 480 - y;

        for (int i = 0; i < NUM_SELECTIONS; i++) {
            int topY = startY + i * lineHeight;
            int bottomY = startY + (i + 1) * lineHeight;
            if (y >= topY && y <= bottomY) {
                if (selection == i) {
                    gameStart = true;
                } else {
                    selection = i;
                }
                break;
            }
        }
    }

    void reset() {
        gameStart = false;
    }

    public int getSelection() {
        return selection;
    }
}
