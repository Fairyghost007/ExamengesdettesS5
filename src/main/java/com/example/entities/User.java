package com.example.entities;


import com.example.enums.Role;
import javax.persistence.*;
// import org.hibernate.annotations.ColumnDefault;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString(exclude = "client")
@EqualsAndHashCode(callSuper = false,of={"login"})
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name="User.selectByLogin", query="SELECT u FROM User u WHERE u.login = :login")
})

public class User  extends AbstractEntity{
    private String email;
    @Column(length = 25, unique = true)
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean statut;

    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "clients_id", nullable = true)
    private Client client;
    

}