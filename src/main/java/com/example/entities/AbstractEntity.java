package com.example.entities;


import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;



@Getter
@Setter
@ToString
@EqualsAndHashCode
@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onePrePersist(){
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
        // this.setMontantRestant(montant-montantVerser);
    }

    @PreUpdate
    public void onePreUpdate(){
        this.setUpdatedAt(LocalDateTime.now());
        // this.setMontantRestant(montant-montantVerser);
    }
    
}
