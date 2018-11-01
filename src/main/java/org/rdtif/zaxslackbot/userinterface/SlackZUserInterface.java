package org.rdtif.zaxslackbot.userinterface;

import com.zaxsoft.zax.zmachine.ZUserInterface;

import java.awt.*;
import java.util.Vector;

public class SlackZUserInterface implements ZUserInterface {
    // These are my best guess based on observations -- scaling will change the actual.
    private static final int FONT_WIDTH_IN_PIXELS = 8;
    private static final int FONT_HEIGHT_IN_PIXELS = 16;
    private static final int Z_MACHINE_BLACK = 2;
    private static final int Z_MACHINE_WHITE = 9;
    private final SlackTextScreen screen;

    public SlackZUserInterface(SlackTextScreen screen) {
        this.screen = screen;
    }

    @Override
    public void initialize(int version) {
        screen.initialize();
        screen.update();
    }

    @Override
    public void fatal(String message) {
        screen.print(message);
        screen.update();
        throw new ZaxFatalException(message);
    }

    @Override
    public Dimension getScreenCharacters() {
        return new Dimension(screen.getSize().getColumns(), screen.getSize().getRows());
    }

    @Override
    public Dimension getFontSize() {
        return new Dimension(FONT_WIDTH_IN_PIXELS, FONT_HEIGHT_IN_PIXELS);
    }

    @Override
    public Dimension getScreenUnits() {
        Dimension fontSizeInPixels = getFontSize();
        Dimension screenSizeInCharacters = getScreenCharacters();
        return new Dimension(screenSizeInCharacters.width * fontSizeInPixels.width, screenSizeInCharacters.height * fontSizeInPixels.height);
    }

    @Override
    public void eraseWindow(int window) {
        screen.eraseWindow(window);
    }

    @Override
    public int getDefaultForeground() {
       return Z_MACHINE_BLACK;
    }

    @Override
    public int getDefaultBackground() {
        return Z_MACHINE_WHITE;
    }

    @Override
    public boolean hasBoldface() {
        return true;
    }

    @Override
    public boolean hasFixedWidth() {
        return true;
    }

    @Override
    public boolean hasItalic() {
        return true;
    }

    @Override
    public boolean hasTimedInput() {
        return false;
    }

    @Override
    public boolean defaultFontProportional() {
        return false;
    }

    @Override
    public boolean hasColors() {
        return false;
    }

    @Override
    public void setTerminatingCharacters(Vector characters) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasStatusLine() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasUpperWindow() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Dimension getWindowSize(int window) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Point getCursorPosition() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void showStatusBar(String s, int a, int b, boolean flag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void splitScreen(int lines) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCurrentWindow(int window) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCursorPosition(int x, int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setColor(int foreground, int background) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTextStyle(int style) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFont(int font) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int readLine(StringBuffer buffer, int time) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int readChar(int time) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void showString(String string) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void scrollWindow(int lines) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void eraseLine(int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getFilename(String title, String suggested, boolean saveFlag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void quit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void restart() {
        throw new UnsupportedOperationException();
    }
}
