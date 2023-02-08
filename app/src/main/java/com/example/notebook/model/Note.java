package com.example.notebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
public class Note implements Serializable {

    public Note(String name, String description, LocalDate eventTime) {
        this.name = name;
        this.description = description;
        this.eventTime = eventTime;
    }

    private int id;
    private String name;
    private String description;
    private LocalDate eventTime;

}
