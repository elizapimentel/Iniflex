package br.com.test.eliza.gupy.repositories;

import br.com.test.eliza.gupy.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query("SELECT f FROM Funcionario f WHERE f.nome <> 'João'")
    List<Funcionario> findAllExcludingJoao();

    Funcionario findByNome(String nome);

    @Query(value="SELECT n FROM Funcionario n ORDER BY n.nome ASC")
    List<Funcionario> findAllByOrderByNomeAsc();

}
