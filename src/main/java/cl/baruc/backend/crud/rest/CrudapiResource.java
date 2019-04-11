/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.baruc.backend.crud.rest;

import cl.baruc.backend.crud.implement.UserImple;
import cl.baruc.backend.crud.vo.UserResponse;
import cl.baruc.backend.crud.vo.UserVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author bromerov
 */
@Path("crudapi")
public class CrudapiResource {

    @Context
    private UriInfo context;
    private final UserImple mple = new UserImple();

    /**
     * Creates a new instance of CrudapiResource
     */
    public CrudapiResource() {
    }

    /**
     * Retrieves representation of an instance of
     * cl.baruc.backend.crud.database.CrudapiResource
     *
     * @return an instance of
     * com.sun.org.apache.xpath.internal.operations.String
     */
    @GET
    @Produces("text/plain")
    public String getJson() {
        return "WebService Online";
    }

    @POST
    @Consumes("application/json")
    @Path("/editUser")
    public Response editUser(UserVO usr) {
        UserVO user = mple.editUser(usr);
        UserResponse response = new UserResponse();
        if (user.getId() > 0) {
            response.setUser(user);
            response.setCode(200);
            response.setMessage("Edit");
            return Response.ok(response).build();
        } else {
            response.setCode(400);
            response.setMessage("Sin usuarios");
            return Response.ok(response).build();
        }

    }

    @POST
    @Consumes("application/json")
    @Path("/getUserById")
    public Response getUserById(UserVO usr) {
        UserVO userByID;
        UserResponse response = new UserResponse();
        userByID = mple.getUserByID(usr.getId());
        if (userByID.getId() > 0) {
            response.setUser(userByID);
            response.setCode(200);
            response.setMessage("Find");
            return Response.ok(response).build();
        } else {
            response.setCode(400);
            response.setMessage("Usuario no encontrado");
            return Response.ok(response).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Path("/getAllUsers")
    public Response getAllUsers() {
        List<UserVO> listUser = mple.getAllUsers();
        UserResponse response = new UserResponse();
        if (!listUser.isEmpty()) {
            response.setUsers(new ArrayList<UserVO>());
            response.getUsers().addAll(listUser);
            response.setCode(200);
            response.setMessage("Find");
            return Response.ok(response).build();
        } else {
            response.setCode(400);
            response.setMessage("Sin usuarios");
            return Response.ok(response).build();
        }

    }

    @POST
    @Consumes("application/json")
    @Path("/createUser")
    public Response createUser(@Valid UserVO usr) {
        UserResponse response = new UserResponse();
        HashMap<String, String> validateUserVO = validateUserVO(usr);
        if (validateUserVO.isEmpty()) {
            try {
                response.setUser(mple.createUser(usr));
                response.setCode(200);
                response.setMessage("Usuario creado exitosamente");
                return Response.ok(response).build();
            } catch (Exception e) {
                System.out.println("Error " + e);
                response.setCode(400);
                response.setMessage(e.toString());
                return Response.ok(response).build();
            }
        } else {
            List<String> errorText = new ArrayList<>();
            for (Map.Entry<String, String> entrySet : validateUserVO.entrySet()) {
                String value = entrySet.getValue();
                errorText.add(value);
                System.out.println(value);
            }
            response.setCode(400);
            response.setMessage(errorText.toString());
            return Response.ok(response).build();
        }

    }

    @POST
    @Consumes("application/json")
    @Path("/deleteUser")
    public Response deleteUser(@Valid UserVO usr) {
        UserResponse response = new UserResponse();
        HashMap<String, String> validateUserVO = validateUserVO(usr);
        if (validateUserVO.isEmpty()) {
            try {
                response.setCode(200);
                response.setMessage(Boolean.toString(mple.deleteUser(usr)));
                return Response.ok(response).build();
            } catch (Exception e) {
                System.out.println("Error " + e);
                response.setCode(400);
                response.setMessage(e.toString());
                return Response.ok(response).build();
            }
        } else {
            List<String> errorText = new ArrayList<>();
            for (Map.Entry<String, String> entrySet : validateUserVO.entrySet()) {
                String value = entrySet.getValue();
                errorText.add(value);
                System.out.println(value);
            }
            response.setCode(400);
            response.setMessage(errorText.toString());
            return Response.ok(response).build();
        }

    }

    public HashMap<String, String> validateUserVO(UserVO usr) {
        HashMap<String, String> validate = new HashMap<>();
        try {
            if (usr.getName() == null) {
                validate.put("name", "Field Name is empty");
            }
            if (usr.getLastname() == null) {
                validate.put("lastname", "Field Lastname is empty");
            }
            if (usr.getBrithday() == null) {
                validate.put("Brithday", "Field Birthday is empty");
            }
            if (usr.getMale() == null) {
                validate.put("Male", "Field Male is empty");
            }
            if (usr.getDni() == null) {
                validate.put("Dni", "Field Dni is empty");
            }
        } catch (Exception e) {
            validate.put("Error", e.toString());
        }
        return validate;
    }

}
