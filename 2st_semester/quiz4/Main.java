public class Main {

    public static void main(String[] args) {

        Point point = new Point(0.0, 0.0);
        Circle circle = new Circle(point, 2.0);
        Rectangle rectangle =
                new Rectangle(point, 3.5, 2.0);
        Square square = new Square(point, 4.0);
        Triangle triangle =
                new Triangle(point, 2.0, 6.0);
        System.out.println("_ _ _ _ _");
        System.out.println(circle.calculateArea());
        System.out.println(rectangle.calculateArea());
        System.out.println(square.calculateArea());
        System.out.println(triangle.calculateArea());
        System.out.println("_ _ _ _ _");

        circle.rotate();
        rectangle.rotate();
        square.rotate();
        triangle.rotate();
    }
}





