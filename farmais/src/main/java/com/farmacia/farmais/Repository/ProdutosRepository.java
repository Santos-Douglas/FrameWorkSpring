package com.farmacia.farmais.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farmacia.farmais.Model.ProdutosModel;

@Repository
public interface ProdutosRepository extends JpaRepository<ProdutosModel, Long>{
	
	public List<ProdutosModel>findAllByTituloContainingIgnoreCase (String titulo);

}
