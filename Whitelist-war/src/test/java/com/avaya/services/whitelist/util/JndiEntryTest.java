/////////////////////////////////////////////////////////////////////////////
//Copyright Avaya Inc., All Rights Reserved
// THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF AVAYA INC
// The copyright notice above does not evidence any
// actual or intended publication of such source code.
// Some third-party source code components may have been modified from
// their original versions by Avaya Inc.
// The modifications are Copyright Avaya Inc., All Rights Reserved.
//
//////////////////////////////////////////////////////////////////////////////
package com.avaya.services.whitelist.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class JndiEntryTest
{    
    private JndiEntry jndiEntry;
    
    @Test
    public void getContext()
    {
        jndiEntry = new JndiEntry("context", null, null);
        
        assertEquals("context", jndiEntry.getContext());
    }
    
    @Test
    public void getContextNull()
    {
        jndiEntry = new JndiEntry(null, null, null);
        
        assertEquals(null, jndiEntry.getContext());
    }
    
    @Test
    public void getKey()
    {
        jndiEntry = new JndiEntry(null, "key", null);
        assertEquals("key", jndiEntry.getKey());
    }

    @Test
    public void getKeyNull()
    {
        jndiEntry = new JndiEntry(null, null, null);
        assertEquals(null, jndiEntry.getKey());
    }

    @Test
    public void getValue()
    {
        jndiEntry = new JndiEntry(null, null, "value");
        assertEquals("value", jndiEntry.getValue());
    }
    
    @Test
    public void getValueNull()
    {
        jndiEntry = new JndiEntry(null, null, null);
        assertEquals(null, jndiEntry.getValue());
    }

    @Test
    public void equal()
    {
        assertEquals(new JndiEntry("context", "key", "value"), new JndiEntry("context", "key", "value"));
    }
    
    @Test
    public void equal_sameObject()
    {
        final JndiEntry jndiEntry = new JndiEntry("context", "key", "value"); 
        assertEquals(jndiEntry, jndiEntry);
    }
    
    @Test
    public void equal_allNull()
    {
        assertEquals(new JndiEntry(null, null, null), new JndiEntry(null, null, null));
    }

    @Test
    public void equal_notEqual()
    {
        assertNotEquals(new JndiEntry("context", "key", "value"), new JndiEntry("DIFFERENTcontext", "key", "value"));
    }
    
    @Test
    public void hash()
    {
        assertEquals(new JndiEntry("context", "key", "value").hashCode(), new JndiEntry("context", "key", "value").hashCode());
    }
    
    @Test
    public void hash_allNull()
    {
        assertEquals(new JndiEntry(null, null, null).hashCode(), new JndiEntry(null, null, null).hashCode());
    }
    
    @Test
    public void hash_notSameHash()
    {
        assertNotEquals(new JndiEntry("context", "key", "value").hashCode(), new JndiEntry("DIFFERENTcontext", "key", "value").hashCode());
    }
    
    @Test
    public void to_String()
    {
        final JndiEntry jndiEntry = new JndiEntry("context", "key", "value");
        assertEquals("JndiEntry [context=context, key=key, value=value]", jndiEntry.toString());
    }

}
