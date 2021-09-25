package PBL_PROJECT;
import java.util.*;
import PBL_PROJECT.DataBase;
public class Main {
    public static DataBase db=new DataBase();
    public static void main(String[] args) {
        boolean db_var = db.open();
        if (!db_var)
            System.out.println("unable to open database ");
        else {
            System.out.println("Database opened successfully");
            System.out.println();
            User u = new User();
            Admin a = new Admin();
            Candidate c = new Candidate();
            Login_info l = new Login_info();
            String name, gender, batch, password, oldpassword, election_name, winner_name;
            int PRN, age, year, election_id, candidate_PRN, count, winner, inside_switch, inside_nx = 1, nx = 1, variable;
            boolean check;
            ArrayList<User> users;
            ArrayList<Integer> count1;
            System.out.println("////////////////////VOTING SYSTEM////////////////////");
            Scanner s = new Scanner(System.in);
            while (inside_nx!=0) {
                System.out.println("********************MAIN MENU********************");
                System.out.println("select from\n1.Admin\n2.Candidate\n3.User\n");
                System.out.println("Enter choice : ");
                try {
                    inside_switch = s.nextInt();
                }catch(InputMismatchException e) {
                    s.nextLine();
                    System.out.println("Enter the number rather than string");
                    inside_switch=s.nextInt();
                }
                s.nextLine();
                switch (inside_switch) {
                    case 1: {
                        while(nx!=0) {
                            System.out.print("select\n1.Login\n2.RegisterCandidate\n3.CreateElection\n4.AnnounceResult\n5.ViewResult\n");
                            System.out.println("6.ViewMessage\n7.DeleteUser\n8.DeleteElection\n9.DeleteCandidate\n10.ResetVotes");
                            System.out.println("Enter choice : ");
                            try {
                                variable = s.nextInt();
                            }catch(InputMismatchException e)
                            {
                                s.nextLine();
                                System.out.println("Enter the number rather than a string");
                                variable=s.nextInt();
                            }
                            s.nextLine();
                            switch (variable) {
                                case 1:
                                        System.out.println("Enter the PRN of the admin");
                                        try {
                                            PRN = s.nextInt();
                                        }catch(InputMismatchException e)
                                        {
                                            s.nextLine();
                                            System.out.println("Enter PRn as a integer rather than a string");
                                            PRN=s.nextInt();
                                        }
                                        s.nextLine();
                                    System.out.println("Enter the password of the admin");
                                    password = s.nextLine();
                                    check = a.login(PRN, password);
                                    if (check)
                                        System.out.println("Admin login successful");
                                    else
                                        System.out.println("Unable to login as adminn");
                                    break;
                                case 2:
                                    System.out.println("Enter the PRN");
                                    try {
                                        PRN = s.nextInt();
                                    }catch(InputMismatchException e)
                                    {
                                        s.nextLine();
                                        System.out.println("Enter PRN as a integer rather than a string");
                                        PRN=s.nextInt();
                                    }
                                    s.nextLine();
                                    System.out.println("Enter your name");
                                    name = s.nextLine();
                                    System.out.println("Enter your password");
                                    password = s.next();
                                    check = c.Register(PRN, name, password);
                                    if (check)
                                        System.out.println("Candidate registered successfully");
                                    else
                                        System.out.println("Unable to register candidate");
                                    break;
                                case 3:
                                    System.out.println("enter the election id");
                                    try {
                                        election_id = s.nextInt();
                                    }catch(InputMismatchException e)
                                    {
                                        s.nextLine();
                                        System.out.println("Enter the election_id as a number rather than string");
                                        election_id=s.nextInt();
                                    }
                                    s.nextLine();
                                    System.out.println("Enter the election name");
                                    election_name = s.nextLine();
                                    check = a.create_election_admin(election_id, election_name);
                                    if (check)
                                        System.out.println("Election created successfully");
                                    else
                                        System.out.println("Unable to create election");
                                    break;
                                case 4:
                                    System.out.println("enter the election id");
                                    try {
                                        election_id = s.nextInt();
                                    }catch(InputMismatchException e)
                                    {
                                        s.nextLine();
                                        System.out.println("Enter the election_id as a number rather than string");
                                        election_id=s.nextInt();
                                    }
                                    s.nextLine();
                                    int temp_no = db.calculate_result(election_id,0);
                                    if (temp_no == -2)
                                        System.out.println("Result aldready released");
                                    else {
                                        check = a.announce(election_id);
                                        if (check)
                                            System.out.println("Result of election " + election_id + " released");
                                        else
                                            System.out.println("Unable to release result");
                                    }
                                    break;
                                case 5:
                                    System.out.println("enter the election id");
                                    try {
                                        election_id = s.nextInt();
                                    }catch(InputMismatchException e)
                                    {
                                        s.nextLine();
                                        System.out.println("Enter the election_id as a number rather than string");
                                        election_id=s.nextInt();
                                    }
                                    s.nextLine();
                                    boolean check1 = a.is_result_available(election_id);
                                    if (check1) {
                                        winner_name = l.view_result(election_id);
                                        if (!winner_name.equals("null"))
                                            System.out.println("The winner of election " + election_id + " is " + winner_name);
                                        else
                                            System.out.println("Unable to display the winner name");
                                    } else
                                        System.out.println("Result not yet released");
                                    break;
                                case 6:
                                    a.viewmessages();
                                    break;
                                case 7:
                                    System.out.println("Enter the PRN to delete the user");
                                    try {
                                        PRN = s.nextInt();
                                    }catch(InputMismatchException e)
                                    {
                                        s.nextLine();
                                        System.out.println("Enter PRN as a integer rather than a string");
                                        PRN=s.nextInt();
                                    }
                                    s.nextLine();
                                    System.out.println("Enter the password of the user");
                                    password = s.nextLine();
                                    check = db.user_acc_delete(PRN, password);
                                    if (check)
                                        System.out.println("User deleted successfully");
                                    else
                                        System.out.println("Unable to delete user");
                                    break;
                                case 8:
                                    System.out.println("Enter the election ID");
                                    try {
                                        election_id = s.nextInt();
                                    }catch(InputMismatchException e)
                                    {
                                        s.nextLine();
                                        System.out.println("Enter the election_id as a number rather than string");
                                        election_id=s.nextInt();
                                    }
                                    s.nextLine();
                                    check = a.delete_election(election_id);
                                    if (check)
                                        System.out.println("Election deleted successfully");
                                    else
                                        System.out.println("Unable to delete election");
                                    break;
                                case 9:
                                    System.out.println("Enter the candidate PRN");
                                    try {
                                        PRN = s.nextInt();
                                    }catch(InputMismatchException e)
                                    {
                                        s.nextLine();
                                        System.out.println("Enter PRN as a integer rather than a string");
                                        PRN=s.nextInt();
                                    }
                                    s.nextLine();
                                    check = a.delete_candidate(PRN);
                                    if (check)
                                        System.out.println("Candidate account deleted successfully");
                                    else
                                        System.out.println("Unable to delete candidate account");
                                    break;
                                case 10:
                                    System.out.println("Enter the election Id");
                                    try {
                                        election_id = s.nextInt();
                                    }catch(InputMismatchException e)
                                    {
                                        s.nextLine();
                                        System.out.println("Enter election number as a integer rather than a string");
                                        election_id=s.nextInt();
                                    }
                                    s.nextLine();
                                    check=a.reset_user_votes(election_id);
                                    if(check)
                                        System.out.println("Votes of election "+election_id+" reset successfull");
                                    else
                                        System.out.println("unable to reset votes");
                                    break;
                                default:
                                    System.out.println("Invalid input of choice 1");
                                    break;
                            }
                            System.out.println("Press 1 to continue 0 to return to Main Menu: ");
                            nx=s.nextInt();
                            s.nextLine();
                        }
                        break;
                    }
                    case 2: {
                        while(nx!=0) {
                            System.out.println("select\n1.Login\n2.TalkToAdmin\n3.ViewResult\n");
                            System.out.println("Enter choice : ");
                            try {
                                variable = s.nextInt();
                            }
                            catch(InputMismatchException e)
                            {
                                s.nextLine();
                                System.out.println("Enter the valid number");
                                variable=s.nextInt();
                            }
                            s.nextLine();
                            switch (variable) {
                                case 1:
                                    System.out.println("Enter the candidate PRN");
                                    try {
                                        PRN = s.nextInt();
                                    }catch(InputMismatchException e)
                                    {
                                        s.nextLine();
                                        System.out.println("Enter PRN as a integer rather than a string");
                                        PRN=s.nextInt();
                                    }
                                    s.nextLine();
                                    System.out.println("Enter the candidate password");
                                    password = s.nextLine();
                                    check = c.login(PRN, password);
                                    if (check)
                                        System.out.println("Login as candidate successfull");
                                    else
                                        System.out.println("unable to login as candidate");
                                    break;
                                case 2:
                                    a.addmessages(c.talkToAdmin());
                                    break;
                                case 3:
                                    System.out.println("Enter the election number");
                                    try {
                                        election_id = s.nextInt();
                                    }catch(InputMismatchException e)
                                    {
                                        s.nextLine();
                                        System.out.println("Enter the election_id as a number rather than string");
                                        election_id=s.nextInt();
                                    }
                                    s.nextLine();
                                    boolean check1 = a.is_result_available(election_id);
                                    if (check1) {
                                        winner_name = l.view_result(election_id);
                                        if (!winner_name.equals("null"))
                                            System.out.println("The winner of election " + election_id + " is " + winner_name);
                                        else
                                            System.out.println("Unable to display the winner name");
                                    } else
                                        System.out.println("Result not yet released");
                                    break;
                                default:
                                    System.out.println("Invalid input 2");
                                    break;
                            }
                            System.out.println("Press 1 to continue 0 to return to Main Menu: ");
                            nx=s.nextInt();
                            s.nextLine();
                        }
                        break;
                    }
                    case 3: {
                        while (nx!=0) {
                            System.out.println("select\n1.Register\n2.Login\n3.ViewInfo\n4.UpdatePassword\n5.TalkToAdmin\n6.CastVote\n7.ViewResult\n");
                            System.out.println("Enter choice : ");
                            try {
                                variable = s.nextInt();
                            }catch(InputMismatchException e)
                            {
                                s.nextLine();
                                System.out.println("Enter the valid integer input");
                                variable=s.nextInt();
                            }
                            s.nextLine();
                            switch (variable) {
                                case 1:
                                    System.out.println("Enter the following ");
                                    System.out.print("\nEnter the name of the user : ");
                                    name = s.nextLine();
                                    System.out.print("\nEnter the gender of the user : ");
                                    gender = s.nextLine();
                                    System.out.print("\nEnter the batch of the user : ");
                                    batch = s.nextLine();
                                    System.out.print("\nEnter the password of the user : ");
                                    password = s.nextLine();
                                    try {
                                        System.out.print("\nEnter the PRN of the user : ");
                                        PRN = s.nextInt();
                                    }catch(InputMismatchException e)
                                    {
                                        s.nextLine();
                                        System.out.println("Enter a number rather than string.");
                                        System.out.print("\nEnter the PRN of the user again : ");
                                        PRN = s.nextInt();
                                    }
                                    s.nextLine();
                                    try {
                                        System.out.print("\nEnter the age of the user : ");
                                        age = s.nextInt();
                                    }catch(InputMismatchException e)
                                    {
                                        s.nextLine();
                                        System.out.println("Enter a number rather than string.");
                                        System.out.print("\nEnter the age of the user again : ");
                                        age = s.nextInt();
                                    }
                                    s.nextLine();
                                    try {
                                        System.out.print("\nEnter the year of the user : ");
                                        year = s.nextInt();

                                    }catch(InputMismatchException e)
                                    {
                                        s.nextLine();
                                        System.out.println("Enter a number rather than string.");
                                        System.out.print("\nEnter the year of the user again : ");
                                        year= s.nextInt();
                                    }
                                    s.nextLine();
                                    check = db.register_user(name, gender, batch, password, PRN, age, year);
                                    if (check)
                                        System.out.println("User registered successfully");
                                    else
                                        System.out.println("unable to register user");
                                    break;
                                case 2:
                                    try {
                                        System.out.print("\nEnter your PRN : ");
                                        PRN = s.nextInt();
                                    }catch(InputMismatchException e)
                                {
                                    s.nextLine();
                                    System.out.println("Enter a number rather than string.");
                                    System.out.print("\nEnter the PRN of the user again : ");
                                    PRN = s.nextInt();
                                }
                                    s.nextLine();
                                    System.out.print("\nEnter your password : ");
                                    password = s.nextLine();
                                    check = db.verify_login(password, PRN);
                                    if (check)
                                        System.out.println("Login as user successful");
                                    else
                                        System.out.println("Unable to login");
                                    break;
                                case 3:
                                    System.out.println("Enter the PRN");
                                    PRN = s.nextInt();
                                    users = db.viewUserInfo(PRN);
                                    for (User user : users) {
                                        System.out.println("Hello");
                                        name = user.getName();
                                        gender = user.getGender();
                                        batch = user.getBatch();
                                        PRN = user.getPRN();
                                        age = user.getAge();
                                        year = user.getYear();
                                        System.out.print("The user details are");
                                        System.out.print("\nName : " + name);
                                        System.out.print("\nAge : " + age);
                                        System.out.print("\nGender : " + gender);
                                        System.out.print("\nYear : " + year);
                                        System.out.print("\nBatch : " + batch);
                                        System.out.print("\nPRN : " + PRN);
                                    }
                                    break;
                                case 4:
                                    System.out.println("Enter the PRN");
                                    PRN = s.nextInt();
                                    s.nextLine();
                                    System.out.println("Enter the old password");
                                    oldpassword = s.nextLine();
                                    System.out.println("Enter the password");
                                    password = s.nextLine();
                                    check = u.update_password(PRN, password, oldpassword);
                                    if (check)
                                        System.out.println("password updated successfully");
                                    else
                                        System.out.println("unable top update password");
                                    break;
                                case 5:
                                    a.addmessages(c.talkToAdmin());
                                    break;
                                case 6:
                                    try {
                                        System.out.println("Enter the user PRN");
                                        PRN = s.nextInt();
                                    }catch(InputMismatchException e)
                                {
                                    s.nextLine();
                                    System.out.println("Enter a number rather than string.");
                                    System.out.print("\nEnter the PRN of the user again : ");
                                    PRN = s.nextInt();
                                }
                                    s.nextLine();
                                        System.out.println("Enter the election number");
                                        int election_number = s.nextInt();
                                    s.nextLine();
                                    if (!a.is_result_available(election_number)) {
                                        db.show_candidates(election_number);
                                        System.out.println("Enter the candidate PRN");
                                        candidate_PRN = s.nextInt();
                                        s.nextLine();

                                        check = u.vote(PRN, candidate_PRN, election_number);
                                        System.out.println(check);
                                        if (check)
                                            System.out.println("Vote casted successfully");
                                        else
                                            System.out.println("unable to cast vote hehe");
                                    } else
                                        System.out.println("Election closed");
                                    break;
                                case 7:
                                    System.out.println("Enter the election number");
                                    election_id = s.nextInt();
                                    s.nextLine();
                                    boolean check1 = a.is_result_available(election_id);
                                    System.out.println(check1);
                                    if (check1) {
                                        winner_name = l.view_result(election_id);
                                        if (!winner_name.equals("null"))
                                            System.out.println("The winner of election " + election_id + " is " + winner_name);
                                        else
                                            System.out.println("Unable to display the winner name");
                                    } else
                                        System.out.println("Result not yet released");
                                    break;
                                default:
                                    System.out.println("Invalid input of choice 3");
                                    break;
                            }
                            System.out.println("Press 1 to continue 0 to return to Main Menu: ");
                            nx=s.nextInt();
                            s.nextLine();
                        }
                        break;
                    }
                    default:
                        System.out.println("Invalid input of choice 4");
                        break;
                }
                System.out.println("Press 1 to continue 0 to exit: ");
                nx=s.nextInt();
                s.nextLine();
            }
        }
       // db.closedb();
    }
}