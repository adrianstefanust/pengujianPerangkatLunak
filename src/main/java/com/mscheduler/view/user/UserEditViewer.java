package com.mscheduler.view.user;

import com.mscheduler.Config;
import com.mscheduler.ViewMap;
import com.mscheduler.model.User;
import java.util.List;
import java.util.Scanner;

public class UserEditViewer extends AbstractUserViewer {

    //singleton + constructor
    private static UserEditViewer instance;

    static {
        instance = new UserEditViewer();
    }

    public static UserEditViewer getInstance() {
        return instance;
    }

    private UserEditViewer() {
        super();
    }
    //end of singleton

    public String getText(String email) {
        return "You have selected to edit user " + email + ". Please choose edit option.\n";
    }

    public void handleInput(Scanner sc, String email) {
        super.handleInput(sc);
        //Kamus
        String pass, input, condition, joinWords;
        String[] inputParse;
        boolean flag, kondisi1, kondisi2;
        User usr, usrE;
        int stat;

        //Algoritma
        usrE = new User();
        flag = usrE.BooleanReadUser(email);
        if (!flag) {
            System.out.println("Email not found !");
            this.exit();
        }
        stat = -1;
        while (!this.exitCalled) {
            this.menu = "edit-user";
            this.printMenu();
            input = sc.nextLine();
            inputParse = input.split(" ");
            if (inputParse[0].equalsIgnoreCase("help")) {
                System.out.print(this.getHelp());
            } else {
                if (inputParse[0] != null) {
                    usr = new User();
                    if (inputParse.length == 1) {   //only 1 => all, any option show plus
                        if (inputParse[0].equalsIgnoreCase("All")) {
                            pass = null;
                            usr.setEmail(email);

                            kondisi1 = false;
                            kondisi2 = false;

                            while (!kondisi1) {
                                System.out.print("Name : ");
                                usr.setName(sc.nextLine());
                                if (usr.getName().equalsIgnoreCase("-c")) {
                                    System.out.println("canceling..");
                                    return;
                                }
                                if (usr.getName().length() >= 4) {
                                    kondisi1 = true;
                                } else {
                                    System.out.println("Name length minimum is 4 !");
                                }
                            }

                            while (!kondisi2) {
                                System.out.print("Password : ");
                                usr.setPassword(sc.nextLine());
                                if (usr.getPassword().equalsIgnoreCase("-c")) {
                                    System.out.println("canceling..");
                                    return;
                                }
                                System.out.print("Confirmed Password : ");
                                pass = (sc.nextLine());
                                if (pass.equalsIgnoreCase("-c")) {
                                    System.out.println("canceling..");
                                    return;
                                }
                                if (!usr.getPassword().equals(pass)) {
                                    System.out.println("Password and Confirmed password is not same !");
                                } else if (usr.getPassword().length() < 4) {
                                    System.out.println("Password length minimum is 4 !");
                                } else {
                                    kondisi2 = true;
                                }
                            }
                            stat = uc.userEditAll(email, usr); //edit 2 param
                        } else {
                            System.out.println("Unknown command");
                            System.out.println(this.getHelp());
                        }
                    } else {                        //optional 1
                        if (inputParse[0].equalsIgnoreCase("name") || inputParse[0].equalsIgnoreCase("password")) {
                            flag = true;
                            pass = null;
                            usr.setEmail(email);

                            condition = inputParse[0];
                            int n = inputParse.length - 1;
                            String[] newArray = new String[n];
                            System.arraycopy(inputParse, 1, newArray, 0, n);
                            joinWords = String.join(" ", newArray);

                            if (condition.equalsIgnoreCase("name")) {
                                usr.setName(joinWords);
                            }
                            if (condition.equalsIgnoreCase("password")) {
                                usr.setPassword(joinWords);
                                System.out.print("Confirmed Password : ");
                                pass = (sc.nextLine());
                                if (pass.equalsIgnoreCase(usr.getPassword())) {
                                    flag = false;
                                } else {
                                    System.out.print("Password and Confirmed Password mismatch !");
                                    usr = new User();
                                }
                            }
                            stat = uc.userEditOne(email, condition, usr); //edit 1
                        } else if (inputParse[0].equalsIgnoreCase("end")) {
                            this.exit();
                        }
                    }
                }
                if (stat == 0) {
                    System.out.println("Email not found !");
                } else if (stat == 1) {
                    System.out.println("Data Saved !");
                    this.exit();
                } else if (stat == 2) {
                    System.out.println("Email can't changed !");
                } else if (inputParse[0].equalsIgnoreCase("end")) {
                    this.exit();
                } else {
                    System.out.println("Unknown Command!");
                }
            }
        }
    }

//    public void handleInput(Scanner sc) {
//        System.out.println("not implement yet 1");
//        //Kamus
//        String input;
//        String[] inputParse;
//        AbstractUserViewer view;
//
//        //Algoritma
//        while (!this.exitCalled) {
//            System.out.print(">");
//            input = sc.nextLine();
//            inputParse = input.split(" ");
//            if (inputParse[0].equals("help")) {
//                System.out.print(this.getHelp());
//            } else {
//                view = this.getView(inputParse[0]);
//                if (view != null) {
//                    view.init();
//                    if (inputParse.length == 1) {
//                        view.handleInput(sc);
//                    } else {
//                        view.handleInput(sc, (inputParse[1]));
//                    }
//                }
//            }
//        }
//    }
//    public AbstractUserViewer getView(String command) {
//        List<ViewMap> viewMap = Config.userViewMap;
//        ViewMap findFirst = viewMap.stream()
//                .filter(x -> x.getTrigger().equals(command))
//                .findFirst().orElse(null);
//        if (findFirst != null) {
//            return (AbstractUserViewer) findFirst.getView();
//        } else {
//            return null;
//        }
//    }
    @Override
    public String getHelp() {
        return "all<enter> : Edit email and password.\n"
                + "name <new_name> : Input new name.\n"
                + "password <new_password> : Input new password.\n"
                + "end<enter> : End progress edit.\n"
                + "-c<enter> : (in edit) Force to end progress edit Immediately.\n";
    }

}
