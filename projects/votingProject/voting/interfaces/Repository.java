package interfaces;

import java.util.List;

public interface Repository<T> {

    boolean save(T t);

    boolean update(T t);

    boolean delete(String id);

    T findById(String id);

    List<T> findAll();

    boolean exists(String id);
}
