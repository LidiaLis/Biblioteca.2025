/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import modelo.Emprestimo;

/**
 *
 * @author Usuario
 */
public interface EmprestimoDAO {
    public int inserir(Emprestimo emprestimo);
    public int editar(Emprestimo emprestimo);
    public int apagar(int id_emprestimo) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    public List<Emprestimo> listar();
    public Emprestimo listar(int id_emprestimo);
}
