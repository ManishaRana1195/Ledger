package com.geekskool.leger.Models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by manisharana on 3/1/17.
 */
public class VerifiedStateTest {

    private VerifiedState verifiedState;

    @Before
    public void init(){
        verifiedState = new VerifiedState();
    }

    @Test
    public void testGetStateOptions() throws Exception {
        assertEquals(verifiedState.getStateOptions(),StateOptions.verified);
    }

    @Test
    public void shouldUpdateState() throws Exception {
        Result result = verifiedState.updateState(StateOptions.unverified);
        assertTrue(result.isSuccess());
    }

    @Test
    public void shouldNotUpdateState() throws Exception {
        Result result = verifiedState.updateState(StateOptions.fraud);
        assertFalse(result.isSuccess());
    }
}