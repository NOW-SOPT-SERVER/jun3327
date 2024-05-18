package sopt.week2clone.domain;

import jakarta.persistence.*;

@Entity
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Selling selling;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
