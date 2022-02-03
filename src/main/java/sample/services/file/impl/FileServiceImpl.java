package sample.services.file.impl;

import sample.dao.impl.FileDao;
import sample.model.CustomFile;
import sample.model.User;
import sample.services.file.FileService;

import java.util.List;

public class FileServiceImpl implements FileService {
    private final FileDao fileDao;

    public FileServiceImpl() {
        fileDao=new FileDao();
    }

    @Override
    public List<CustomFile> files(int userId){
        return fileDao.files(userId);
    }
    @Override
    public List<CustomFile> files(){
        return fileDao.files();
    }

    @Override
    public boolean add(int userId, String path) {
        return  (fileDao.add(userId, getName(path), path));
    }

    private String getName(String path){
        return path.substring(path.lastIndexOf("\\")+1, path.lastIndexOf("."));
    }

    @Override
    public void delete(User user, int idFile) {
        fileDao.delete(user.getId(), idFile);
    }
    @Override
    public void fullDelete(User user, int idFile) {
        fileDao.delete(user.getId(), idFile);
        fileDao.fullDelete(idFile);
    }

    @Override
    public void edit(int fileId, String newPath) {
        fileDao.edit(fileId, getName(newPath), newPath);
    }
}
