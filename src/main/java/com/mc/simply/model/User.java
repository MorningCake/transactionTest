package com.mc.simply.model;

import com.mc.simply.messaging.impl.UserSender;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "main", name = "simply_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;
}
