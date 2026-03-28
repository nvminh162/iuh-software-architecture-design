package com.nvminh162;

public class EagerInitialization {

    private static final EagerInitialization INSTANCE = new EagerInitialization();
    private String name;

    public EagerInitialization() {}

    public static EagerInitialization getInstance() {
        return INSTANCE;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
