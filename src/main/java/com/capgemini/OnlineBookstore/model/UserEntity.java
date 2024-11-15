package com.capgemini.OnlineBookstore.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty(message = "Username cannot be empty")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "address cannot be empty")
    private String address;

    @NotEmpty(message = "phonenumber cannot be empty")
    @Column(name = "phonenumber")
    private String phonenumber;

    /*
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderEntity> orders;
*/
}
