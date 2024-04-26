package com.demo.library.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private int id;
	
	@Column(length = 100, nullable = false)
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "authod_id")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Author author;
	
	@Column(length = 13, nullable = false, unique = true)
	private String isbn;
	
	@Column(nullable = false)
	private int publicationYear;
	
	@Column(nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean isAvailable;
}
