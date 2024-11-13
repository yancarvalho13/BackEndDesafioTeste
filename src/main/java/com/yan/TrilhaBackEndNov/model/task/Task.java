package com.yan.TrilhaBackEndNov.model.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yan.TrilhaBackEndNov.model.user.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "tasks")
@Entity(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore //Necessário para evitar o loop da mesma tarefa, problema de serialização do JSON
    private User user;
}
