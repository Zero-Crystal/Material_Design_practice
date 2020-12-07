package com.example.material_design_practice;

public class fruitsBean {
    private String fruitsName;
    private int fruitsId;

    public fruitsBean(String fruitsName, int fruitsId) {
        this.fruitsName = fruitsName;
        this.fruitsId = fruitsId;
    }

    public String getFruitsName() {
        return fruitsName;
    }

    public void setFruitsName(String fruitsName) {
        this.fruitsName = fruitsName;
    }

    public int getFruitsId() {
        return fruitsId;
    }

    public void setFruitsId(int fruitsId) {
        this.fruitsId = fruitsId;
    }
}
