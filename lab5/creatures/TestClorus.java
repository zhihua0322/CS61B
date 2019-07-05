package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

public class TestClorus {
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.move();
        assertEquals(1.94, c.energy(), 0.01);
        c.stay();
        assertEquals(1.95, c.energy(), 0.01);
        c.stay();
        assertEquals(1.96, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        // TODO
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        Clorus os = c.replicate();
        assertEquals(1, os.energy(), 0.01);
    }

    @Test
    public void testAttack() {
        // TODO
        Clorus c = new Clorus(2);
        Plip p = new Plip((2));
        assertEquals(2, c.energy(), 0.01);
        assertEquals(2, p.energy(), 0.01);
        c.attack(p);
        assertEquals(4, c.energy(), 0.01);
    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        //Plip nearby; attack one of them randomly.
        c = new Clorus(2);
        HashMap<Direction, Occupant> topPlip = new HashMap<Direction, Occupant>();
        topPlip.put(Direction.TOP, new Plip(1));
        topPlip.put(Direction.BOTTOM, new Empty());
        topPlip.put(Direction.LEFT, new Impassible());
        topPlip.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(topPlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

        // Energy >= 1; replicate towards an empty space.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);


        // Energy < 1; move towards a random empty space.
        c = new Clorus(.99);
        actual = c.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.TOP);
        assertEquals(expected, actual);

    }
}
