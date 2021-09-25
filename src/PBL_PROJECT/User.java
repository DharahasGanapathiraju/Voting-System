package PBL_PROJECT;
import java.util.*;
import PBL_PROJECT.Main;
import java.util.*;
public class User extends Login_info {
    private String name,gender,batch,password;
    private int PRN;
    private int age,year;

    public User()
    {
        super("defaultpassword",0);
        //this.password=password;
        //this.PRN=PRN;
        this.name="defaultname";
        this.gender="defaultgender";
        this.batch="defaultbatch";
        this.age=18;
        this.year=1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean update_password(int PRN,String new_password,String old_password)
    {
        boolean result=Main.db.updateUserPassword(PRN,new_password,old_password);
        return result;
    }

    public boolean vote(int PRN,int vote,int election_number)
    {
        boolean ans=Main.db.cast_user_vote(PRN,vote,election_number);
        return ans;
    }
}