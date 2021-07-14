import java.util.Arrays;

class SportCenter {
    private String name;

    public PersonalTrainer[] PTs;

    public SportCenter(String name) {
        this.name = name;
        this.PTs = new PersonalTrainer[0];
    }


    public void addPT(PersonalTrainer pt){
        int size = this.PTs.length;

        PersonalTrainer[] pts =  Arrays.copyOf(this.PTs,size+1);

        pts[size] = pt;

        this.PTs = pts;
    }

    public PersonalTrainer searchPT(String name,String surname){

        for (PersonalTrainer pt: this.PTs) {

            if(pt.name.equals(name) && pt.surname.equals(surname)){
                return pt;
            }

        }

        return null;
    }

}
