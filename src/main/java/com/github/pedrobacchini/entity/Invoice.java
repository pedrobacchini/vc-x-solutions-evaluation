package com.github.pedrobacchini.entity;

import com.github.pedrobacchini.audit.Audit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Invoice {

    @Id
    @SequenceGenerator(name = "company_id_seq", sequenceName = "company_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_id_seq")
    private Long id;

    @Setter
    @Column(nullable = false)
    private Long number;

    @Setter
    @Column(nullable = false)
    private LocalDate date;

    @Setter
    @Column(nullable = false)
    private BigDecimal value;

    @Setter
    @OneToOne
    @JoinColumn(nullable = false)
    private Company taker;

    @Setter
    @OneToOne
    @JoinColumn(nullable = false)
    private Company provider;

    @Embedded
    private final Audit audit = new Audit();
}
