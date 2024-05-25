package sopt.week2clone.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "likes")
@NoArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Selling selling;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Like(Selling selling, Member member) {
        this.selling = selling;
        this.member = member;
    }

    public static Like createOne(Selling selling, Member member) {
        return new Like(selling, member);
    }
}
