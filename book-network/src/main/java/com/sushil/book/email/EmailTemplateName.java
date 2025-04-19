package com.sushil.book.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account"),
    ACTIVATE_COMPLETE("activate_complete");

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
