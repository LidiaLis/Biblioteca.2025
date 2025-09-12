/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Livro;
import java.sql.*;

/**
 *
 * @author Lídia Lis
 */
public interface LivroDAO {
    public int inserir(Livro livro);
    public int editar(Livro livro);
    public int apagar(int id_livro) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    public List<Livro> listar();
    public Livro buscaPorId(int idLivro);
    public Livro listar(int id_livro);
}
