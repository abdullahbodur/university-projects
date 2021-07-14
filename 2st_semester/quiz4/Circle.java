public class Circle extends Shape {

    private double radius;

    public Circle(Point location, double radius){
        super(location);
        this. radius = radius;
        System. out. println("Circle()");
    }
    public void draw(){
        System. out. println("Circle.draw()");
    }
    public double calculateArea(){
        System. out. println("Circle.calculateArea()");
        return PI * radius * radius;

    }
    public double getRadius(){return radius;}
}
