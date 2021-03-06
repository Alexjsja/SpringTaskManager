package com.bestSpringApplication.taskManager.models.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Role {
    STUDENT("student"),
    TEACHER("teacher"),
    ADMIN("admin");

    private static final Map<String,Role> VALUES = new HashMap<>(Role.values().length);

    static {
        for(Role el: values()) VALUES.put(el.getStrValue(),el);
    }

    @Getter
    private final String strValue;

    Role(String strValue){
        this.strValue=strValue;
    }

    public static Optional<Role> of(String str) {
        return Optional.ofNullable(VALUES.get(str));
    }

}
