class Member extends Person{

    private double weight;
    private double height;



    public Member(int id, String name, String surname,double weight,double height) {
        super(id, name, surname);
        this.height = height;
        this.weight = weight;
    }

    private double bmi(){
        return this.weight / Math.pow(this.height,2);

    }


    public String weightStatus(){
        double bmi = this.bmi();


        if(bmi >= 30) {
            return "Obese";
        }
        else if(30 > bmi && bmi >= 25){
            return "Fat";

        }
        else if (25 > bmi && bmi >= 18.5){
            return "Normal";

        }
        else if (18.5 > bmi){
            return "Thin";

        }

        return "";
    }

    // setler
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
