package com.ponatosik.kanban.core.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "myTask")
public class Task {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator"
    )
    @SequenceGenerator(
            name = "sequence-generator",
            sequenceName = "task_sequence"
    )
    @Column(name = "id")
    private Integer id;

    @Setter
    private String title;
    @Setter
    private String description = null;
    @Setter
    private LocalDateTime deadline = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statusId", referencedColumnName = "id")
    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Setter
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId", referencedColumnName = "id")
    @JsonProperty("groupId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Group group;

    @Setter(AccessLevel.PACKAGE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name= "taskOrder")
    private Integer order = null;

    Task(
            Integer id,
            String title,
            String description,
            LocalDateTime deadline,
            Status status,
            Group group,
            Integer order) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
        this.description = description;
        this.status = status;
        this.group = group;
        this.order = order;
    }

    Task(Integer id, String title, String description, LocalDateTime deadline, Status status, Group group) {
        this(id, title, description, deadline, status, group, null);
    }

    Task(Integer id, String title, String description, Status status, Group group) {
        this(id, title, description, null, status, group);
    }

    Task(Integer id, String title, Status status, Group group) {
        this(id, title, null, null, status, group);
    }
}

