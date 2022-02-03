package sample.services.file;

import sample.model.CustomFile;
import sample.model.User;

import java.util.List;

public interface FileService {
    List<CustomFile> files(int userId);
    List<CustomFile> files();
    boolean add(int userId, String path);
    void delete(User user, int idFile);
    void fullDelete(User user, int idFile);
    void edit(int fileId, String newPath);
}
