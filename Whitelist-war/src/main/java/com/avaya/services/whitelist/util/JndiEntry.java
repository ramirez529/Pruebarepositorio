/////////////////////////////////////////////////////////////////////////////
//Copyright Avaya Inc., All Rights Reserved
// THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF AVAYA INC
// The copyright notice above does not evidence any
// actual or intended publication of such source code.
// Some third-party source code components may have been modified from
// their original versions by Avaya Inc.
// The modifications are Copyright Avaya Inc., All Rights Reserved.
//////////////////////////////////////////////////////////////////////////////
package com.avaya.services.whitelist.util;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class JndiEntry
{
    private final String context;
    private final String key;
    private final String value;

    public JndiEntry(final String context, final String key, final String value)
    {
        this.context = context;
        this.key = key;
        this.value = value;
    }

    public String getContext()
    {
        return context;
    }

    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(context).append(key).append(value).toHashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (obj instanceof JndiEntry)
        {
            final JndiEntry other = (JndiEntry) obj;

            return new EqualsBuilder().append(context, other.context).append(key, other.key)
                    .append(value, other.value).isEquals();
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "JndiEntry [context=" + context + ", key=" + key + ", value=" + value + "]";
    }

}
