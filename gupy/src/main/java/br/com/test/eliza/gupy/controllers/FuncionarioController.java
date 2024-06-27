package br.com.test.eliza.gupy.controllers;

import br.com.test.eliza.gupy.dto.FuncionarioDTO;
import br.com.test.eliza.gupy.model.Funcionario;
import br.com.test.eliza.gupy.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/inserirTodos")
    public ResponseEntity<List<Funcionario>> inserirTodos() {
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.inserirTodos());
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<FuncionarioDTO>> listarTodosFuncionarios() {
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.listarTodosFuncionarios());
    }

    @GetMapping("/listarTodosExcetoJoao")
    public ResponseEntity<List<FuncionarioDTO>> listarTodosFuncionariosExcluindoJoao() {
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.listarTodosFuncionariosExcluindoJoao());
    }

    @GetMapping("/listarOrdenados")
    public ResponseEntity<List<FuncionarioDTO>> listarFuncionariosOrdemAlfabetica() {
        List<FuncionarioDTO> funcionarios = funcionarioService.listarFuncionariosOrdemAlfabetica();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/aumentarSalarios")
    public ResponseEntity<List<FuncionarioDTO>> aumentarSalarios() {
        List<FuncionarioDTO> funcionariosAtualizados = funcionarioService.aumentarSalarios();
        return ResponseEntity.ok(funcionariosAtualizados);
    }

    @GetMapping("/agruparPorFuncao")
    public ResponseEntity<Map<String, List<FuncionarioDTO>>> agruparPorFuncao() {
        Map<String, List<FuncionarioDTO>> funcionariosPorFuncao = funcionarioService.agruparPorFuncao();
        return ResponseEntity.ok(funcionariosPorFuncao);
    }

    @GetMapping("/aniversariantesOutubroDezembro")
    public ResponseEntity<List<FuncionarioDTO>> buscarAniversariantesOutubroDezembro() {
        List<FuncionarioDTO> aniversariantes = funcionarioService.buscarAniversariantesOutubroDezembro();
        return ResponseEntity.ok(aniversariantes);
    }

    @GetMapping("/maiorIdade")
    public ResponseEntity<FuncionarioDTO> buscarFuncionarioMaiorIdade() {
        FuncionarioDTO funcionarioMaisVelho = funcionarioService.buscarFuncionarioMaiorIdade();
        return ResponseEntity.ok(funcionarioMaisVelho);
    }

    @GetMapping("/totalSalarios")
    public ResponseEntity<BigDecimal> calcularTotalSalarios() {
        BigDecimal totalSalarios = funcionarioService.calcularTotalSalarios();
        return ResponseEntity.ok(totalSalarios);
    }

    @GetMapping("/salariosMinimos")
    public ResponseEntity<Map<String, BigDecimal>> calcularSalariosMinimos() {
        Map<String, BigDecimal> salariosMinimos = funcionarioService.calcularSalariosMinimos();
        return ResponseEntity.ok(salariosMinimos);
    }

    @DeleteMapping("/excluirJoao")
    public ResponseEntity<Void> excluirFuncionarioJoao() {
        funcionarioService.excluirFuncionarioJoao();
        return ResponseEntity.noContent().build();
    }

}
