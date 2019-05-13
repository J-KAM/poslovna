package com.ftn.constants;

/**
 * Created by Alex on 5/21/17.
 */
public class Sql {

    public static final String ACTIVE = "`active` = true";

    public static final String UPDATE = "UPDATE ";

    public static final String SOFT_DELETE = " SET `active` = false, updated = now() WHERE id = ?";
}
