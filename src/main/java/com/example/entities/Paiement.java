package com.example.entities;

import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "paiement")
public class Paiement extends AbstractEntity {
    @Column(nullable = false)
    private Double montant; 

    @ManyToOne
    @JoinColumn(name = "dette_id", nullable = false)
    private Dette dette;
}
