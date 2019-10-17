package byow.Core;

import java.util.Random;

public class BPSpace {
    private static final int MIN_SIZE = 6;

    private int width;
    private int height;
    Position pos;
    BPSpace leftChild;
    BPSpace rightChild;
    Room room;

    public BPSpace(Position pos, int width, int height) {
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.leftChild = null;
        this.rightChild = null;
        this.room = null;
    }

    public boolean partition(Random random) {
        if (leftChild != null) {
            return false;
        }

        boolean horizontal;
        if (height > width) {
            horizontal = true;
        } else if (width > height) {
            horizontal = false;
        } else {
            horizontal = random.nextBoolean();
        }

        int max = (horizontal ? height : width) - MIN_SIZE;
        if (max <= MIN_SIZE) {
            return false;
        }

        int split = random.nextInt(max);
        if (split < MIN_SIZE) {
            split = MIN_SIZE;
        }
        if (horizontal) {
            leftChild = new BPSpace(pos, width, split);
            rightChild = new BPSpace(new Position(pos.x, pos.y + split), width, height - split);
        } else {
            leftChild = new BPSpace(pos, split, height);
            rightChild = new BPSpace(new Position(pos.x + split, pos.y), width - split, height);
        }
        return true;
    }

    public void buildRoom(Random random) {
        if (leftChild != null) {
            leftChild.buildRoom(random);
            rightChild.buildRoom(random);
        } else {
            int offsetX = (width - MIN_SIZE <= 0) ? 0 : random.nextInt(width - MIN_SIZE);
            int offsetY = (width - MIN_SIZE <= 0) ? 0 : random.nextInt(height - MIN_SIZE);

            Position roomPos = new Position(pos.x + offsetX, pos.y + offsetY);
            int roomWidth = Math.max(random.nextInt(width - offsetX), MIN_SIZE);
            int roomHeight = Math.max(random.nextInt(height - offsetY), MIN_SIZE);
            room = new Room(roomPos, roomWidth, roomHeight);
        }
    }
}
