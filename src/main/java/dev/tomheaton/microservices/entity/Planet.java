package dev.tomheaton.microservices.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Planet {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String designation;

    public Planet() {
    }

    public Planet(String name, String designation) {
        this.name = name;
        this.designation = designation;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Planet planet)) {
            return false;
        }
        return Objects.equals(this.id, planet.id) && Objects.equals(this.name, planet.name) && Objects.equals(this.designation, planet.designation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.designation);
    }

    @Override
    public String toString() {
        return String.format("Planet { id=%s, name='%s', designation='%s' }", this.id, this.name, this.designation);
    }
}
