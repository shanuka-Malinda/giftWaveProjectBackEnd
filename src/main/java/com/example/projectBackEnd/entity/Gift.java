package com.example.projectBackEnd.entity;

import com.example.projectBackEnd.constant.CommonStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column
    private String giftName;

    @Column
    private String SendingDate;

    @Column
    private LocalDateTime createdAt;

    @Column
    private String totalPrice;

    @Enumerated
    private CommonStatus commonStatus;

    @ManyToMany
    @JoinTable(
            name = "gift_items",
            joinColumns = @JoinColumn(name = "gift_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private Set<Items> items;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
    private String userId;
}
