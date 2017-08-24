package com.geoloc.trees;

import lombok.Data;

/**
 * Abstract definition of tree
 */
@Data
public abstract class Tree {

    protected long height;
    protected long circuit;
    protected long numberOfBranches;
    protected float depthOfRoot;

    /**
     * Grows tree
     */
    public abstract void grow();
}
