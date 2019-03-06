package com.codecool.webrouteannotation.site;

public class Routes {

    @WebRoute(path="/test1")
    public String test1(){
        return "This is the TEST1 Webroute!";
    }

    @WebRoute(path="/test2")
    public String test2(){
        return "This is the TEST2 Webroute!";

    }

    @WebRoute(method="POST", path="/users")
    public String onTest() {
        // here goes the code to handle POST requests going to localhost:8000/users
        return "users with POST method!";
    }
}
