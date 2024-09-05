package com.example.projectBackEnd.entity;

import com.example.projectBackEnd.constant.CommonStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Gift")
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String giftName;
    private LocalDateTime createdAt;

    @Enumerated
    private CommonStatus commonStatus;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @ManyToMany
    @JoinTable(
            name = "gift_items",
            joinColumns = @JoinColumn(name = "gift_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private Set<Items> items;
}
