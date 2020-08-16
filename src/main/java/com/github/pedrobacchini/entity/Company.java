package com.github.pedrobacchini.entity;

import com.github.pedrobacchini.audit.Audit;
import com.github.pedrobacchini.enumerated.CompanyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Company {

    @Id
    @SequenceGenerator(name = "company_id_seq", sequenceName = "company_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_id_seq")
    private Long id;

    @Setter
    private String tradeName;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private String documentIdentifier;

    @Setter
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CompanyType type;

    @Embedded
    private final Audit audit = new Audit();

    public Company(String tradeName, String name, String documentIdentifier, CompanyType type) {
        this.tradeName = tradeName;
        this.name = name;
        this.documentIdentifier = documentIdentifier;
        this.type = type;
    }
}
