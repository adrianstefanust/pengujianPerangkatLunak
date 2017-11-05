package com.mscheduler.model;

import java.util.Objects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscheduler.Config;
import java.security.InvalidKeyException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    
    private String email;
    private String password;
    private String name;
    private boolean isAdmin;

    public User() {
        email = "";
        password = "";
        name = "";
        isAdmin = false;
    }

    public User(String email) {
        password = "";
        name = "";
        isAdmin = false;
        this.email = email;
    }

    public User(String email, String password, String name, boolean isAdmin) {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean saving() {
        boolean flag = false;
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<User> newUser = mapper.readValue(new File(Config.DATA_USER_INDEX), new TypeReference<List<User>>() {
            });
            this.setPassword(this.encr(this.getPassword()));
            newUser.add(this);
            mapper.writeValue(new File(Config.DATA_USER_INDEX), newUser);
            flag = true;
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public User readUser() {             // access overall
        ObjectMapper mapper = new ObjectMapper();
        User newUser;
        newUser = new User();
        try {
            newUser = mapper.readValue(new File(Config.DATA_USER_INDEX), User.class);
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newUser;
    }

    public boolean BooleanReadUser(String input) {
        String id;
        boolean stat;
        ObjectMapper mapper = new ObjectMapper();
        id = "-1";
        stat = false;
        try {
            List<User> newUser = mapper.readValue(new File(Config.DATA_USER_INDEX), new TypeReference<List<User>>() {
            });

            for (User u : newUser) {
                if (input.equals(u.getEmail())) {
                    id = u.getEmail() + "";
                }
            }
            if (!id.equals("-1")) {   //jika user ada stat = true
                stat = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stat;
    }

    public boolean BooleanReadPass(String email, String input) {
        String id;
        boolean stat;
        ObjectMapper mapper = new ObjectMapper();
        id = "-1";
        stat = false;
        try {
            List<User> newUser = mapper.readValue(new File(Config.DATA_USER_INDEX), new TypeReference<List<User>>() {
            });

            for (User u : newUser) {
                if (email.equals(u.getEmail()) && this.encr(input).equals(u.getPassword()) ) {
                    id = u.getEmail() + "";
                }
            }
            if (!id.equals("-1")) {
                stat = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stat;
    }

    public User readUserOne(String email) {
        User users;
        ObjectMapper mapper = new ObjectMapper();
        users = new User();
        try {
            List<User> newUser = mapper.readValue(new File(Config.DATA_USER_INDEX), new TypeReference<List<User>>() {
            });

            for (User u : newUser) {
                if (u.getEmail().equalsIgnoreCase(email)) {
                    users = u;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;

    }

    public String encr(String text) {
        String enc;
        enc = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            enc = hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return enc;
    }

    public String checkLoginDb(User usr) {
        String res; //0 salah, 1 regular, 2 admin
        res = "0";
        if (!usr.BooleanReadUser(this.email)) { //email ada
            res = "0";
        } else {
            if (!usr.BooleanReadPass(this.email,this.password)) { //pass pas
                res = "0";
            } else {
                usr = usr.readUserOne(this.email);
                if (usr.isAdmin) {
                    res = "2";
                } else {
                    res = "1";
                }
            }
        }
        return res;
    }

    public boolean lastAdmin() {
        boolean flag;
        int n;
        flag = false;
        ObjectMapper mapper = new ObjectMapper();
        User users = new User();
        n = 0;
        try {
            List<User> newUser = mapper.readValue(new File(Config.DATA_USER_INDEX), new TypeReference<List<User>>() {
            });
            if (this.isAdmin) {
                for (User u : newUser) {
                    if (u.isAdmin) {
                        n++;
                    }
                }
                if (n < 2) {
                    flag = true;
                }
            } else {
                flag = false;
            }

        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return flag;
    }

    public boolean editAll(User usr) {       //this db, usr new
        boolean stat;
        stat = false;
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<User> newUser = mapper.readValue(new File(Config.DATA_USER_INDEX), new TypeReference<List<User>>() {
            });
            for (User u : newUser) {
                if (u.email.equalsIgnoreCase(usr.getEmail())) {
                    if (newUser.remove(u)) {
                        u.setName(usr.getName());
                        u.setPassword(this.encr(usr.getPassword()));
                        newUser.add(u);
                        mapper.writeValue(new File(Config.DATA_USER_INDEX), newUser);
                        stat = true;
                        return stat;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stat;
    }

    public boolean editOne(String condition, User usr) {
        boolean stat;
        stat = false;
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<User> newUser = mapper.readValue(new File(Config.DATA_USER_INDEX), new TypeReference<List<User>>() {
            });
            for (User u : newUser) {
                if (u.email.equalsIgnoreCase(usr.getEmail())) {
                    if (newUser.remove(u)) {
                        if (condition.equalsIgnoreCase("Name")) {
                            u.setName(usr.getName());
                        } else if (condition.equalsIgnoreCase("password")) {
                            u.setPassword(this.encr(usr.getPassword()));
                        }
                        newUser.add(u);
                        mapper.writeValue(new File(Config.DATA_USER_INDEX), newUser);
                        stat = true;
                        return stat;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stat;
    }

    public String readAll() {
        boolean stat;
        String allUser;
        ObjectMapper mapper = new ObjectMapper();
        allUser = "";
        try {
            List<User> listUser = mapper.readValue(new File(Config.DATA_USER_INDEX), new TypeReference<List<User>>() {
            });
            for (User u : listUser) {
                allUser += u.email + "\n";
            }
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allUser;
    }

    public boolean del() {
        boolean stat;
        stat = false;
        User usr = new User();
        ObjectMapper mapper = new ObjectMapper();
        if (this.checkdel(this.getEmail())) {
            try {
                List<User> newUser = mapper.readValue(new File(Config.DATA_USER_INDEX), new TypeReference<List<User>>() {
                });
                for (User u : newUser) {
                    if (u.email.equalsIgnoreCase(this.getEmail())) {
                        if (newUser.remove(u)) {
                            mapper.writeValue(new File(Config.DATA_USER_INDEX), newUser);
                            stat = true;
                            return stat;
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return stat;
    }

    public boolean checkdel(String Email) {  //return false if found in inv
        //kamus
        boolean stat;
        ObjectMapper mapper = new ObjectMapper();

        //init
        stat = true;

        //algoritam
        try {
            List<Invitation> listInv = mapper.readValue(new File(Config.DATA_INVITATION_INDEX), new TypeReference<List<Invitation>>() {
            });
            for (Invitation u : listInv) {
                if (email.equalsIgnoreCase(u.getTo())) {
                    stat = false;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stat;
    }
}
