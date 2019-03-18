package com.codecool.webrouteannotation.site;


import static com.codecool.webrouteannotation.site.HttpMethod.GET;
import static com.codecool.webrouteannotation.site.HttpMethod.POST;

public class Routes {

    @WebRoute(path = "/test1")
    public String test1() {
        return "This is the TEST1 Webroute!";
    }

    @WebRoute(method = GET, path = "/test2")
    public String test2() {
        return "This is the TEST2 Webroute!";

    }

    @WebRoute(method = POST, path = "/users")
    public String onTest() throws NoSuchMethodException {
        // here goes the code to handle POST requests going to localhost:8000/users
            return "This is /users with POST method!";
    }
}

