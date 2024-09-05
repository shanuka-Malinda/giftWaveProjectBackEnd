package com.example.projectBackEnd.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String userName;

    @Column
    private String Address;

    @Column
    private String email;

    @Column
    private String tel;

    @Column( columnDefinition = "LONGTEXT")
    private String image;

    @Column
    private String password;
}
