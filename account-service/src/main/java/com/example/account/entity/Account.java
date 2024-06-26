package com.example.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name="login")
    private String login;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="creation_date")
    private Date creationDate;

    @Column(name="bill_id")
    private UUID billId;

    private String role;

    @Column(name="books")
    private List<UUID> books;
}
