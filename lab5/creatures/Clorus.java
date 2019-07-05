package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import static huglife.HugLifeUtils.randomEntry;
import static huglife.HugLifeUtils.random;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature {
    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;
    private static final double movelose = 0.03;
    private static final double staygain = 0.01;
    private static final double repEnergyRetained = 0.5;

    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }
    public Color color() {
        return color(r, g, b);
    }
    public void attack(Creature c) {
        energy = energy + c.energy();
    }
    public void stay() {
        // TODO
        energy = energy + staygain;
    }
    public void move() {
        // TODO
        energy = energy - movelose;
        if (energy < 0) energy = 0;
    }
    public Clorus replicate() {
        double babyEnergy = energy * (1 - repEnergyRetained);
        energy = energy * repEnergyRetained;
        return new Clorus(babyEnergy);
    }
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        // TODO
        // (Google: Enhanced for-loop over keys of NEIGHBORS?)
        // for () {...}
        for (Direction d : neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                emptyNeighbors.addLast(d);
            } else if (neighbors.get(d).name().equals("plip")) {
                plipNeighbors.addLast(d);
            }
        }
        if (emptyNeighbors.size() == 0) { // FIXME
            // TODO
            return new Action(Action.ActionType.STAY);
        }
        // Rule 2
        else if(plipNeighbors.size() > 0) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plipNeighbors));
        }
        // Rule 3
        else if(energy > 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }
        // Rule 4
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }
}
