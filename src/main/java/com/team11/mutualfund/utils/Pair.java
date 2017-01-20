package com.team11.mutualfund.utils;

/**
 * Created by marcusgao on 17/1/19.
 */
public class Pair<L, R> {
    private L left;
    private R right;

    public Pair() { }

    public Pair(L l, R r) {
        left = l;
        right = r;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }
}
