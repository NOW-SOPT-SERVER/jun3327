package sopt.week2clone.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sopt.week2clone.service.dto.SellingCreateDto;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder(access = AccessLevel.PRIVATE)
    private Selling(String title, String method, boolean priceProposal,
                          double price, String text, String location, Member member) {
        this.title = title;
        this.method = method;
        this.proposal = priceProposal;
        this.price = price;
        this.text = text;
        this.location = location;
        this.member =member;
    }

    public static Selling create(SellingCreateDto createDto, Member member) {
        return Selling.builder()
                .title(createDto.title())
                .method(createDto.method())
                .priceProposal(createDto.priceProposal())
                .price(createDto.price())
                .text(createDto.text())
                .location(createDto.location())
                .member(member)
                .build();
    }

}
