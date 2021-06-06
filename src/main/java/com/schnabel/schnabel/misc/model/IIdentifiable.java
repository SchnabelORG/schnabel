package com.schnabel.schnabel.misc.model;

/**
 * Interface used for entities containing ID's
 * 
 * @param <I> Type of ID variable
 */

public interface IIdentifiable<I>
{
    /**
     * 
     * @return id
     */
    public I getId();
}
