package com.example.bhaveekdesai.tiltmenu.datastructures;

public class Menu_four_item {
    public String item_name;
    public Menu_four_item left, right, up, down;

    public Menu_four_item(String item_name){
        this.item_name = item_name;
    }
}
