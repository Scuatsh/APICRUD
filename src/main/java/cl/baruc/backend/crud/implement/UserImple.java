/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.baruc.backend.crud.implement;

import cl.baruc.backend.crud.database.Database;
import cl.baruc.backend.crud.interfaces.UserInter;
import cl.baruc.backend.crud.vo.UserVO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class UserImple implements UserInter {

    @Override
    public UserVO getUserByID(int id) {
        UserVO salida = new UserVO();
        String consulta = "SELECT * FROM user WHERE Id = ?";
        Database conSelect = new Database();
        if (conSelect != null) {
            try {
                PreparedStatement ps = conSelect.getConexion().prepareStatement(consulta);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    salida.setId(rs.getInt("ID"));
                    salida.setName(rs.getString("Name"));
                    salida.setMale(rs.getBoolean("Male"));
                    salida.setBrithday(rs.getDate("Birthday"));
                    salida.setDni(rs.getString("DNI"));
                    salida.setLastname(rs.getString("Lastname"));
                }
                ps.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }

        }
        return salida;
    }

    @Override
    public UserVO editUser(UserVO user) {
        String consulta = "UPDATE user SET Name=?,Lastname=?,Male=?,Birthday=?,Dni=? WHERE Id=?";
        Database con = new Database();
        if (con != null) {
            try {
                PreparedStatement ps = con.getConexion().prepareStatement(consulta);
                ps.setString(1, user.getName());
                ps.setString(2, user.getLastname());
                ps.setString(3, user.getMale().toString());
                ps.setDate(4, new java.sql.Date(user.getBrithday().getTime()));
                ps.setString(5, user.getDni());
                ps.setInt(6, user.getId());
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                System.out.println("Error: " + ex);
                System.out.println(ex);
            }
        }
        return user;
    }

    @Override
    public boolean deleteUser(UserVO user) {
        String consulta = "DELETE FROM user WHERE Id=?";
        Database con = new Database();
        if (con != null) {
            try {
                PreparedStatement ps = con.getConexion().prepareStatement(consulta);
                ps.setInt(1, user.getId());
                ps.executeUpdate();
                ps.close();
                return true;
            } catch (SQLException ex) {
                System.out.println("Error: " + ex);
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<UserVO> getAllUsers() {
        List<UserVO> users = new ArrayList<>();
        String consulta = "SELECT * FROM user";
        Database conSelect = new Database();
        if (conSelect != null) {
            try {
                PreparedStatement ps = conSelect.getConexion().prepareStatement(consulta);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    UserVO salida = new UserVO();
                    salida.setId(rs.getInt("ID"));
                    salida.setName(rs.getString("Name"));
                    salida.setMale(rs.getBoolean("Male"));
                    salida.setBrithday(rs.getDate("Birthday"));
                    salida.setDni(rs.getString("DNI"));
                    salida.setLastname(rs.getString("Lastname"));
                    users.add(salida);
                }
                ps.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }

        }
        return users;
    }

    @Override
    public UserVO createUser(UserVO user) {
        String consulta = "INSERT INTO `user` (`Name`, `Lastname`, `Male`, `Birthday`, `DNI`) VALUES (?,?,?,?,?);";
        Database con = new Database();
        if (con != null) {
            try {
                PreparedStatement ps = con.getConexion().prepareStatement(consulta);
                ps.setString(1, user.getName());
                ps.setString(2, user.getLastname());
                ps.setString(3, user.getMale().toString());
                ps.setDate(4, new java.sql.Date(user.getBrithday().getTime()));
                ps.setString(5, user.getDni());
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                System.out.println("Error: " + ex);
                System.out.println(ex);
            }
        }
        return user;
    }

}
