package com.example.trw.calculator;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by TRW on 24/7/2560.
 */

public class NumberCalculateHelperTest {

    @Test
    public void changeOperator_correctWithChangeOperator() {
        String changeOperator = NumberCalculateHelper.changeOperator("15+","-");
        Assert.assertEquals("15-", changeOperator);
    }

    @Test
    public void changeOperator_incorrectWithNoChangeOperator() {
        String changeOperator = NumberCalculateHelper.changeOperator("15+","+");
        Assert.assertEquals("15+", changeOperator);
    }

    @Test
    public void changeOperator_incorrectStartWithEmpty() {
        String changeOperator = NumberCalculateHelper.changeOperator("", "+");
        Assert.assertEquals(null, changeOperator);
    }

    @Test
    public void changeOperator_incorrectStartWithNull() {
        String changeOperator = NumberCalculateHelper.changeOperator(null, "+");
        Assert.assertEquals(null, changeOperator);
    }

    @Test
    public void checkNumberInsert_correctWithInsertMinusWithEmptyValue() {
        String checkNumberInsert = NumberCalculateHelper.checkNumberInsert("","-");
        Assert.assertEquals("-", checkNumberInsert);
    }

    @Test
    public void checkNumberInsert_correctStartWithNumber() {
        String checkNumberInsert = NumberCalculateHelper.checkNumberInsert("10","+");
        Assert.assertEquals("10+", checkNumberInsert);
    }

    @Test
    public void checkNumberInsert_incorrectStartWithDecimalPoint() {
        String checkNumberInsert = NumberCalculateHelper.checkNumberInsert(".","+");
        Assert.assertEquals(null, checkNumberInsert);
    }

    @Test
    public void checkNumberInsert_incorrectStartWithEmpty() {
        String checkNumberInsert = NumberCalculateHelper.checkNumberInsert("","+");
        Assert.assertEquals(null, checkNumberInsert);
    }

    @Test
    public void checkNumberInsert_incorrectStartWithNull() {
        String checkNumberInsert = NumberCalculateHelper.checkNumberInsert(null,"+");
        Assert.assertEquals(null, checkNumberInsert);
    }


    @Test
    public void deleteText_correctWithNumber() {
        String deleteText = NumberCalculateHelper.deleteText("100");
        Assert.assertEquals("10", deleteText);
    }

    @Test
    public void deleteText_correctWithOperator() {
        String deleteText = NumberCalculateHelper.deleteText("10+");
        Assert.assertEquals("10", deleteText);
    }

    @Test
    public void deleteText_correctWithNumberWithSeparator() {
        String deleteText = NumberCalculateHelper.deleteText("1,000");
        Assert.assertEquals("100", deleteText);
    }

    @Test
    public void deleteText_incorrectWithNull() {
        String deleteText = NumberCalculateHelper.deleteText(null);
        Assert.assertEquals(null, deleteText);
    }

    @Test
    public void deleteText_incorrectWithEmpty() {
        String deleteText = NumberCalculateHelper.deleteText("");
        Assert.assertEquals(null, deleteText);
    }

    @Test
    public void groupSentence_correctWithNormalNumber() {
        String groupSentence = NumberCalculateHelper.groupSentence("1500",
                NumberCalculateHelper.SELECTION_HANDLE);
        Assert.assertEquals("1,500", groupSentence);
    }

    @Test
    public void groupSentence_correctWithDecimalNumber() {
        String groupSentence = NumberCalculateHelper.groupSentence("1500.50",
                NumberCalculateHelper.SELECTION_HANDLE);
        Assert.assertEquals("1,500.50", groupSentence);
    }

    @Test
    public void groupSentence_correctWithNumberStartWithMinus() {
        String groupSentence = NumberCalculateHelper.groupSentence("-1500",
                NumberCalculateHelper.SELECTION_HANDLE);
        Assert.assertEquals("-1,500", groupSentence);
    }

    @Test
    public void groupSentence_incorrectWithNull() {
        String groupSentence = NumberCalculateHelper.groupSentence(null,
                NumberCalculateHelper.SELECTION_HANDLE);
        Assert.assertEquals(null, groupSentence);
    }

    @Test
    public void groupSentence_incorrectWithEmpty() {
        String groupSentence = NumberCalculateHelper.groupSentence("",
                NumberCalculateHelper.SELECTION_HANDLE);
        Assert.assertEquals("", groupSentence);
    }

    @Test
    public void groupSentence_correctWithObjectOperatorMoreThanObjectNumber() {
        String groupSentence = NumberCalculateHelper.groupSentence("-1+",
                NumberCalculateHelper.SELECTION_HANDLE);
        Assert.assertEquals("-1+", groupSentence);
    }

    @Test
    public void groupSentence_correctWithObjectNumberMoreThanObjectOperator() {
        String groupSentence = NumberCalculateHelper.groupSentence("1+2",
                NumberCalculateHelper.SELECTION_HANDLE);
        Assert.assertEquals("1+2", groupSentence);
    }

    @Test
    public void groupDigits_correctWithNormalNumber() {
        String groupDigits = NumberCalculateHelper.groupDigits("1",
                NumberCalculateHelper.Base.DECIMAL);
        Assert.assertEquals("1", groupDigits);
    }

    @Test
    public void groupDigits_correctWithPositiveNumber() {
        String groupDigits = NumberCalculateHelper.groupDigits("1+",
                NumberCalculateHelper.Base.DECIMAL);
        Assert.assertEquals("1+", groupDigits);
    }

    @Test
    public void groupDigits_correctWithNegativeNumber() {
        String groupDigits = NumberCalculateHelper.groupDigits("1-",
                NumberCalculateHelper.Base.DECIMAL);
        Assert.assertEquals("1-", groupDigits);
    }

    @Test
    public void groupDigits_correctWithNumberStartWithMinus() {
        String groupDigits = NumberCalculateHelper.groupDigits("-1",
                NumberCalculateHelper.Base.DECIMAL);
        Assert.assertEquals("-1", groupDigits);
    }

    @Test
    public void groupDigits_correctWithDecimalNumber() {
        String groupDigits = NumberCalculateHelper.groupDigits("1.5",
                NumberCalculateHelper.Base.DECIMAL);
        Assert.assertEquals("1.5", groupDigits);
    }

    @Test
    public void groupDigits_correctWithDecimalNumberStartWithMinus() {
        String groupDigits = NumberCalculateHelper.groupDigits("-1.5",
                NumberCalculateHelper.Base.DECIMAL);
        Assert.assertEquals("-1.5", groupDigits);
    }

    @Test
    public void groupDigits_correctWithNumberStartWithDecimalPoint() {
        String groupDigits = NumberCalculateHelper.groupDigits(".25",
                NumberCalculateHelper.Base.DECIMAL);
        Assert.assertEquals(".25", groupDigits);
    }

    @Test
    public void group_correctWithNormalNumber() {
        String group = NumberCalculateHelper.group("100", 3, ',');
        Assert.assertEquals("100", group);
    }

    @Test
    public void group_correctWithThousandsNumber() {
        String group = NumberCalculateHelper.group("1000", 3, ',');
        Assert.assertEquals("1,000", group);
    }

    @Test
    public void group_incorrectWithEmpty() {
        String group = NumberCalculateHelper.group("", 3, ',');
        Assert.assertEquals("", group);
    }

    @Test
    public void group_incorrectWithNull() {
        String group = NumberCalculateHelper.group(null, 3, ',');
        Assert.assertEquals(null, group);
    }

    @Test
    public void getSeparatorDistance_correctWithNoBase() {
        int getSeparatorDistance = NumberCalculateHelper
                .getDecSeparatorDistance();
        Assert.assertEquals(3, getSeparatorDistance);
    }

    @Test
    public void getSeparator_correct() {
        char getSeparator = NumberCalculateHelper
                .getSeparator(NumberCalculateHelper.Base.DECIMAL);
        Assert.assertEquals(',', getSeparator);
    }

    @Test
    public void isNegative_correctWithNegativeNumber() {
        boolean isNegative =  NumberCalculateHelper.isNegative("-10");
        Assert.assertTrue(isNegative);
    }

    @Test
    public void isNegative_incorrectWithPositiveNumber() {
        boolean isNegative =  NumberCalculateHelper.isNegative("10");
        Assert.assertFalse(isNegative);
    }

    @Test
    public void isNegative_incorrectWithNull() {
        boolean isNegative =  NumberCalculateHelper.isNegative(null);
        Assert.assertFalse(isNegative);
    }

    @Test
    public void isNegative_incorrectWithEmptyString() {
        boolean isNegative =  NumberCalculateHelper.isNegative("");
        Assert.assertFalse(isNegative);
    }

    @Test
    public void getResult_correctWithNormalNumber() {
        String getResult = NumberCalculateHelper.getResult("1500+50");
        Assert.assertEquals("1,550", getResult);
    }

    @Test
    public void getResult_correctWithNumberWithSeparator() {
        String getResult = NumberCalculateHelper.getResult("1,500+50");
        Assert.assertEquals("1,550", getResult);
    }

    @Test
    public void getResult_correctWithNegativeNumber() {
        String getResult = NumberCalculateHelper.getResult("-1500+50");
        Assert.assertEquals("-1,450", getResult);
    }

    @Test
    public void getResult_correctWithDecimalNumber() {
        String getResult = NumberCalculateHelper.getResult("1500.50+50-25");
        Assert.assertEquals("1,525.50", getResult);
    }

    @Test
    public void getResult_incorrectWithoutNumber() {
        String getResult = NumberCalculateHelper.getResult("1500.50+");
        Assert.assertEquals(null, getResult);
    }

    @Test
    public void getResult_incorrectWithNull() {
        String getResult = NumberCalculateHelper.getResult(null);
        Assert.assertEquals(null, getResult);
    }

    @Test
    public void getResult_incorrectWithEmpty() {
        String getResult = NumberCalculateHelper.getResult("");
        Assert.assertEquals(null, getResult);
    }

}
