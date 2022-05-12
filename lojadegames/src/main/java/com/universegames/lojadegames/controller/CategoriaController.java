package com.universegames.lojadegames.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universegames.lojadegames.model.CategoriaModel;
import com.universegames.lojadegames.repository.CategoriaRepository;

@RestController
@RequestMapping ("/categoria")
@CrossOrigin ("*")

public class CategoriaController {
	
	@Autowired
	
	private CategoriaRepository repository;
	
	@GetMapping
	public List<CategoriaModel> getAll() {
		return repository.findAll();

	}
	
	@PostMapping
	public ResponseEntity<CategoriaModel> postCategoria (@Valid @RequestBody CategoriaModel categoria) {
		return ResponseEntity.ok(repository.save(categoria));
	
	}
	
	@PutMapping
	public ResponseEntity<CategoriaModel> put (@RequestBody CategoriaModel categoria) { 
		return ResponseEntity.ok(repository.save(categoria));
	}
	
	@DeleteMapping ("/{id}")
	public void delete (@PathVariable long id) {
		repository.deleteById(id);
	}
}
