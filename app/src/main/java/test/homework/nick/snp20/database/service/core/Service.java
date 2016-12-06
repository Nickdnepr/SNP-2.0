package test.homework.nick.snp20.database.service.core;

import java.util.List;

/**
 * Created by Nick on 06.12.16.
 */
public interface Service<T> {

    long save(T t);
    List<T> getAll();
}
