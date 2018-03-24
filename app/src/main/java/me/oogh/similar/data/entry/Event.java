package me.oogh.similar.data.entry;

import java.util.List;

/**
 * Created by oogh on 18-3-20.
 */

public class Event {
    public enum Tag {
        MURMUR_ADD_COMPLETED,
        MURMUR_EDIT_COMPLETED,
        MURMUR_PARAM

    }

    public static class MurmurListEvent {
        public List<Murmur> murmurs;

        public MurmurListEvent(List<Murmur> murmurs) {
            this.murmurs = murmurs;
        }
    }

    public static class MurmurEvent {
        public Murmur murmur;
        public Tag tag;

        public MurmurEvent(Murmur murmur, Tag tag) {
            this.murmur = murmur;
            this.tag = tag;
        }
    }
}
