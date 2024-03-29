import javax.swing.*;
import java.sql.*;

public class AppModel {

    public void login(String userName, String password){
        String query = "SELECT user_name," +
                               "password " +
                        "FROM login_info " +
                        "WHERE user_name = " + "'" + userName + "'";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception exception){
            exception.printStackTrace();
        }
        try{
            Connection connection = DriverManager.getConnection(DatabaseInfo.url,DatabaseInfo.userNameDB,DatabaseInfo.passwordDB);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()){
                //validate username and password
                //resultSet.getString(1) is username in data base(login_info)
                //resultSet.getString(1) is password in data base(login_info)
                if (userName.equals(resultSet.getString(1)) && password.equals(resultSet.getString(2))){
                    System.out.println("login success");
                    JOptionPane.showMessageDialog(null,"You are now authorized to fully access the data","LOGIN SUCCESS",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println("login fail");
                    JOptionPane.showMessageDialog(null,"Nice try","LOGIN FAIL",JOptionPane.INFORMATION_MESSAGE);
                }
            } else{
                JOptionPane.showMessageDialog(null, "User name have not been created","Username not validate",JOptionPane.INFORMATION_MESSAGE);
            }
            System.out.println("end");
            statement.close();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }


    //REGISTER FUNCTION
    public void register(String userName, String password, String firstName, String lastName, String gender, String email, int phoneNumber, String address){
        if (isAccountExisted(userName)){
            JOptionPane.showMessageDialog(null, "Username already existed!","Username not validate",JOptionPane.WARNING_MESSAGE);

        } else {
            //store username and password into login_info
            String addLoginInfoQuery = "INSERT INTO login_info (user_name, password) " +
                    "VALUES(" + "'" + userName + "'" + ","+
                    "'" + password + "'" +
                    ")";
            System.out.println(addLoginInfoQuery);
            //store user's personal information in user_info
            String addUserQuery = "INSERT INTO user_info (first_name, last_name, gender, email, phone, address) " +
                    "VALUES(" +   "'" + firstName + "'" +","+
                    "'" + lastName+ "'" + ","+
                    "'" + gender + "'" + ","+
                    "'" + email + "'" + ","+
                    phoneNumber  + ","+
                    "'" + address + "'" +
                    ")";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            }catch (Exception ex){
                ex.printStackTrace();
            }
            try {
                Connection connection = DriverManager.getConnection(DatabaseInfo.url,DatabaseInfo.userNameDB,DatabaseInfo.passwordDB);
                Statement statement = connection.createStatement();
                statement.execute(addLoginInfoQuery);
                statement.execute(addUserQuery);
                statement.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    public boolean isAccountExisted(String userName){
        boolean isExist = false;
        String query = "SELECT user_name," +
                               "password " +
                        "FROM login_info " +
                        "WHERE user_name = " + "'" + userName + "'";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.userNameDB, DatabaseInfo.passwordDB);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                isExist = true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return isExist;
    }
}
