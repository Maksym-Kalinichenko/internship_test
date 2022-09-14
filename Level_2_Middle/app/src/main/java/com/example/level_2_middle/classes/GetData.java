package com.example.level_2_middle.classes;

public class GetData {
    private int userAge;
    private String userName;
    private boolean checkSwitch;
    private String userDate;

    public GetData(String userName, int userAge, String userDate, boolean checkSwitch) {
        this.userAge = userAge;
        this.userName = userName;
        this.checkSwitch = checkSwitch;
        this.userDate = userDate;
    }

    public String getName() {
        return userName;
    }

    public int getAge() {
        return userAge;
    }

    public boolean getSwitch() {
        return checkSwitch;
    }

    public String getDate() {
        return userDate;
    }


}