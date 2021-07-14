public abstract class Shape {
    public static double PI = 3.14;

    private Point location;

    public Shape(Point location) {
        this.location = location;
        System.out.println("Shape()");
    }

    public void rotate(){
        System.out.println("Shape.rotate()");

    }
    public Point getLocation() {return location;}
    public void changeLocation(Point location){
        this. location = location;
    }
    abstract public void draw();

    abstract public double calculateArea();

}
