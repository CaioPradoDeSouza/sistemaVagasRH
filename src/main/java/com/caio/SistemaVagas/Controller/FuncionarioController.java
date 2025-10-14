package com.caio.SistemaVagas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.caio.SistemaVagas.Model.Dependentes;
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
	
	//chama o form de cadastrar Funcionario
	@RequestMapping(value="/cadastrarFuncionario", method= RequestMethod.GET)
	public String form() {
		return "funcionario/formFuncionario";
	}
	
	//cadastra funcionarios
	@RequestMapping(value="cadastrarFuncionario", method= RequestMethod.POST)
	public String form(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem","Verifique os campos");
			return "redirect:/cadastrarFuncionario";
		}
		fr.save(funcionario);
		attributes.addFlashAttribute("mensagem","Funcionário cadastrado com sucesso!");
		return "redirect:/cadastrarFuncionario";
		
	}
	
	//listar funcionário
	@RequestMapping(value="/funcionarios")
	public ModelAndView listaFuncionarios() {
		ModelAndView mv = new ModelAndView("funcionario/listaFuncionario");
		Iterable<Funcionario> funcionarios = fr.findAll();
		mv.addObject("funcionarios",funcionarios);
		return mv;
	}
	
	// listar dependentes
	@RequestMapping(value="/dependentes/{id}",method=RequestMethod.GET)
	public ModelAndView dependentes(@PathVariable("id") long id) {
		Funcionario funcionario = fr.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/dependentes");
		mv.addObject("funcionario",funcionario);
		
		// lista de dependentes baseada no funcionário
		Iterable<Dependentes> dependentes = dr.findByFuncionario(funcionario);
		mv.addObject("dependentes",dependentes);
		
		return mv;
	}
	
}
