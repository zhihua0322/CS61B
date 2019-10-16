package byow.lab12;

import org.junit.Test;

import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    public static void addHexagon(TETile[][] world, int x, int y, int s, TETile tile) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        addLowerHalf(world, x, y, s, tile);
        addUpperHalf(world, x, y + 2 * s - 1, s, tile);
    }

    private static void addLowerHalf(TETile[][] world, int x, int y, int s, TETile tile) {
        int height = s;
        for (int i = 0; i < height; i += 1) {
            for (int j = 0; j < s + 2 * i; j += 1) {
                world[(x - i) + j][y + i] = tile;
            }
        }
    }

    private static void addUpperHalf(TETile[][] world, int x, int y, int s, TETile tile) {
        int depth = s;
        for (int i = 0; i < depth; i += 1) {
            for (int j = 0; j < s + 2 * i; j += 1) {
                world[(x - i) + j][y - i] = tile;
            }
        }
    }

    public static void addTesselationOfHexagons(TETile[][] world, int x, int y, int s) {
        for (int diff = -s + 1; diff < s; diff += 1) {
//            for (int i = 0; i < 2 * s - 1; i += 1) {
//                addColumnOfHexagons(world, x, y, s, diff);
//            }
            addColumnOfHexagons(world, x, y, s, diff);
        }
    }

    private static void addColumnOfHexagons(TETile[][] world, int x, int y, int s, int diff) {
        int numofHex = s + (s - 1) - Math.abs(diff);
        int heightofEachHex = 2 * s;
        int startX = calcStartX(x, s, diff);
        int startY = calcStartY(y, s, diff);
        TETile tile;
        for (int i = 0; i < numofHex; i += 1) {
            startY += heightofEachHex;
            tile = randomTile();
            addHexagon(world, startX, startY, s, tile);
        }
    }

    private static int calcStartX(int x, int s, int diff) {
        int offset = 2 * Math.abs(diff) * (s - 1) + Math.abs(diff);
        if (diff >= 0) {
            return x + offset;
        }
        return x - offset;
    }

    private static int calcStartY(int y, int s, int diff) {
        int offset = s * Math.abs(diff);
        if (diff == 0) {
            return y;
        }
        return y + offset;
    }

    private static TETile randomTile() {
        Random dice = new Random();
        int tileNum = dice.nextInt(5);
        switch (tileNum) {
            case 0:
                return Tileset.WALL;
            case 1:
                return Tileset.FLOWER;
            case 2:
                return Tileset.GRASS;
            case 3:
                return Tileset.FLOOR;
            case 4:
                return Tileset.TREE;
            default:
                return Tileset.NOTHING;
        }
    }
//x,y is the left down corner of hex
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(70, 70);
        TETile[][] world = new TETile[70][70];
        for (int x = 0; x < 70; x += 1) {
            for (int y = 0; y < 70; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
//        HexWorld.addHexagon(world, 15, 15, 2, Tileset.FLOWER);
//        HexWorld.addHexagon(world, 29, 6, 3, Tileset.GRASS);
//        HexWorld.addHexagon(world, 24, 3, 3, Tileset.WALL);
        HexWorld.addTesselationOfHexagons(world, 15, 15, 3);

        ter.renderFrame(world);
    }
}
