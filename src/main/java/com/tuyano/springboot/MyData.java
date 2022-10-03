package com.tuyano.springboot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name="mydata")
public class MyData{

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@Column
@NotNull   //●
private long id;

@Column(length = 50,nullable = false)
@NotEmpty //●
private String name;

@Column(length = 200,nullable = true)
@Email //●
private String mail;

@Column(nullable = true)
@Min(0) //●
@Max(200) //●
private Integer age;

@Column(nullable = true)
private String memo;

}
