package br.com.test.eliza.gupy.service;

import br.com.test.eliza.gupy.dto.FuncionarioDTO;
import br.com.test.eliza.gupy.model.Funcionario;
import br.com.test.eliza.gupy.repositories.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Funcionario> inserirTodos() {
        List<Funcionario> funcionariosInseridos = new ArrayList<>();

        funcionariosInseridos.add(funcionarioRepository.save(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador")));
        funcionariosInseridos.add(funcionarioRepository.save(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador")));
        funcionariosInseridos.add(funcionarioRepository.save(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador")));
        funcionariosInseridos.add(funcionarioRepository.save(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor")));
        funcionariosInseridos.add(funcionarioRepository.save(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista")));
        funcionariosInseridos.add(funcionarioRepository.save(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador")));
        funcionariosInseridos.add(funcionarioRepository.save(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador")));
        funcionariosInseridos.add(funcionarioRepository.save(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente")));
        funcionariosInseridos.add(funcionarioRepository.save(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista")));
        funcionariosInseridos.add(funcionarioRepository.save(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")));

        return funcionariosInseridos;
    }


    public List<FuncionarioDTO> listarTodosFuncionarios() {
        return funcionarioRepository.findAll().stream()
                .map(FuncionarioDTO::new)
                .collect(Collectors.toList());
    }

    public List<FuncionarioDTO> listarTodosFuncionariosExcluindoJoao() {
        return funcionarioRepository.findAllExcludingJoao().stream()
                .map(FuncionarioDTO::new)
                .collect(Collectors.toList());
    }

    public void excluirFuncionarioJoao() {
        Funcionario joao = funcionarioRepository.findByNome("João");
        if (joao != null) {
            funcionarioRepository.delete(joao);
        } else {
            throw new EntityNotFoundException("Funcionário João não encontrado");
        }
    }

    public List<FuncionarioDTO> aumentarSalarios() {
        BigDecimal percentualAumento = new BigDecimal("0.10"); // 10%
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        // Aplicar aumento de salário
        for (Funcionario funcionario : funcionarios) {
            BigDecimal salarioAtual = funcionario.getSalario();
            BigDecimal aumento = salarioAtual.multiply(percentualAumento);
            funcionario.setSalario(salarioAtual.add(aumento));
            funcionarioRepository.save(funcionario);
        }

        // Retornar a lista atualizada de funcionários em forma de DTOs
        return funcionarios.stream()
                .map(FuncionarioDTO::new)
                .collect(Collectors.toList());
    }


    public Map<String, List<FuncionarioDTO>> agruparPorFuncao() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .collect(Collectors.groupingBy(
                        Funcionario::getFuncao,
                        Collectors.mapping(FuncionarioDTO::new, Collectors.toList())
                ));
    }

    public List<FuncionarioDTO> buscarAniversariantesOutubroDezembro() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .map(FuncionarioDTO::new)
                .collect(Collectors.toList());
    }

    public FuncionarioDTO buscarFuncionarioMaiorIdade() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        LocalDate dataAtual = LocalDate.now();
        Funcionario funcionarioMaisVelho = null;
        long idadeMaisVelha = 0;

        for (Funcionario funcionario : funcionarios) {
            long idade = funcionario.getDataNascimento().until(dataAtual).getYears();
            if (idade > idadeMaisVelha) {
                idadeMaisVelha = idade;
                funcionarioMaisVelho = funcionario;
            }
        }

        return funcionarioMaisVelho != null ? new FuncionarioDTO(funcionarioMaisVelho) : null;
    }

    public List<FuncionarioDTO> listarFuncionariosOrdemAlfabetica() {
        return funcionarioRepository.findAllByOrderByNomeAsc().stream()
                .map(FuncionarioDTO::new)
                .collect(Collectors.toList());
    }

    public BigDecimal calcularTotalSalarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<String, BigDecimal> calcularSalariosMinimos() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        Map<String, BigDecimal> salariosMinimosMap = new HashMap<>();

        for (Funcionario funcionario : funcionarios) {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_DOWN);
            salariosMinimosMap.put(funcionario.getNome(), salariosMinimos);
        }

        return salariosMinimosMap;
    }
}
