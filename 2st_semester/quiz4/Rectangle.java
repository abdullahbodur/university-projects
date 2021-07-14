public class Rectangle extends Shape {

    private double width;
    private double height;

    public Rectangle(Point location, double width, double height) {
        super(location);
        this.width = width;
        this.height = height;

        System.out.println("Rectangle()");

    }

    public void draw() {
        System.out.println("Rectangle.draw()");

    }

    public double calculateArea() {
        System.out.println("Rectangle.calculateArea()");
        return width * height;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }


}
