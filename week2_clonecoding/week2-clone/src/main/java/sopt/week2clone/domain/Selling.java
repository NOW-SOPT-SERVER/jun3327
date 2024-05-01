package sopt.week2clone.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Selling extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String method;
    private boolean proposal;
    private double price;
    private String text;
    private String location;
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Selling(String title, String method, boolean priceProposal,
                          double price, String text, String location, Member member) {
        this.title = title;
        this.method = method;
        this.proposal = priceProposal;
        this.price = price;
        this.text = text;
        this.location = location;
        this.member =member;
    }
}
