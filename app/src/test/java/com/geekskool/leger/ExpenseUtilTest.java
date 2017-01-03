package com.geekskool.leger;

import com.geekskool.leger.Models.Expense;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;

/**
 * Created by manisharana on 3/1/17.
 */
public class ExpenseUtilTest {
    private File file;
    private String inputString;

    @Before
    public void init(){
        inputString = loadDataFromFile();
    }

    private String loadDataFromFile() {
        String json = null;
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("testJson.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Test
    public void testGetExpenseList() throws Exception {
        ArrayList<Expense> expenseList = ExpenseUtil.getExpenseList(inputString);
        assertFalse(expenseList.isEmpty());
    }

}