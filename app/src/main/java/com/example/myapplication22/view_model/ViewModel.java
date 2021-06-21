package com.example.myapplication22.view_model;

import com.example.myapplication22.model.Model;

public class ViewModel {

    Model m;
    public String ip;
    public String port;

    public ViewModel(Model m) { this.m = m; }

    public boolean connect() {
        return this.m.connect(ip, Integer.parseInt(port));
    }

    public void update(String target, float value){
        try {
            this.m.update(target, value);
        } catch (Exception e) {
            System.out.println("Exception thrown when tried to update values.\n");
        }
    }
}
