package com.example.notebook.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Note implements Serializable {
    private int id;
    private String name;
    private String description;
    private LocalDateTime eventTime;
}
