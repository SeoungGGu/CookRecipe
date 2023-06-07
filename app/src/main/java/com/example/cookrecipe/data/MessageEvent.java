package com.example.cookrecipe.data;

public class MessageEvent {
    private Ingredent data;

    public MessageEvent(Ingredent ingredent) {
        this.data = ingredent;
    }

    public Ingredent getData() {
        return data;
    }
}
