package com.employees.model;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    private static final long serialVersionUID = -7817224776021728682L;

    private Integer empId;
    private String firstName;
    private String lastName;
    private String position;
    private String SeparationDate;

}
