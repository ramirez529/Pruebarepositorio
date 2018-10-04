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
package com.avaya.services.whitelist.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.avaya.services.whitelist.db.WhiteListEntry;

public final class WhiteListEntryTest
{
//    private WhiteListEntry whiteListEntry;
//    private WhiteListEntry differentWhiteListEntry;
//    
//    private static final String CALLING_HANDLE = "+13035551212";
//    private static final String CALLED_HANDLE = "+17205551212";
//    
//    @Before
//    public void setUp()
//    {
//        whiteListEntry = new WhiteListEntry();
//        differentWhiteListEntry = new WhiteListEntry();
//    }
//   
//    @Test
//    public void getCalledHandle_default()
//    {
//        assertEquals(null, whiteListEntry.getCalledHandle());        
//    }
//
//    @Test
//    public void getCalledHandle_value()
//    {
//        whiteListEntry.setCalledHandle(CALLED_HANDLE);
//        
//        assertEquals(CALLED_HANDLE, whiteListEntry.getCalledHandle());        
//    }
//    
//    @Test
//    public void getCallingHandle_default()
//    {
//        assertEquals(null, whiteListEntry.getCallingHandle());        
//    }
//
//    @Test
//    public void getCallingNumber_value()
//    {
//        whiteListEntry.setCallingHandle(CALLING_HANDLE);
//        
//        assertEquals(CALLING_HANDLE, whiteListEntry.getCallingHandle());        
//    }
//    
//    @Test
//    public void equals_equal()
//    {
//        differentWhiteListEntry.setCalledHandle(CALLED_HANDLE);
//        differentWhiteListEntry.setCallingHandle(CALLING_HANDLE);
//        
//        whiteListEntry.setCalledHandle(CALLED_HANDLE);
//        whiteListEntry.setCallingHandle(CALLING_HANDLE);
//        
//        assertEquals(whiteListEntry, differentWhiteListEntry);
//    }
//    
//    @Test
//    public void equals_notEqual_differentCalledHandle()
//    {
//        final String DIFFERENT_CALLED_HANDLE = CALLED_HANDLE + "somethingdifferent";
//        
//        differentWhiteListEntry.setCalledHandle(DIFFERENT_CALLED_HANDLE);
//        differentWhiteListEntry.setCallingHandle(CALLING_HANDLE);
//        
//        whiteListEntry.setCalledHandle(CALLED_HANDLE);
//        whiteListEntry.setCallingHandle(CALLING_HANDLE);
//        
//        assertFalse(whiteListEntry.equals(differentWhiteListEntry));
//    }
//
//    @Test
//    public void equals_notEqual_differentCallingHandle()
//    {
//        final String DIFFERENT_CALLING_HANDLE = CALLING_HANDLE + "somethingdifferent";
//        
//        differentWhiteListEntry.setCalledHandle(CALLED_HANDLE);
//        differentWhiteListEntry.setCallingHandle(DIFFERENT_CALLING_HANDLE);
//        
//        whiteListEntry.setCalledHandle(CALLED_HANDLE);
//        whiteListEntry.setCallingHandle(CALLING_HANDLE);
//        
//        assertFalse(whiteListEntry.equals(differentWhiteListEntry));
//    }
//    
//    @Test
//    public void equals_differentObjectType()
//    {
//        assertFalse(whiteListEntry.equals("some string"));
//    }
//    
//    @Test
//    public void hashCode_differentObjectsSameClassDefaultValues()
//    {
//        assertEquals(whiteListEntry.hashCode(), new WhiteListEntry().hashCode());
//    }
//    
//    @Test
//    public void testToString()
//    {
//        assertTrue(StringUtils.isNotEmpty(whiteListEntry.toString()));
//    }
}