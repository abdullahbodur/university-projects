import java.util.Arrays;
import java.util.Comparator;

class PersonalTrainer extends Person{

    public Member[] members;
    public String sportType;

    public PersonalTrainer(int id, String name, String surname,String sportType) {

        super(id, name, surname);
        this.members = new Member[0];
        this.sportType = sportType;
    }

    public int returnCountofMembers(){
        return this.members.length;
    }

    public void addMember(Member m){

        int size = this.members.length;

        Member[] members =  Arrays.copyOf(this.members,size+1);

        members[size] = m;

        this.members  = members;
    }

    public Member returnMember(int memberID){

        for (Member m: members) {
            if(m.Id == memberID) return m;

        }

        return null;
    }

    public Member ReturnFattestMember(){

        List<Member> members = Arrays.asList(this.members) ;

        members.sort(Comparator.comparing(Member::getWeight).reversed());

        return members.get(0);
    }

}
