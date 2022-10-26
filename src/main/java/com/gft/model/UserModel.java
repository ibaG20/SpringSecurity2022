package com.gft.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;
	
	@Column(unique = true)
	private String name;
	private String email;
	private String password;
	private String description;
	private String photo_url;
	private Double average_rating;
	
	@ManyToMany(fetch   = FetchType.EAGER, //CARREGA OS DADOS SEMPRE
			    cascade = CascadeType.ALL)
	@JoinTable(
			name               = "tb_users_roles", 
			joinColumns        = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles = new HashSet<>();
	
	public UserModel(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

}
