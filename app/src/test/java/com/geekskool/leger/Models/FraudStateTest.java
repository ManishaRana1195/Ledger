package com.geekskool.leger.Models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by manisharana on 3/1/17.
 */
public class FraudStateTest {

    private FraudState fraudState;

    @Before
    public void init(){
        fraudState = new FraudState();
    }

    @Test
    public void testGetStateOptions() throws Exception {
        assertEquals(fraudState.getStateOptions(),StateOptions.fraud);
    }

    @Test
    public void shouldUpdateState() throws Exception {
        Result result = fraudState.updateState(StateOptions.unverified);
        assertTrue(result.isSuccess());
    }

    @Test
    public void shouldNotUpdateState() throws Exception {
        Result result = fraudState.updateState(StateOptions.verified);
        assertFalse(result.isSuccess());
    }
}