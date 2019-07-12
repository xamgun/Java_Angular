package com.algaworks.comercial.controller;

import com.algaworks.comercial.model.Oportunidade;
import com.algaworks.comercial.repository.OportunidadeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/oportunidades")
public class OportuidadeController {
	
	@Autowired
	private OportunidadeRepository oportunidades;

    @GetMapping
    public List<Oportunidade> listar(){    
        return oportunidades.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Oportunidade> buscar (@PathVariable Long id) {
    	Optional<Oportunidade> oportunidade = oportunidades.findById(id);
    	
    	if (!oportunidade.isPresent()) {
			return ResponseEntity.notFound().build();
		}
    	
    	return ResponseEntity.ok(oportunidade.get());
	}
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Oportunidade adicionar (@Valid @RequestBody Oportunidade oportunidade) {
    	
    	Optional<Oportunidade> oportunidadeExistente = oportunidades
    			.findByNomeProspectoAndDescricao(oportunidade.getNomeProspecto(), oportunidade.getDescricao());
    	if (oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ja exista oportunidade cadastrada");
		}
    	return oportunidades.save(oportunidade);
    }
    
    @DeleteMapping("/{id}")
    public void deleteOportunidades (@PathVariable Long id) {
    	oportunidades.deleteById(id);
    }


}
