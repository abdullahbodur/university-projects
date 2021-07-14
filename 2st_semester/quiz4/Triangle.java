public class Triangle extends Shape {

    private double baseLength;
    private double height;

    public Triangle(Point location,
                    double baseLength,
                    double height) {
        super(location);
        this.baseLength = baseLength;
        this.height = height;
        System.out.println("Triangle()");

    }

    public void draw() {
        System.out.println("Triangle.draw()");
    }

    public double calculateArea() {
        System.out.println("Triangle.calculateArea()");
        return baseLength * height / 2;
    }


    public double getBaseLength() {
        return baseLength;

    }

    public double getHeight() {
        return height;
    }

    ;
}
