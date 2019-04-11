/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.baruc.backend.crud.interfaces;

import cl.baruc.backend.crud.vo.UserVO;
import java.util.List;

/**
 *
 * @author bromerov
 */
public interface UserInter {

    public UserVO getUserByID(int id);

    public UserVO editUser(UserVO user);

    public UserVO createUser(UserVO user);

    public boolean deleteUser(UserVO user);

    public List<UserVO> getAllUsers();

}
