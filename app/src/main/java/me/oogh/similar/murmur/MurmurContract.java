package me.oogh.similar.murmur;

import java.util.List;

import me.oogh.similar.base.BasePresenter;
import me.oogh.similar.base.BaseView;
import me.oogh.similar.data.entry.Murmur;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-04
 * @description
 */

public class MurmurContract {

    public enum Type {
        DAILY("daily"),
        FUTURE("future");

        private String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum SyncType {
        SAVE, REMOVE, UPDATE
    }

    interface View extends BaseView<Presenter> {
        void showFailed();
        void showMurmurList(List<Murmur> murmurs);

        void showCachedMurmurList(List<Murmur> murmurs);
    }

    interface Presenter extends BasePresenter {
        /**
         * 存储Murmur到本地
         *
         * @param murmur
         */
        void saveMurmur(Murmur murmur);

        void listMurmur(String userId);

        void updateMurmur(Murmur murmur);

        void removeMurmur(Murmur murmur);

        void listCachedMurmur(String userId);
    }
}
