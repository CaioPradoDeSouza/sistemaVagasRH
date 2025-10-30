package com.caio.SistemaVagas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.caio.SistemaVagas.Model.Dependente;
import com.caio.SistemaVagas.Model.Funcionario;
import com.caio.SistemaVagas.Repository.DependenteRepository;
import com.caio.SistemaVagas.Repository.FuncionarioRepository;

import jakarta.validation.Valid;

import com.caio.SistemaVagas.Model.Candidato;

@Controller
public class FuncionarioController {
	@Autowired
	private FuncionarioRepository fr;

	@Autowired
	private DependenteRepository dr;

	// chama o form de cadastrar Funcionario
	@RequestMapping("/cadastrarFuncionario")
	public String form() {
		return "funcionario/form-funcionario";
	}

	// cadastra funcionarios
	@RequestMapping(value = "cadastrarFuncionario", method = RequestMethod.POST)
	public String form(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarFuncionario";
		}
		fr.save(funcionario);
		attributes.addFlashAttribute("mensagem", "Funcionário cadastrado com sucesso!");
		return "redirect:/cadastrarFuncionario";

	}

	// listar funcionário
	@RequestMapping(value = "/funcionarios")
	public ModelAndView listaFuncionarios() {
		ModelAndView mv = new ModelAndView("funcionario/lista-funcionario");
		Iterable<Funcionario> funcionarios = fr.findAll();
		mv.addObject("funcionarios", funcionarios);
		return mv;
	}

	// listar dependentes
	@RequestMapping("/detalhes-funcionario/{id}")
	public ModelAndView detalhesFuncionario(@PathVariable("id") long id) {
		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/detalhes-funcionario");
		mv.addObject("funcionarios", funcionario);

		// lista de dependentes baseada no funcionário
		Iterable<Dependente> dependente = dr.findByFuncionario(funcionario);
		mv.addObject("dependentes", dependente);

		return mv;
	}

	// adicionar dependentes
	@RequestMapping(value = "/detalhes-funcionario/{id}", method = RequestMethod.POST)
	public String detalhesFuncionarioPost(@PathVariable("id") long id, Dependente dependente, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagen", "Verifique os campos ! ");
			return "redirect:/detalhes-funcionario/{id}";
		}

		if (dr.findByCpf(dependente.getCpf()) != null) {
			attributes.addFlashAttribute("mensagem_erro", "CPF duplicado");
			return "redirect:/detalhes-funcionario/{id}";
		}

		Funcionario funcionario = fr.findById(id);
		dependente.setFuncionario(funcionario);
		dr.save(dependente);
		attributes.addFlashAttribute("mensagem", "Dependente adicionado com sucesso ! ");

		return "redirect:/detalhes-funcionario/{id}";

	}

	// deleta funcionario
	@RequestMapping(value = "/deletarFuncionario")
	public String deletarFuncionario(long id) {
		Funcionario funcionario = fr.findById(id);
		fr.delete(funcionario);
		return "redirect:/funcionarios";
	}

	// Métodos que atualizam funcionário
	// form
	@RequestMapping("editar-funcionario")
	public ModelAndView editarFuncionario(long id) {
		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	// update funcionário
	@RequestMapping(value = "/editar-funcionario", method = RequestMethod.POST)
	public String updateFuncionario(@Valid Funcionario funcionario, BindingResult result,
			RedirectAttributes attributes) {
		
		fr.save(funcionario);
		attributes.addFlashAttribute("success", "Funcionário alterado com sucesso !");
		
		long idLong = funcionario.getId();
		String id = "" + idLong;
		return "redirect:/detalhes-funcionario/" + id;
	}
	
	// deletar dependente
	@RequestMapping(value = "/deletarDependente")
	public String deletarDependente(String cpf) {
		
		Dependente dependente = dr.findByCpf(cpf);
		
		Funcionario funcionario = dependente.getFuncionario();
		String codigo = "" + funcionario.getId();
				
		dr.delete(dependente);
		
		return "redirect:/detalhes-funcionario/" + codigo;
	}

}
