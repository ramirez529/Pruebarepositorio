/////////////////////////////////////////////////////////////////////////////
//Copyright Avaya Inc., All Rights Reserved
// THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF AVAYA INC
// The copyright notice above does not evidence any
// actual or intended publication of such source code.
// Some third-party source code components may have been modified from
// their original versions by Avaya Inc.
// The modifications are Copyright Avaya Inc., All Rights Reserved.
//////////////////////////////////////////////////////////////////////////////
package com.avaya.services.whitelist.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.openjpa.persistence.DataCache;
// MODIFICADO BY MANUEL
//@Entity(name = "WHITELIST")   .....original
@Entity(name = "TEAM")
@DataCache(timeout = CacheValues.DEFAULT_EVICTION_TIMEOUT_IN_MILLISECONDS)
@Table(name = "TEAM")
@NamedQuery(
        name = "findWhiteListEntry",
        query = "SELECT c FROM TEAM c " +
                "WHERE c.membernum= :membernum")
public class WhiteListEntry 
{
    private static final long serialVersionUID = 5884606347497301292L;

    @Id
    @Column(name = "membernum", nullable = false)
    private int membernum;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "team")
    private String team;
    @Column(name = "mobilecarrier")
    private String mobilecarrier;
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "preference")
    private String preference;

    public WhiteListEntry()
    {
    }

    public int membernum()
    {
        return membernum;
    }

    public void setmembernum(final int membernum)
    {
        this.membernum = membernum;
    }

    public String getfirstname()
    {
        return firstname;
    }

    public void setfirstname(final String firstname)
    {
        this.firstname = firstname;
    }
    public String getlastname()
    {
        return lastname;
    }

    public void setlastname(final String lastname)
    {
        this.lastname = lastname;
    }
    public String getemail()
    {
        return email;
    }

    public void setemail(final String email)
    {
        this.email = email;
    }
    public String getphone()
    {
        return phone;
    }

    public void setphone(final String phone)
    {
        this.phone = phone;
    }

    @Override
    public String toString()
    {
        return "WhiteListEntry [membernum=" + membernum
                + ", firstname=" + firstname + "]";
    }
}
