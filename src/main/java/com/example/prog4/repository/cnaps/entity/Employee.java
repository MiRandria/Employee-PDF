package com.example.prog4.repository.cnaps.entity;

import com.example.prog4.repository.base.entity.enums.Csp;
import com.example.prog4.repository.base.entity.enums.Sex;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = "\"employee\"")
public class Employee implements Serializable {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private String id;
  private String cin;
  private String image;
  private String address;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "personal_email")
  private String personalEmail;
  @Column(name = "professional_email")
  private String professionalEmail;
  @Column(name = "registration_number")
  private String registrationNumber;

  @Column(name = "birth_date")
  private LocalDate birthDate;
  @Column(name = "entrance_date")
  private LocalDate entranceDate;
  @Column(name = "departure_date")
  private LocalDate departureDate;
  @Column(name = "children_number")

  private Integer childrenNumber;

  @Enumerated(EnumType.STRING)
  @ColumnTransformer(read = "CAST(sex AS varchar)", write = "CAST(? AS sex)")
  private Sex sex;
  @Enumerated(EnumType.STRING)
  @ColumnTransformer(read = "CAST(csp AS varchar)", write = "CAST(? AS csp)")
  private Csp csp;
  private String endToEndId;
}