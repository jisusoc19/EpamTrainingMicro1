package com.task3.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@Entity
@Data
@Table(name = "usergym")
@Slf4j
@NoArgsConstructor
public class User implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false,name="first_Name")
	private String firstName;

	@Column(nullable = false,name="last_Name")
	private String lastName;

	@Column(nullable = false,name="username")
	private String username;

	@Column(nullable = false,name="password")
	private String password;

	@Column(nullable = false,name="is_Active")
	private Boolean isActive = true;

	@OneToOne(mappedBy ="userid")
	Trainee trainee;

	@OneToOne(mappedBy ="userid")
	Trainer trainer;


	private static final long serialVersionUID = 1L;
	public void init() {
		log.info("User Entity Creado");
	}

}
