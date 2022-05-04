package ru.job4j.dreamjob.model;

import java.io.Serializable;
import java.util.Objects;

public class Post implements Serializable {
    private int id;
    private String name;
    private boolean visible;
    private City city;

    private Post() {

    }

    public Post(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Post(int id, String name,City city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public Post(int id, String name,boolean visible,City city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.visible = visible;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}