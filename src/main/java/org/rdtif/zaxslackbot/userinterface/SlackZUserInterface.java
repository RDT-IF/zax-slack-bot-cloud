package org.rdtif.zaxslackbot.userinterface;

import com.zaxsoft.zax.zmachine.ZUserInterface;

import java.awt.*;
import java.util.Vector;

public class SlackZUserInterface implements ZUserInterface {
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
    public Dimension getScreenUnits() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Dimension getFontSize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Dimension getWindowSize(int window) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getDefaultForeground() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getDefaultBackground() {
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
    public void eraseWindow(int window) {
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
