package battlegrid.abstracts;

/**
 * Names: Itay Sabato, Rotem Barzilay <br/>
 * Logins: itays04, rotmus <br/>
 * IDs: 036910008, 300618592 <br/>
 * Date: 24/01/2011 <br/>
 * Time: 15:43:51 <br/>
 */
public enum Direction {
    NORTH(0,-1),
    NORTH_EAST(1,-1),
    EAST(1,0),
    SOUTH_EAST(1,1),
    SOUTH(0,1),
    SOUTH_WEST(-1,1),
    WEST(-1,0),
    NORTH_WEST(-1,-1);

    public final int dx;
    public final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}


