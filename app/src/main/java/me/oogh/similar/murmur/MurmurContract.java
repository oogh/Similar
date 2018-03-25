package me.oogh.similar.murmur;

import java.util.List;

import me.oogh.similar.base.BasePresenter;
import me.oogh.similar.base.BaseView;
import me.oogh.similar.data.entry.Murmur;

/**
 * Created by oogh on 18-3-4.
 */

public class MurmurContract {
    interface View extends BaseView<Presenter> {

        void showEmpty();

        void showMurmurList(List<Murmur> murmurs);

    }

    interface Presenter extends BasePresenter {
        /**
         * 存储Murmur到本地
         *
         * @param murmur
         */
        void saveMurmur(Murmur murmur);

        /**
         * 列出该用户的全部Murmur
         */
        void listMurmur(String userId);

        void updateMurmur(Murmur murmur);

        void removeMurmur(Murmur murmur);
    }
}
