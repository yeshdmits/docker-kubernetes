package com.yeshenko.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie {

    @Id
    @Column(name = "movie_id", length = 10)
    private String id;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(name = "date_release", nullable = false)
    private Integer dateRelease;

    @Column(nullable = false)
    private Double rating;

    @ManyToMany
    @JoinTable(name = "movie_person", joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<Person> people = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) && Objects.equals(name, movie.name) && Objects.equals(dateRelease, movie.dateRelease) && Objects.equals(rating, movie.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateRelease, rating);
    }
}