package br.com.test.eliza.gupy.dto;

import br.com.test.eliza.gupy.model.Funcionario;
import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class FuncionarioDTO {

    private String nome;
    private String dataNascimento;
    private String salario;
    private String funcao;

    public FuncionarioDTO(Funcionario funcionario) {
        this.nome = funcionario.getNome();
        this.dataNascimento = formatarData(funcionario.getDataNascimento());
        this.salario = formatarSalario(funcionario.getSalario());
        this.funcao = funcionario.getFuncao();
    }

    private String formatarData(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    private String formatarSalario(BigDecimal salario) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(salario);
    }
}
