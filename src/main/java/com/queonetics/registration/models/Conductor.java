package com.queonetics.registration.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "tb_conductors")
public class Conductor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String registration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conductor that = (Conductor) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(registration, that.registration);
    }
}
