package PBL_PROJECT;

import java.sql.*;
import java.util.*;

public class DataBase {
    public static final String connection_string = "jdbc:sqlite:C:\\Users\\dhara\\Desktop\\Online_voting1\\basedata.db";
    Connection connection;

    //Variables for userData table
    public static final String table_user_data = "userData";
    public static final String column_user_name = "name";
    public static final String column_user_password = "password";
    public static final String column_user_batch = "batch";
    public static final String column_user_gender = "gender";
    public static final String column_user_PRN = "PRN";
    public static final String column_user_age = "age";
    public static final String column_user_year = "year";
    public static final String column_user_vote = "candidate_no";
    public static final String column_user_election_number = "election_no";

    //Variables for election table
    public static final String table_election = "election";
    public static final String column_election_name = "Election_name";
    public static final String column_election_id = "_id";
    public static final String column_election_candidate = "winner";
    public static final String table_candidate = "candidate";

    //variables for candidate table
    public static final String column_candidate_PRN = "PRN";
    public static final String column_candidate_name = "name";
    public static final String column_candidate_password = "password";
    public static final String column_candidate_symbol = "symbol";
    public static final String column_candidate_ele_id = "election_id";




    //Statments to work on userData table
    public static final String Create_user_table = "create table if not exists " + table_user_data + "(" + column_user_PRN + " integer primary key," + column_user_password
            + "text," + column_user_name + " text ," + column_user_batch + " text ,"
            + column_user_gender + " text ," + column_user_age + " integer," + column_user_year + " integer," + column_user_vote + " integer," +
            column_user_election_number + " integer)";

    public static final String reset_user = "update " + table_user_data + " set " + column_user_vote + "=?," + column_user_election_number + "=? where " + column_user_election_number + "=?";
    PreparedStatement reset_user_prep;

    public static final String query_user_to_vote = "select " + column_user_vote + " from " + table_user_data + " where " + column_user_PRN + "=?";
    PreparedStatement query_user_to_vote_prep;




    //Statments related to candidate table
    public static final String create_candidate_table = "create table if not exists " + table_candidate + " (" + column_candidate_PRN + " integer primary key, " +
            column_candidate_name + " text not null, " + column_candidate_password + " text not null," + column_candidate_symbol + " text not null," +
            column_candidate_ele_id + " integer not null)";

    public static final String count_candidates = "select count(*) from " + table_candidate + " where " + column_candidate_ele_id + "=?";
    PreparedStatement count_candidates_prep;



    //Statments related to election table
    public static final String insert_into_election_table = "Insert into " + table_election + " values( ?,?,?)";
    PreparedStatement insert_into_election_table_prep;

    public static final String create_election_table = "create table if not exists " + table_election + " (" + column_election_id + " integer primary key, " +
            column_election_name + " text not null, " + column_election_candidate + " integer )";

    public static final String delete_election = "delete from " + table_election + " where " + column_election_id + "=?";
    PreparedStatement delete_election_prep;




    //Open and close functions for database
    public boolean open() //function 1
    {
        try {
            connection = DriverManager.getConnection(connection_string);
            Statement statement = connection.createStatement();
            statement.execute(Create_user_table);
            statement.execute(create_election_table);
            statement.execute(create_candidate_table);
            return true;

        } catch (SQLException e) {
            System.out.println("Unable to open database : " + e.getMessage());
            return false;
        }
    }

    public void closedb() //function 2
    {
        try {
            if (reset_user_prep != null)
                reset_user_prep.close();
            if (show_candidates_prep != null)
                show_candidates_prep.close();
            if (declare_winner_prep != null)
                declare_winner_prep.close();
            if (update_election_winner_prep != null)
                update_election_winner_prep.close();
            if (get_candidate_no_prep != null)
                get_candidate_no_prep.close();
            if (count_candidates_prep != null)
                count_candidates_prep.close();
            if (delete_election_prep != null)
                delete_election_prep.close();
            if (delete_candidate_prep != null)
                delete_candidate_prep.close();
            if (candidate_login_prep != null)
                candidate_login_prep.close();
            if (candidate_login_prep != null)
                candidate_login_prep.close();
            if (insert_into_candidate_table_prep != null)
                insert_into_candidate_table_prep.close();
            if (insert_into_election_table_prep != null)
                insert_into_election_table_prep.close();
            if (update_user_password_prep != null)
                update_user_password_prep.close();
            if (query_user_table_for_PRN_prep != null)
                query_user_table_for_PRN_prep.close();
            if (delete_user_prep != null)
                delete_user_prep.close();
            if (register_user_table_prep != null)
                register_user_table_prep.close();
            if (login_user_prep != null)
                login_user_prep.close();
            if (view_user_table_prep != null)
                view_user_table_prep.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.out.println("Unable to close the database : " + e.getMessage());
        }
    }



    //Functions related to user
//Function to view userInfo
    public static final String view_user_table_info = "select * from " + table_user_data + " where " + column_user_PRN + " = ?";
    PreparedStatement view_user_table_prep;
    public ArrayList<User> viewUserInfo(int PRN) {
        try {
            Statement statement = connection.createStatement();
            view_user_table_prep = connection.prepareStatement(view_user_table_info);
            view_user_table_prep.setInt(1, PRN);
            ResultSet result = view_user_table_prep.executeQuery();
            ArrayList<User> users = new ArrayList<User>();
            while (result.next()) {
                User user = new User();
                user.setPRN(result.getInt(1));
                user.setPassword(result.getString(2));
                user.setName(result.getString(3));
                user.setBatch(result.getString(4));
                user.setGender(result.getString(5));
                user.setAge(result.getInt(6));
                user.setYear(result.getInt(7));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            System.out.println("Unable to retrieve data : " + e.getMessage());
            return null;
        }
    }


//Function for user login
public static final String login_user = "select " + column_user_PRN + "," + column_user_password + " from " + table_user_data + " where " + column_user_PRN + "=?";
    PreparedStatement login_user_prep;
    public boolean verify_login(String password, int PRN) {

        try {
            Statement statement = connection.createStatement();
            login_user_prep = connection.prepareStatement(login_user);
            login_user_prep.setInt(1, PRN);
            ResultSet result = login_user_prep.executeQuery();
            if (PRN == result.getInt(1)) {
                if (password.equals(result.getString(2)))
                    return true;
                else
                    return false;
            } else
                return false;
        } catch (SQLException e) {
            System.out.println("Unable to login : " + e.getMessage());
            return false;
        }
    }


//Function for user registration
public static final String register_user_table = "Insert into " + table_user_data + " values( ?,?,?,?,?,?,?,?,?)";
    PreparedStatement register_user_table_prep;
    public boolean register_user(String name, String gender, String batch, String password, int PRN, int age, int year) {
        try {
            Statement statement = connection.createStatement();
            register_user_table_prep = connection.prepareStatement(register_user_table);
            register_user_table_prep.setInt(1, PRN);
            register_user_table_prep.setString(2, password);
            register_user_table_prep.setString(3, name);
            register_user_table_prep.setString(4, batch);
            register_user_table_prep.setString(5, gender);
            register_user_table_prep.setInt(6, age);
            register_user_table_prep.setInt(7, year);
            register_user_table_prep.setInt(8, 0);
            register_user_table_prep.setInt(9, 0);
            //statement.execute(register_user_table);
            int result = register_user_table_prep.executeUpdate();
            if (result != 1)
                return false;
            else
                return true;
        } catch (SQLException e) {
            System.out.println("Unable to register : " + e.getMessage());
            return false;
        }
    }


//Function to delete user account
public static final String delete_user = "delete from " + table_user_data + " where " + column_user_PRN + "=?";
    PreparedStatement delete_user_prep;
    public static final String query_user_table_for_PRN = "select " + column_user_PRN + " from " + table_user_data + " where " + column_user_PRN + "=?";
    PreparedStatement query_user_table_for_PRN_prep;
    public boolean user_acc_delete(int PRN, String password) {
        try {
            Statement statement = connection.createStatement();
            query_user_table_for_PRN_prep = connection.prepareStatement(query_user_table_for_PRN);
            query_user_table_for_PRN_prep.setInt(1, PRN);
            ResultSet result = query_user_table_for_PRN_prep.executeQuery();
            if (!result.next())
                return false;
            else {
                delete_user_prep = connection.prepareStatement(delete_user);
                delete_user_prep.setInt(1, PRN);
                int affected_rows = delete_user_prep.executeUpdate();
                if (affected_rows == 0)
                    return false;
                else
                    return true;
            }

        } catch (SQLException e) {
            System.out.println("Unable to delete the user account" + e.getMessage());
            return false;
        }
    }


//Function to update user password
public static final String update_user_password = "update " + table_user_data + " set " + column_user_password + " =? where " + column_user_password + "=? and " +
        column_user_PRN + "=?";
    PreparedStatement update_user_password_prep;
    public boolean updateUserPassword(int PRN, String new_password, String old_password) {
        try {
            query_user_table_for_PRN_prep = connection.prepareStatement(query_user_table_for_PRN);
            query_user_table_for_PRN_prep.setInt(1, PRN);
            ResultSet result = query_user_table_for_PRN_prep.executeQuery();
            if (result.next()) {
                update_user_password_prep = connection.prepareStatement(update_user_password);
                update_user_password_prep.setString(1, new_password);
                update_user_password_prep.setString(2, old_password);
                update_user_password_prep.setInt(3, PRN);
                int affected_rows = update_user_password_prep.executeUpdate();
                if (affected_rows != 1)
                    return false;
                else
                    return true;
            } else
                return false;

        } catch (SQLException e) {
            System.out.println("Unable to update user password : " + e.getMessage());
            return false;
        }
    }
//Function to cast user vote
public static final String cast_vote = "update " + table_user_data + " set " + column_user_vote + "=?," + column_user_election_number + "=?"
        + " where " + column_user_PRN + "=?";
    PreparedStatement cast_vote_prep;
    public boolean cast_user_vote(int PRN, int vote, int election_number) {
        try {
            query_user_to_vote_prep = connection.prepareStatement(query_user_to_vote);
            query_user_to_vote_prep.setInt(1, PRN);
            ResultSet result = query_user_to_vote_prep.executeQuery();
            int ans = result.getInt(1);
            System.out.println(ans);
            if (ans == 0) {
                System.out.println(cast_vote);
                cast_vote_prep = connection.prepareStatement(cast_vote);
                cast_vote_prep.setInt(1, vote);
                cast_vote_prep.setInt(2, election_number);
                cast_vote_prep.setInt(3, PRN);
                int results = cast_vote_prep.executeUpdate();
                if (results == 1) {
                    return true;
                } else {
                    return false;
                }
            } else
                return false;
        } catch (SQLException e) {
            System.out.println("Unable to vote  : " + e.getMessage());
            return false;
        }
    }





    //functions related to candidate
    //Function to register a candidate
    public static final String insert_into_candidate_table = "Insert into " + table_candidate + " values( ?,?,?,?,?)";
    PreparedStatement insert_into_candidate_table_prep;
    public boolean register_candidate(int PRN, String name, String password, String symbol, int election_id) {
        try {
            insert_into_candidate_table_prep = connection.prepareStatement(insert_into_candidate_table);
            insert_into_candidate_table_prep.setInt(1, PRN);
            insert_into_candidate_table_prep.setString(2, name);
            insert_into_candidate_table_prep.setString(3, password);
            insert_into_candidate_table_prep.setString(4, symbol);
            insert_into_candidate_table_prep.setInt(5, election_id);
            int affected_rows = insert_into_candidate_table_prep.executeUpdate();
            if (affected_rows == 1)
                return true;
            else
                return false;
        } catch (SQLException e) {
            System.out.println("Unable to register : " + e.getMessage());
            return false;
        }
    }

    //Candidate login verify function
    public static final String candidate_login = "select " + column_candidate_PRN + "," + column_candidate_password + " from " +
            table_candidate + " where " + column_candidate_PRN + "=?";
    PreparedStatement candidate_login_prep;
    public boolean candidate_verify_login(int PRN, String password) {
        try {
            candidate_login_prep = connection.prepareStatement(candidate_login);
            candidate_login_prep.setInt(1, PRN);
            ResultSet results = candidate_login_prep.executeQuery();
            if (PRN == results.getInt(1)) {
                if (password.equals(results.getString(2)))
                    return true;
                else
                    return false;
            } else
                return false;
        } catch (SQLException e) {
            System.out.println("Unable to login as a candidate : " + e.getMessage());
            return false;
        }
    }


//Function to show available candidates to vote
public static final String show_candidates = "select " + column_candidate_name + "," + column_candidate_PRN + " from " + table_candidate
        + " where " + column_candidate_ele_id + "=? order by " + column_candidate_PRN;
    PreparedStatement show_candidates_prep;
    public void show_candidates(int election_no) {
        try {
            show_candidates_prep = connection.prepareStatement(show_candidates);
            show_candidates_prep.setInt(1, election_no);
            ResultSet result = show_candidates_prep.executeQuery();
            System.out.println("Candidate details");
            while (result.next()) {
                int no = result.getInt(2);
                String name = result.getString(1);
                System.out.println("PRN : " + no + " Name : " + name);
            }

        } catch (SQLException e) {
            System.out.println("Unable to display camdidates : " + e.getMessage());
        }
    }


    //Function to delete candidate entry
    public static final String delete_candidate = "delete from " + table_candidate + " where " + column_candidate_PRN + "=?";
    PreparedStatement delete_candidate_prep;
    public boolean delete_candidate_acc(int PRN) {
        try {
            delete_candidate_prep = connection.prepareStatement(delete_candidate);
            delete_candidate_prep.setInt(1, PRN);
            int affected_rows = delete_candidate_prep.executeUpdate();
            if (affected_rows == 1)
                return true;
            else
                return false;
        } catch (SQLException e) {
            System.out.println("Unable to delete candidate account : " + e.getMessage());
            return false;
        }
    }

    public boolean delete_election_acc(int _id) {
        try {
            delete_election_prep = connection.prepareStatement(delete_election);
            delete_election_prep.setInt(1, _id);
            int affected_rows = delete_election_prep.executeUpdate();
            if (affected_rows == 1)
                return true;
            else
                return false;
        } catch (SQLException e) {
            System.out.println("Unable to delete the election entry : " + e.getMessage());
            return false;
        }
    }



    public boolean create_election(int _id, String name) {
        try {
            insert_into_election_table_prep = connection.prepareStatement(insert_into_election_table);
            insert_into_election_table_prep.setInt(1, _id);
            insert_into_election_table_prep.setString(2, name);
            int affected_rows = insert_into_election_table_prep.executeUpdate();
            if (affected_rows == 1)
                return true;
            else
                return false;
        } catch (SQLException e) {
            System.out.println("Unable to create election : " + e.getMessage());
            return false;
        }
    }



    //Function to get candidate id from table
    public static final String get_candidate_no = "select " + column_candidate_PRN + " from " + table_candidate + " where " + column_candidate_ele_id + "=?";
    PreparedStatement get_candidate_no_prep;
    public ArrayList<Integer> get_candidate_id(int election_id) {
        try {
            get_candidate_no_prep = connection.prepareStatement(get_candidate_no);
            get_candidate_no_prep.setInt(1, election_id);
            ResultSet result = get_candidate_no_prep.executeQuery();
            ArrayList<Integer> count = new ArrayList<>();
            while (result.next()) {
                int no = result.getInt(1);
                count.add(no);
            }
            return count;
        } catch (SQLException e) {
            System.out.println("Unable to return candidate numbers : " + e.getMessage());
            return null;
        }
    }


//Function to return the winner number
public static final String check_election_for_winner = "select " + column_election_candidate + " from " + table_election +
        " where " + column_election_id + "=?";
    PreparedStatement check_election_for_winner_prep;
    public int calculate_result(int election_id,int key) {
        ArrayList<Integer> candidates = get_candidate_id(election_id);
        try {
            check_election_for_winner_prep = connection.prepareStatement(check_election_for_winner);
            check_election_for_winner_prep.setInt(1, election_id);
            ResultSet r = check_election_for_winner_prep.executeQuery();
            String get_data = "select " + column_user_vote + " from " + table_user_data + " where " + column_user_election_number + "=?";
            PreparedStatement get_data_prep = connection.prepareStatement(get_data);
            get_data_prep.setInt(1, election_id);
            ResultSet result = get_data_prep.executeQuery();
            ArrayList<Integer> votes = new ArrayList<>();
            if (r.next()) {
                int win = r.getInt(1);
                if (win != 0 && key==0)
                    return -2;
                else {
                    for (int i = 0; i < candidates.size(); i++) {
                        votes.add(0);
                    }
                    while (result.next()) {
                        int vote = result.getInt(1);
                        int index_vote = candidates.indexOf(vote);
                        int value_at_index = votes.get(index_vote) + 1;
                        votes.add(index_vote, value_at_index);
                    }
                    int highest = 0;
                    for (int c : votes) {
                        if (c > highest)
                            highest = c;
                    }
                    int winner = votes.indexOf(highest);
                    int candidate_winner = candidates.get(winner);
                    return candidate_winner;
                }
            } else
                return -1;
        } catch (SQLException e) {
            System.out.println("unable to calculate result : " + e.getMessage());
            return -1;
        }
    }


//Function to update election table with winner name
public static final String update_election_winner = "update " + table_election + " set " + column_election_candidate + "=? where " + column_election_id + "=?";
    PreparedStatement update_election_winner_prep;
    public boolean update_election(int winner, int election_id) {
        try {
            update_election_winner_prep = connection.prepareStatement(update_election_winner);
            update_election_winner_prep.setInt(1, winner);
            update_election_winner_prep.setInt(2, election_id);
            int ans = update_election_winner_prep.executeUpdate();
            if (ans == 1)
                return true;
            else
                return false;
        } catch (SQLException e) {
            System.out.println("unable to update winner in election : " + e.getMessage());
            return false;
        }
    }


//Function to display winner name
public static final String decalare_winner = "select " + column_candidate_name + " from " + table_candidate + " where " + column_candidate_PRN + "=? and " +
        column_candidate_ele_id + "=?";
    PreparedStatement declare_winner_prep;

    public String view_winner(int election_id) {
      //  public static final String decalare_winner = "select " + column_candidate_name + " from " + table_candidate + " where " + column_candidate_PRN + "=? and " +
       //         column_candidate_ele_id + "=?";
        try {
            declare_winner_prep = connection.prepareStatement(decalare_winner);
            int winner_no = calculate_result(election_id,1);
            System.out.println("winner no is :"+winner_no);
            declare_winner_prep.setInt(1, winner_no);
            declare_winner_prep.setInt(2, election_id);
            ResultSet result = declare_winner_prep.executeQuery();
            if (result.next()) {
                String ans = result.getString(1);
                return ans;
            } else
                return "null";
        } catch (SQLException e) {
            System.out.println("Unable to display result : " + e.getMessage());
            return "null";
        }
    }


//Function to reset votes when election is closed
    public void reset_user_vote(int election_id) {
        try {
            reset_user_prep = connection.prepareStatement(reset_user);
            reset_user_prep.setInt(1, 0);
            reset_user_prep.setInt(2, 0);
            reset_user_prep.setInt(3, election_id);
            int affected_rows = reset_user_prep.executeUpdate();
            if (affected_rows >= 1)
                System.out.println("User votes resetted successfully");
            else
                System.out.println("unable to reset the user votes");
        } catch (SQLException e) {
            System.out.println("Unable to reset votes : " + e.getMessage());
        }

    }


//Function to check whether a user can vote or not if election is closed by the admi
    public boolean can_vote(int election_id) {
        try {
            check_election_for_winner_prep = connection.prepareStatement(check_election_for_winner);
            check_election_for_winner_prep.setInt(1, election_id);
            ResultSet r = check_election_for_winner_prep.executeQuery();
            if (r.next()) {
                if (r.getInt(1) != 0)
                    return true;
                else
                    return false;
            } else
                return false;

        } catch (SQLException e) {
            System.out.println("Unable to get data : " + e.getMessage());
            return false;
        }
    }

    //Function to count no of candidates in the election
    public int count_candidates(int ele_id) {
        try {
            count_candidates_prep = connection.prepareStatement(count_candidates);
            count_candidates_prep.setInt(1, ele_id);
            ResultSet result = count_candidates_prep.executeQuery();
            if (result.next())
                return result.getInt(1);
            else
                return -1;
        } catch (SQLException e) {
            System.out.println("Unable to count the candidates");
            return -1;
        }
    }
}
