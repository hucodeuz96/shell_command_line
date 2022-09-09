package com.example.shell_command_line;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author "Husniddin Ulachov"
 * @created 9:59 PM on 9/8/2022
 * @project shell_command_line
 */
@ShellComponent
class TransferMoney {
    List<AuthUser> users = new ArrayList<>(List.of(
            AuthUser.builder()
                    .username("Husniddin")
                    .password("123")
                    .balance(BigInteger.valueOf(1_000_000))
                    .build(),
            AuthUser.builder()
                    .username("Shuxrat")
                    .password("123")
                    .balance(BigInteger.valueOf(5_000_000)).build()
    ));
    private boolean connected;
    private AuthUser sessionUser;

    @ShellMethod(value = "Login", key = "login")
    @ShellMethodAvailability("blockLoginCommand")
    public void login(String username,String password){
            users.stream().filter(authUser -> authUser.getUsername().equals(username) && authUser.getPassword().equals(password))
                    .findFirst()
                    .ifPresentOrElse(authUser -> {
                        sessionUser = authUser;
                        connected = true;
                        System.out.println("Successfully logged in");
                    },() -> System.out.println("Bad credintials"));
    }

    public Availability blockLoginCommand(){
        return !connected? Availability.available():Availability.unavailable("ou can not perform login command because you already logged in");
    }
    public Availability connected(){
        return connected ? Availability.available():
                Availability.unavailable("You can not perform this action.\nYou should do login");
    }

    @ShellMethod(value = "show users info",key = "users_info")
    public void showInfoUser(){
        System.out.println(users);
    }
    @ShellMethod(value = "Transfer money from account  to account", key= "transfer")
    @ShellMethodAvailability("connected")
    public boolean transfer(String reciver,BigInteger amount){
        AuthUser reciver_found = users.stream().filter(authUser -> authUser.getUsername().equals(reciver)).findFirst().orElseThrow(() -> new RuntimeException("Reciver not found "));
        sessionUser.setBalance(sessionUser.getBalance().subtract(amount));
        reciver_found.setBalance(reciver_found.getBalance().add(amount));
        System.out.println("Transfer successfully processed");
        return true;
    }
}
