package sample.dao.impl;

import sample.dao.dbconnection.DataBaseConnection;
import sample.dao.EntityDao;
import sample.model.CustomFile;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class FileDao extends EntityDao {
    public static final String INSERT_FILE_QUERY = "INSERT INTO files (filename, path) VALUES (?, ?)";
    public static final String INSERT_FILE_USER_QUERY = "INSERT INTO users_files (user_id, file_id) VALUES (?, ?)";
    public static final String SELECT_FILE_QUERY = "SELECT * FROM files where filename=?";
    public static final String SELECT_QUERY = "SELECT * FROM files";
    public static final String SELECT_FILES_QUERY = "SELECT * FROM files inner join users_files" +
            " on files.id=users_files.file_id where users_files.user_id=?";
    public static final String DELETE_FILE_QUERY = "DELETE FROM files where id=?";
    public static final String DELETE_FILE_USER_QUERY = "DELETE FROM users_files where user_id=? and file_id=?";
    public static final String UPDATE_FILE_QUERY = "UPDATE files SET filename=?, path=? WHERE id=?";


    public List<CustomFile> files(int userId) {
        List<CustomFile> result = new LinkedList<>();
        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FILES_QUERY)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result.add(new CustomFile(rs.getInt("id"),
                        rs.getString("filename"), rs.getString("path")));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }

    public List<CustomFile> files() {
        List<CustomFile> result = new LinkedList<>();
        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result.add(new CustomFile(rs.getInt("id"),
                        rs.getString("filename"), rs.getString("path")));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return result;
    }

    public boolean add(int userId, String filename, String path) {
        Connection connection = DataBaseConnection.getConnection();
        int fileId = getFileIdByName(filename);
        if (fileId != 0) return false;
        else {
            try (PreparedStatement preparedStatementFile = connection.prepareStatement(INSERT_FILE_QUERY);
                 PreparedStatement preparedStatementFileUser = connection.prepareStatement(INSERT_FILE_USER_QUERY)) {
                preparedStatementFile.setString(1, filename);
                preparedStatementFile.setString(2, path);
                preparedStatementFile.executeUpdate();

                preparedStatementFileUser.setInt(1, userId);
                fileId = getFileIdByName(filename);
                preparedStatementFileUser.setInt(2, fileId);
                preparedStatementFileUser.executeUpdate();

                return true;
            } catch (SQLException e) {
                printSQLException(e);
                return false;
            }
        }
    }

    public boolean delete(int fileId, int userId) {
        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatementFileUser = connection.prepareStatement(DELETE_FILE_USER_QUERY)) {
            preparedStatementFileUser.setInt(1, fileId);
            preparedStatementFileUser.setInt(2, userId);
            preparedStatementFileUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
    }

    public boolean fullDelete(int fileId) {
        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatementFileUser = connection.prepareStatement(DELETE_FILE_QUERY)) {

            preparedStatementFileUser.setInt(1, fileId);
            preparedStatementFileUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
    }

    public int getFileIdByName(String filename) {
        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FILE_QUERY)) {
            preparedStatement.setString(1, filename);
            preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.first()) {
                return rs
                        .getInt("id");
            }
            return 0;
        } catch (SQLException e) {
            printSQLException(e);
            return 0;
        }
    }

    public void edit(int fileId, String newName, String newPath) {
        Connection connection = DataBaseConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FILE_QUERY)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newPath);
            preparedStatement.setInt(3, fileId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }


}
