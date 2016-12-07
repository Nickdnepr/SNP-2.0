package test.homework.nick.snp20.database.service;

import android.content.Context;
import test.homework.nick.snp20.database.dao.InfoDao;
import test.homework.nick.snp20.database.service.core.OpenDBService;
import test.homework.nick.snp20.database.service.core.Service;
import test.homework.nick.snp20.model.music_info_model.Info;

import java.util.List;

/**
 * Created by Nick on 06.12.16.
 */
public class InfoService extends OpenDBService implements Service<Info> {

    private Context context;

    public InfoService(Context context) {
        this.context = context;
    }

    @Override
    public long save(Info info) {
        try {
            if (!isOpen()) {
                open(context);
            }
            return new InfoDao(getDatabase()).save(info);
        } finally {
            if (isOpen()) {
                close();
            }
        }

    }

    @Override
    public List<Info> getAll() {
        try {
            if (!isOpen()) {
                open(context);
            }
            return new InfoDao(getDatabase()).getAll();
        } finally {
            if (isOpen()) {
                close();
            }
        }
    }
}
