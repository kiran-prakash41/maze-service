package uk.gov.dwp.maze.model;

public class Cell {

    //array of walls, and neighbours
    private boolean[] walls;
    private Cell[] neighbours;

    //the visit flag for this space
    private boolean visit;
    private boolean start;
    private boolean end;
    private boolean path;
    private boolean travelled;
    private int width, height;

    public Cell(int w, int h){
        width = w;
        height = h;
        walls =  new boolean[] {true,true,true,true};
        neighbours = new Cell[]{null,null, null, null};
        visit=false;
        start=false;
        end=false;
        path=false;
        travelled = false;
    }

    public void setNeighbours(Cell n[]){
        for(int i=0;i<4;i++)
            neighbours[i] = n[i];
    }

   public Cell[] getNeighbours(){
        return neighbours;
    }

   public void removeWall(int direction){
        walls[direction]=false;
        if(neighbours[direction] != null){
            neighbours[direction].walls[(direction+2) % 4] = false;
        }
   }

    public boolean[] getWalls() {
        return walls;
    }

    public void setWalls(boolean[] walls) {
        this.walls = walls;
    }

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public boolean isPath() {
        return path;
    }

    public void setPath(boolean path) {
        this.path = path;
    }

    public boolean isTravelled() {
        return travelled;
    }

    public void setTravelled(boolean travelled) {
        this.travelled = travelled;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n Path : ");
        builder.append(this.path);
        builder.append("\n Start : ");
        builder.append(this.start);
        builder.append("\n End : ");
        builder.append(this.end);
        builder.append("\n Travelled : ");
        builder.append(this.travelled);
        builder.append("\n Visit : ");
        builder.append(this.visit);
        return builder.toString();
    }
}
