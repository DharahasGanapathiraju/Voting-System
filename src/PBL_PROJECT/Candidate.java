package PBL_PROJECT;

import java.util.*;
import PBL_PROJECT.Main;
public class Candidate extends Login_info {
    private int PRN,election_id;
    private String password,name,symbol;

    public Candidate() {
        super("defaultpassword", 0);
    }

    public String talkToAdmin()
    {
        String message;
        System.out.println("Enter the message to the admin note to give your PRN");
        Scanner s=new Scanner(System.in);
        message=s.nextLine();
        return message;

    }
    @Override
    public boolean Register(int PRN,String name,String password)
    {
        Scanner s=new Scanner(System.in);
        System.out.println("Enter your symbol name");
        String symbol=s.nextLine();
        System.out.println("Enter the election ID");
        int electio_id=s.nextInt();
        s.nextLine();
        boolean ans=Main.db.register_candidate(PRN,name,password,symbol,electio_id);
        if(ans)
            return true;
        else
            return false;
    }

    @Override
    public boolean login(int PRN,String password)
    {
        boolean ans=Main.db.candidate_verify_login(PRN,password);
        if(ans)
            return true;
        else
            return false;
    }


}
