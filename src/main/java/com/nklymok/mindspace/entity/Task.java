package com.nklymok.mindspace.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Task {
    private Long id;
    private String header;
    private String description;
    private LocalDateTime dueDate;
    private Integer priority;
}
