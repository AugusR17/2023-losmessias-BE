package com.losmessias.leherer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.losmessias.leherer.role.AppUserSex;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String location;
    @Column
    private String phone;
    @Column
    private AppUserSex sex;
    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<ClassReservation> classReservations;

    public Student(String firstName, String lastName, String email, String location, String phone, AppUserSex appUserSex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.location = location;
        this.classReservations = new ArrayList<>();
        this.phone = phone;
        this.sex = appUserSex;
    }

    public void addReservation(ClassReservation classReservation) {
        this.classReservations.add(classReservation);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName=" + lastName +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public String toJson(){
        return "{" +
                "\"id\":" + id +
                ", \"firstName\":\"" + firstName + '\"' +
                ", \"lastName\":\"" + lastName + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"location\":\"" + location + '\"' +
                '}';
    }
}
