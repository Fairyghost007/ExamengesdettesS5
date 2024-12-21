package com.example.entities;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "detailDette")
public class Detail extends AbstractEntity{
    @Column(length = 11)
    private Integer qteDette;
    @Column(length = 255)
    private Double montant;


    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id",nullable = false) 
    private Article article;
    
    @ManyToOne
    @JoinColumn(name = "dette_id", referencedColumnName = "id",nullable = false)  
    private Dette dette;
    
    
}
