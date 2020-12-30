package ru.haval.muTrainings.beans;

public class Date {
  private String date;
  private int status;

  public static final int NONE = 0;
  public static final int REQUIRED = 1;
  public static final int OPTIONAL = 2;

  public Date(String date, int status) {
    this.date = date;
    this.status = status;
  }

  public String getDate() {
    return date;
  }

  public int getStatus() {
    return status;
  }
}
