package com.farmacia.farmais.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table (name = "tb_categoria")
public class CategoriaModel {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	public Long id;
	
	@NotNull
	public String descricao;
	
	@OneToMany (mappedBy = "categoria" , cascade = CascadeType.ALL)
	@JsonIgnoreProperties ("categoria")
	private List<ProdutosModel> categoria;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<ProdutosModel> getProdutos() {
		return categoria;
	}

	public void setProdutos(List<ProdutosModel> produtos) {
		this.categoria = produtos;
	}
	

}
