package com.example.cookrecipe.data;


import java.io.Serializable;
import java.util.Objects;

public class Ingredent implements Serializable {


    private String name;
    private int icon;
    private String type;

    private long startTime;
    private long endTime;

    public Ingredent(String name, int icon, String type) {
        this.name = name;
        this.icon = icon;
        this.type = type;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Ingredent{" +
                "name='" + name + '\'' +
                ", icon=" + icon +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Ingredent other = (Ingredent) obj;
        return Objects.equals(name, other.name) && type == other.type;
    }
}
