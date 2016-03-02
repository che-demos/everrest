package com.thoughtworks.che.sample;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class UserApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> cls = new HashSet<>(1);
        cls.add(UserService.class);
        return cls;
    }
}
