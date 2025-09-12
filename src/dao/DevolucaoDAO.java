/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import modelo.Devolucao;

/**
 *
 * @author Lídia Lis
 */
public interface DevolucaoDAO {
    public Date buscarDataEmprestimo(int idEmprestimo);
    public int inserir(Devolucao devolucao);
    public int editar(Devolucao devolucao);
    public int apagar(int id_devolucao) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    public List<Devolucao> listar();
    public Devolucao listar(int id_devolucao);
    public Devolucao buscaPorId(int idEmprestimo);


}
