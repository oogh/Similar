package me.oogh.similar.murmur;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-04-10
 * @description
 */
public class MurmurException extends Exception {
    public MurmurException(String message) {
        super(message);
    }

    public MurmurException(Exception e) {
        super(e);
    }
}
