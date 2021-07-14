public class Square extends Rectangle {

    public Square(Point location, double length) {
        super(location,
                length, length);

        System.out.println("Square()");


    }

    public double getLength() {
        return getHeight();
    }
}
