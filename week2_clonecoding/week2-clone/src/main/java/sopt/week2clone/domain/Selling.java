package sopt.week2clone.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Selling {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String method;
    private boolean proposal;
    private double price;
    private String text;
    private String location;

    @ManyToOne
    private Member member;

    @Builder
    public Selling(String title, String method, boolean proposal,
                          double price, String text, String location, Member member) {
        this.title = title;
        this.method = method;
        this.proposal = proposal;
        this.price = price;
        this.text = text;
        this.location = location;
        this.member =member;
    }
}
