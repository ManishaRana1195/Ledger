package com.geekskool.leger.Models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by manisharana on 3/1/17.
 */
public class UnverifiedStateTest {

    private UnverifiedState unverifiedState;

    @Before
    public void init(){
        unverifiedState = new UnverifiedState();
    }

    @Test
    public void testGetStateOptions() throws Exception {
        assertEquals(unverifiedState.getStateOptions(),StateOptions.unverified);
    }

    @Test
    public void shouldUpdateStateToVerified() throws Exception {
        Result result = unverifiedState.updateState(StateOptions.verified);
        assertTrue(result.isSuccess());
    }

    @Test
    public void shouldUpdateStateToFraud() throws Exception {
        Result result = unverifiedState.updateState(StateOptions.fraud);
        assertTrue(result.isSuccess());
    }

    @Test
    public void shouldNotUpdateState() throws Exception {
        Result result = unverifiedState.updateState(StateOptions.unverified);
        assertFalse(result.isSuccess());
    }
}