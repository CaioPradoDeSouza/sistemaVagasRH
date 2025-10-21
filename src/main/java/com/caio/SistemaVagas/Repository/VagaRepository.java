package com.caio.SistemaVagas.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.caio.SistemaVagas.Model.Funcionario;
import com.caio.SistemaVagas.Model.Vaga;

public interface VagaRepository extends CrudRepository <Vaga, Long> {
	
	Vaga findByCodigo(long codigo);
	
	List<Vaga> findByNome(String nome);
	
	@Query(value="select u from Vaga u where u.nome like %?1% ")
	List<Vaga>findByNomesVaga(String nome);

}
