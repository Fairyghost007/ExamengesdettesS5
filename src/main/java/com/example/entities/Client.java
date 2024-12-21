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
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "client")
public class Client extends AbstractEntity {
    @Column(length = 25, unique = true)
    private String surname;
    @Column(length = 11, unique = true)
    private String phone;
    @Column(length = 255, unique = false)
    private String address;

    // Navigabilite
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @OneToMany(mappedBy = "client")
    private List<Dette> dettes;


   
}
