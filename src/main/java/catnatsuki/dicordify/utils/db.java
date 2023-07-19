package catnatsuki.dicordify.utils;

import java.sql.*;


public class db {
    private final String DB_PATH = "./db/picks.db";

    private Connection connection;

    public static void main(String[] args){

    }

    public Connection connect(){
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+DB_PATH);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }
    public String existingRecord(String userID){
        String check = "SELECT userID"+" FROM additions WHERE userId = ?";
        try {
            Connection conn = connect();
            PreparedStatement pstm = conn.prepareStatement(check);
            pstm.setString(1, userID);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){
                conn.close();
                return "exists";
            }else {
                conn.close();
                return "missing";
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    public Integer getMonthlyCounter(String userId){
        String getCount = "SELECT monthlyCounter" +" FROM additions WHERE userId = ?";
        try {
            Connection conn = connect();
            PreparedStatement pstm = conn.prepareStatement(getCount);
            pstm.setString(1, userId);
            ResultSet rs = pstm.executeQuery();
            Integer ans=0;
            while (rs.next()){
                ans = rs.getInt("monthlyCounter");
            }
            conn.close();
            return ans;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public String insert(String userId, Integer monthlyCounter, Integer currentMonth, Integer currentYear){
        String sql = "INSERT INTO additions (userId, monthlyCounter, entryMonth, entryYear) VALUES(?, ?, ?, ?)";
//        String sqli = "SELECT name FROM sqlite_master WHERE type='table' AND name='adds';\n";
        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setInt(2, monthlyCounter);
            pstmt.setInt(3, currentMonth);
            pstmt.setInt(4, currentYear);
            pstmt.executeUpdate();
            conn.close();
            return "success";
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return "error";
        }
    }

    public Integer getMonth(String userId){
        String getMonth = "SELECT entryMonth" +" FROM additions WHERE userId = ?";
        try {
            Connection conn = connect();
            PreparedStatement pstm = conn.prepareStatement(getMonth);
            pstm.setString(1, userId);
            ResultSet rs = pstm.executeQuery();
            Integer ans=0;
            while (rs.next()){
                ans = rs.getInt("entryMonth");
            }
            conn.close();
            return ans;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public String updateRecord(String userId, Integer monthlyCounter){
        String update = "UPDATE additions SET monthlyCounter = ? "+"WHERE userId = ?";
        try{
            Connection conn = connect();
            PreparedStatement pstm = conn.prepareStatement(update);
            pstm.setInt(1, monthlyCounter);
            pstm.setString(2, userId);
            pstm.executeUpdate();
            conn.close();
            return "updated";
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return "failed to update";
        }
    }

    public Integer getYear(String userId){
        String getYear = "SELECT entryYear"+" FROM additions WHERE userId = ?";
        try {
            Connection conn = connect();
            PreparedStatement pstm = conn.prepareStatement(getYear);
            pstm.setString(1, userId);
            ResultSet rs = pstm.executeQuery();
            Integer ans=0;
            while (rs.next()){
                ans = rs.getInt("entryYear");
            }
            conn.close();
            return  ans;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public void deleteRec(String userId){
        String delCom = "DELETE FROM additions WHERE userId = ?";
        try {
            Connection conn = connect();
            PreparedStatement pstm = conn.prepareStatement(delCom);
            pstm.setString(1, userId);
            pstm.executeUpdate();
            conn.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

