/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucp.sw2.horario1.vtesis.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pucp.sw2.horario1.vtesis.dto.PersonaDTO;
import pucp.sw2.horario1.vtesis.modelos.Persona;

/**
 *
 * @author Hiroshi
 */
@Repository(value = "personaDAO")
public class PersonaDAO {

    @Autowired
    DataSource datasource;

    public PersonaDTO get(String email) {

        String query = "select e.EmployeeID, "
                + "e.Title, "
                + "e.FirstName, "
                + "e.LastName, "
                + "e.Email, "
                + "e.HomePhone, "
                + "e.Extension, "
                + "e.PostalCode, "
                + "e.Region, "
                + "e.Role, "
                + "e.Address, "
                + "e.City, "
                + "e.Country, "
                + "e.Enabled "
                + "from employees e "
                + "where e.Email = ?";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
        PersonaDTO employee = jdbcTemplate.queryForObject(query, new Object[]{email}, new RowMapper<PersonaDTO>() {
            @Override
            public PersonaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        return employee;
    }

    public List<PersonaDTO> listarPersona() {
        List<PersonaDTO> lstResultados = null;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
        List<Object> parametros = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();

        sql.append("select p.codigo, p.nombres, p.apellidos from persona p where Rol_idRol=3 or Rol_idRol=2 or Rol_idRol=1 ;");
        //Listando todos los alumnos, asesores o administradores
        //cursos, entregable y fecha de actualizacion.              

        lstResultados = jdbcTemplate.query(sql.toString(), parametros.toArray(),
                new RowMapper<PersonaDTO>() {
                    @Override
                    public PersonaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PersonaDTO p = new PersonaDTO();
                        p.setCodigo(rs.getString(1));
                        p.setNombres(rs.getString(2));
                        p.setApellidos(rs.getString(3));
                        return p;
                    }
                });

        return lstResultados;
    }

    public void registrarPersona(PersonaDTO persona) {
        StringBuilder sb = new StringBuilder();
        Map params = new HashMap();
        sb.append(" insert into persona (nombres, apellidos, codigo, contraseña, idRol) values(:nombres, :apellidos, :codigo, contraseña, :idRol); ");
        params.put("nombres", persona.getNombres());
        params.put("apellidos", persona.getApellidos());
        params.put("codigo", persona.getCodigo());
        params.put("contraseña", persona.getContrasena());
        params.put("idRol", persona.getIdRol());

        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(datasource);
        SqlParameterSource paramSource = new MapSqlParameterSource(params);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sb.toString(), paramSource, keyHolder);
        //seteamos el id generado
        persona.setIdPersona(keyHolder.getKey().intValue());

    }

    public void update(PersonaDTO persona) {

        StringBuilder sql = new StringBuilder();

        try {

            JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
            sql.append("UPDATE persona SET nombres = ?,"
                    + " apellidos = ? ,"
                    + " codigo = ? ,"
                    + " contraseña = ? ,"
                    //                    + " foto = ? ,"
                    + " Rol_idRol = ? ,"
                    + " WHERE idPersona = ?");

            List<Object> parametros = new ArrayList<Object>();
            parametros.add(persona.getNombres());
            parametros.add(persona.getApellidos());
            parametros.add(persona.getCodigo());
            parametros.add(persona.getContrasena());
//             parametros.add(persona.getFoto());
            parametros.add(persona.getIdRol());
            parametros.add(persona.getIdPersona());

            jdbcTemplate.update(sql.toString(), parametros.toArray());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void borrarPersona(Persona persona) {

        StringBuilder sql = new StringBuilder();

        sql.append("DELETE from persona");
        sql.append(" WHERE idPersona = ?");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
        List<Object> parametros = new ArrayList<Object>();
        parametros.add(persona.getIdPersona());
        jdbcTemplate.update(sql.toString(), parametros.toArray());

    }
}
