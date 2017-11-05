package com.mscheduler.controller;

import com.mscheduler.Utilities;
import com.mscheduler.model.User;
import com.mscheduler.view.user.*;
import java.io.IOException;

import java.util.Objects;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscheduler.Config;
import com.mscheduler.model.Invitation;
import com.mscheduler.model.User;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserController extends AbstractController {

    int maxPage;

    //singleton + constructor
        private static UserController instance;

        static{
            instance = new UserController();
        }

        public static UserController getInstance(){
          return instance;
        }
        
        private UserController() {
            super();
        }
    //end of singleton

    public List<User> loadUser() {
        try {
            ArrayList<User> user_list = mapper.readValue(new File(Config.DATA_USER_INDEX), new TypeReference<ArrayList<User>>() {
            });
            return user_list;
        } catch (IOException ex) {
            Logger.getLogger(InvitationController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<User> listUser() {
        //Kamus
        String email;
        List<Integer> list;
        List<User> result;
        String meeting_title;
        User user_email;

        //Algoritma
        email = (this.getSession() != null) ? this.getSession().getEmail() : "";
        result = new ArrayList<>();

        result = this.loadUser();
        return result;
    }

    public String listUsersAll() {
        User usr = new User();
        return usr.readAll();
    }
    
    public String listUserAll(int page) {
        int i,LastIndex;
        String str = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            LastIndex = 0;

            String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(LastIndex);
            List<User> list = this.loadUser();

            i = LastIndex;
            for (User u : list) {
                str += u.getEmail() + "\n";
                i--;
            }

            System.out.println(list);

            this.maxPage = LastIndex;
            str = "";
            if (this.maxPage == 1) {
                str = Utilities.getPage(str, 1) + "\n";
                UserController usr = new UserController();
            } else {
                str = "-----------\n"
                        + "Page " + page + " of " + this.maxPage + "\n"
                        + "-----------\n";

                if (page < this.maxPage) {
                    str += Utilities.getPage(str, page) + "\n"
                            + "----more----\n";
                } else {
                    str += Utilities.getPage(str, page) + "\n";
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }

    public boolean addUser(User usr) {
        boolean flag;
        flag = usr.saving();
        return flag;
    }

    public String checkLogin(String email, String pass) {
        String stat;
        User usr = new User();

        usr.setEmail(email);
        usr.setPassword(pass);
        stat = usr.checkLoginDb(usr);

        return stat;
    }

    public boolean editUser(String email, User user) {
        return false;
    }

    public int userEditAll(String email, User usr) {
        //kamus
        int stat;
        User dbUser;

        //init
        dbUser = new User();
        dbUser.readUserOne(email);
        stat = 0;

        //algo
        if (dbUser.editAll(usr)) {
            stat = 1;
        } else {
            stat = 0;
        }

        return stat;
    }

    public int userEditOne(String email, String condition, User usr) {
        //kamus
        int stat;
        User dbUser;

        //init
        dbUser = new User();
        dbUser.readUserOne(email);
        stat = 0;

        //algo
        if (condition.equalsIgnoreCase("email")) {
            stat = 2;
        } else if (dbUser.editOne(condition,usr)) {
            stat = 1;
        } else {
            stat = 0;
        }

        return stat;
    }

    public int intEditUser(String email) {
        //0 not found, 1 data saved, 2email cant,unknown
        //name,email,pass,email(2)cant
        //kamus 
        User usr = new User();
        int stat;

        //algo
        stat = 0;
        usr.del();

        return stat;
    }

    public int delUser(String email) {
        int stat;
        User usr = new User();
        usr = usr.readUserOne(email);
        stat = 0;

        if (this.existUser(email)) {
            if (usr.lastAdmin()) {
                stat = 2;
            } else {
                if (usr.del()) {
                    //checkdel
                    stat = 1;
                } else {
                    stat = 3;
                }
            }
        } else {
            stat = 0;
        }

        return stat;
    }

    public User login(String email, String password) {
        User usr = new User();
        if (existUser(email)) {
            if (usr.BooleanReadPass(email,password)) {
                usr.readUserOne(email);
            }
        }
        return usr;
    }

    private User findUser(String email) {       //cari user
        User usr = new User();
        if (existUser(email)) {
            usr.readUserOne(email);
        }
        return usr;
    }
    
    public User getUserByEmail(String email) {
        return this.loadUser().stream().filter(x->x.getEmail().equals(email)).findFirst().orElse(null);
    }

    public boolean existUser(String email) {   //sudah ada
        User usr = new User();
        return usr.BooleanReadUser(email);
    }
}
