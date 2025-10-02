package com.caio.SistemaVagas.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.caio.SistemaVagas.Model.Candidato;
import com.caio.SistemaVagas.Model.Vaga;
import java.util.List;


public interface CandidatoRepository extends CrudRepository <Candidato, Long> {
	
	Iterable<Candidato> findByVaga(Vaga vaga);
	
	Candidato findByRg(String rg);
	
	Candidato findById(long id);
	
	List<Candidato>findByNomeCandidato(String nomeCandidato);
	
}
