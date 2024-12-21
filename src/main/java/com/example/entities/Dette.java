package com.example.entities;


import javax.persistence.*;

import com.example.enums.Statut;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;



@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "dette")
public class Dette extends AbstractEntity{
    @Column(length = 25)
    private Double montant;
    @Column(length = 255, unique = false)
    // @Transient
    private Double montantVerser;
    @Column(length = 255, unique = false)
    private Double montantRestant;

    @Enumerated(EnumType.STRING)
    private Statut status;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy="dette", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detail> details;

    @OneToMany(mappedBy = "dette", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paiement> paiements;


    @Override
    public String toString() {
        return "Dette(" +
                "id=" + getId() +
                ", montant=" + montant +
                ", montantVerser=" + montantVerser +
                ", montantRestant=" + montantRestant +
                ", clientId=" + (client != null ? client.getId() : "null")+
                ", Statut=" + status +
                ')';
    }

}
