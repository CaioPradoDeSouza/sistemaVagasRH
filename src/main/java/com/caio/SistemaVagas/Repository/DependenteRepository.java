package com.caio.SistemaVagas.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import com.caio.SistemaVagas.Model.Dependentes;
import com.caio.SistemaVagas.Model.Funcionario;

public interface DependenteRepository extends CrudRepository <Dependentes, String> {
	
	Iterable<Dependentes> findByFuncionario(Funcionario funcionario);
	
	
	// pensando no metodo delele
	Dependentes findByCpf(String cpf);
	Dependentes findById(long id);
	
	// criado para impelementar 
	List<Dependentes> findByNome(String nome);
	
	@Query(value="select u from Dependentes u where u.nome like %?1% ")
	List<Dependentes>findByNomesDependentes(String nome);

}
