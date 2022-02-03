package sample.dao.impl;

import sample.dao.dbconnection.DataBaseConnection;
import sample.dao.EntityDao;
import sample.model.Role;

import java.sql.*;
import java.util.Optional;
import java.util.OptionalInt;

public class RoleDao extends EntityDao {
    public static final String SELECT_ROLE_ID_QUERY = "SELECT id FROM roles WHERE name=?";
    public static final String SELECT_ROLE_NAME_QUERY = "SELECT NAME FROM roles WHERE id=?";

    public OptionalInt getIdRoleByName(Role role){
        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLE_ID_QUERY)) {
            preparedStatement.setString(1, role.getName());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return OptionalInt.of(rs.getInt("id"));
            }
            return OptionalInt.empty();
        } catch (SQLException e) {
            printSQLException(e);
            return OptionalInt.empty();
        }
    }
    public Optional<String> getRoleNameById(int roleId){
        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLE_NAME_QUERY)) {
            preparedStatement.setInt(1, roleId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return Optional.of(rs.getString("name"));
            }
            return Optional.empty();
        } catch (SQLException e) {
            printSQLException(e);
            return Optional.empty();
        }
    }
}
