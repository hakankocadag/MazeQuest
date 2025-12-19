package mazegame;
import java.awt.Color;

public class Cell {
    public int row;
    public int col;
    public boolean visited = false;
    
    public boolean wallN = true;
    public boolean wallE = true;
    public boolean wallS = true;
    public boolean wallW = true;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public void draw() {
        if (visited) {
            StdDraw.setPenColor(StdDraw.WHITE);
        } else {
            StdDraw.setPenColor(StdDraw.BLACK);
        }
        StdDraw.filledSquare(col + 0.5, row + 0.5, 0.5);
        
        StdDraw.setPenColor(StdDraw.RED);
        if (wallN) {
            StdDraw.line(col, row + 1, col + 1, row + 1);
        }
        if (wallE) {
            StdDraw.line(col + 1, row, col + 1, row + 1);
        }
        if (wallS) {
            StdDraw.line(col, row, col + 1, row);
        }
        if (wallW) {
            StdDraw.line(col, row, col, row + 1);
        }
    }
}