package com.eternalcode.lobby.feature.image;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;


class ImageMessage {
    private final static char TRANSPARENT_CHAR = ' ';

    private final Color[] colors = {
        new Color(0, 0, 0),
        new Color(0, 0, 170),
        new Color(0, 170, 0),
        new Color(0, 170, 170),
        new Color(170, 0, 0),
        new Color(170, 0, 170),
        new Color(255, 170, 0),
        new Color(170, 170, 170),
        new Color(85, 85, 85),
        new Color(85, 85, 255),
        new Color(85, 255, 85),
        new Color(85, 255, 255),
        new Color(255, 85, 85),
        new Color(255, 85, 255),
        new Color(255, 255, 85),
        new Color(255, 255, 255),
    };

    private final String[] lines;

    public ImageMessage(BufferedImage image, int height, char imgChar) {
        Object[][] chatColors = toChatColorArray(image, height);
        lines = toImgMessage(chatColors, imgChar);
    }

    public ImageMessage(ChatColor[][] chatColors, char imgChar) {
        lines = toImgMessage(chatColors, imgChar);
    }

    public ImageMessage(String... imgLines) {
        lines = imgLines;
    }

    public ImageMessage addText(List<String> texts) {
        for (int y = 0; y < lines.length; y++) {
            if (texts.size() > y) {
                lines[y] += " " + texts.get(y);
            }
        }
        return this;
    }

    public ImageMessage addText(String... text) {
        for (int y = 0; y < lines.length; y++) {
            if (text.length > y) {
                lines[y] += " " + text[y];
            }
        }
        return this;
    }

    private Object[][] toChatColorArray(BufferedImage image, int height) {
        double ratio = (double) image.getHeight() / image.getWidth();
        int width = Math.min(10, (int) (height / ratio));
        BufferedImage resized = resizeImage(image, width, height);

        Object[][] chatImg = new Object[resized.getWidth()][resized.getHeight()];
        for (int x = 0; x < resized.getWidth(); x++) {
            for (int y = 0; y < resized.getHeight(); y++) {
                int rgb = resized.getRGB(x, y);
                Object closest;
                try {
                    closest = net.md_5.bungee.api.ChatColor.of(new Color(rgb, true));
                } catch (NoSuchMethodError ignored) {
                    closest = getClosestNonRGBChatColor(new Color(rgb, true));
                }
                chatImg[x][y] = closest;
            }
        }
        return chatImg;
    }

    private String[] toImgMessage(Object[][] colors, char imgChar) {
        String[] lines = new String[colors[0].length];
        for (int i = 0; i < colors[0].length; i++) {
            StringBuilder line = new StringBuilder();
            for (Object[] chatColors : colors) {
                Object color = chatColors[i];
                line.append((color != null) ? chatColors[i].toString() + imgChar : TRANSPARENT_CHAR);
            }
            lines[i] = line.toString() + ChatColor.RESET;

        }
        return lines;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        AffineTransform af = new AffineTransform();
        af.scale(
            width / (double) originalImage.getWidth(),
            height / (double) originalImage.getHeight());

        AffineTransformOp operation = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return operation.filter(originalImage, null);
    }

    private double getDistance(Color c1, Color c2) {
        double rmean = (c1.getRed() + c2.getRed()) / 2.0;
        double r = c1.getRed() - c2.getRed();
        double g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        double weightR = 2 + rmean / 256.0;
        double weightG = 4.0;
        double weightB = 2 + (255 - rmean) / 256.0;
        return weightR * r * r + weightG * g * g + weightB * b * b;
    }

    private boolean areIdentical(Color c1, Color c2) {
        return Math.abs(c1.getRed() - c2.getRed()) <= 5 &&
            Math.abs(c1.getGreen() - c2.getGreen()) <= 5 &&
            Math.abs(c1.getBlue() - c2.getBlue()) <= 5;

    }

    private ChatColor getClosestNonRGBChatColor(Color color) {
        if (color.getAlpha() < 128) return null;

        int index = 0;
        double best = -1;

        for (int i = 0; i < colors.length; i++) {
            if (areIdentical(colors[i], color)) {
                return ChatColor.values()[i];
            }
        }

        for (int i = 0; i < colors.length; i++) {
            double distance = getDistance(color, colors[i]);
            if (distance < best || best == -1) {
                best = distance;
                index = i;
            }
        }

        return ChatColor.values()[index];
    }

    public String[] getLines() {
        return lines;
    }

    public void send(Player player) {
        for (String line : lines) {
            player.sendMessage(line);
        }
    }
}
