package com.ci.hightide.model;

public class Student {
    private String studentName;
    private Integer birthDay;
    private Integer birthMonth;
    private String userName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Integer birthDay) {
        this.birthDay = birthDay;
    }

    public Integer getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(Integer birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Student{" +
                ", studentName='" + studentName + '\'' +
                ", birthDay=" + birthDay +
                ", birthMonth=" + birthMonth +
                ", userName='" + userName + '\'' +
                '}';
    }
}
