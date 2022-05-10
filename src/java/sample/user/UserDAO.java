/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sample.utils.DBUtils;

/**
 *
 * @author Hoang Tam
 */
public class UserDAO {
    private static final String LOGIN = "SELECT userID"
            + " FROM tblUsers"
            + " WHERE userId=? AND password=? ";
    private static final String SEARCH = "SELECT userID, fullName, roleID"
            + " FROM tblUsers"
            + " WHERE fullname like ? ";
    public boolean checkLogin(String userID, String password) throws SQLException{
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs =null;
        try {
            conn = DBUtils.getConnection();
            if(conn != null){
                ptm= conn.prepareStatement(LOGIN);
                ptm.setString(1, userID);
                ptm.setString(2, password);
                rs =ptm.executeQuery();
                if(rs.next()){
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(rs!=null) rs.close();
            if(ptm!=null) ptm.close();
            if(conn!=null) conn.close();
        }
        return result;
    }
    public List<UserDTO> getListUser(String search) throws SQLException{
        List<UserDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs =null;
        try {
            conn = DBUtils.getConnection();
            if(conn != null){
                ptm= conn.prepareStatement(SEARCH);
                ptm.setString(1, "%"+search+"%");
                rs= ptm.executeQuery();
                while(rs.next()){
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    String password = "***";
                    list.add(new UserDTO(userID, fullName, roleID, password));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(rs!=null) rs.close();
            if(ptm!=null) ptm.close();
            if(conn!=null) conn.close();
        }
        return list;
    }
}
