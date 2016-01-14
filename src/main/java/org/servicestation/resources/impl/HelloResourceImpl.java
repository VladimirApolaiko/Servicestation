package org.servicestation.resources.impl;

import org.servicestation.resources.HelloResource;

import java.util.ArrayList;
import java.util.List;


public class HelloResourceImpl implements HelloResource {

    public String sayHello() {
        List<String> string = new ArrayList<>();
        return "TEST TEST TEST";
    }
}
