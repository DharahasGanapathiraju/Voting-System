package PBL_PROJECT;

import java.util.*;
import PBL_PROJECT.Main;
public class Admin extends Login_info {
    private String password, name;
    private int PRN;

    public Admin() {
        super("PBLPROJECT", 1);
        this.name = "Admins";
        this.password="dharahas";
        this.PRN=1;
    }

    @Override
    public boolean login(int PRN,String password)
    {
        if(PRN==this.PRN)
        {
            if(password.equals(this.password)) {

                return true;
            }
            else
                return false;
        }
        else
            return false;
    }
    ArrayList<String>messages=new ArrayList<>();

    public void addmessages(String message)
    {

        messages.add(message);
        System.out.println("Message sent successfully");
    }

    public void viewmessages()
    {
        for(String msg:messages)
            System.out.println("The message is : "+msg);
    }

    public boolean create_election_admin(int _id,String name)
    {
        boolean ans=Main.db.create_election(_id,name);
        return ans;
    }
    public boolean delete_candidate(int PRN)
    {

        boolean ans=Main.db.delete_candidate_acc(PRN);
        return ans;
    }

    public boolean delete_election(int _id)
    {
        boolean ans=Main.db.delete_election_acc(_id);
        return ans;
    }

    public boolean is_result_available(int election_id)
    {
        boolean ans=Main.db.can_vote(election_id);
        return ans;

    }
int released;
    public boolean announce(int election_id)
    {

        Scanner s=new Scanner(System.in);
        System.out.println("Enter the code to announce the result");
        int code=s.nextInt();
        s.nextLine();
        int winner;
        if(code==1) {
            winner = Main.db.calculate_result(election_id,0);
                boolean ans = Main.db.update_election(winner, election_id);
                if (ans) {
                    released = 1;
                    return true;
                } else {
                    released = 0;
                    return false;
                }
        }
        else
            return false;
    }
public boolean reset_user_votes(int election_id)

{
    System.out.println("Enter the code to reset all user votes");
    Scanner s=new Scanner(System.in);
    int code=s.nextInt();
    s.nextLine();
    if(code==1) {
        Main.db.reset_user_vote(election_id);
        return true;
    }
    else
        return false;
}
}