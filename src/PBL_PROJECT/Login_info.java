package PBL_PROJECT;

public class Login_info {
    public String password;
    public int PRN;

    public Login_info(){}

    //public String getPassword() {
       // return password;
   // }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPRN() {
        return PRN;
    }

    public void setPRN(int PRN) {
        this.PRN = PRN;
    }

    public Login_info(String password, int PRN) {//used for constructor call
        this.password = password;
        this.PRN = PRN;
    }

    public boolean login(int PRN,String password)
    {
        return true;
    }

    public boolean Register(int PRN,String name,String password)
    {
        return true;
    }

    public String view_result(int election_no)
    {

        String ans=Main.db.view_winner(election_no);//winner name
        return ans;
    }
}