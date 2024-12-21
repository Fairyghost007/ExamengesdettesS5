package com.example.entities;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString(exclude = { "dettes", "details" })
@EqualsAndHashCode(callSuper = false,of={"libelle"})
@Entity
@Table(name = "article")
public class Article extends AbstractEntity {

    @Column(length = 25, unique = true)
    private String libelle;
    private Double prix;
    private Integer qteStock;


    @OneToMany(mappedBy = "article")
    private List<Detail> details;

}
