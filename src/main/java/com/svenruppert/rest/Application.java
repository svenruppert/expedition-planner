package com.svenruppert.rest;


public class Application {


  private Application() {
  }

  public static void main(String[] args) {
    RestService restService = new RestService();
    restService.startService(7070);
  }


}
