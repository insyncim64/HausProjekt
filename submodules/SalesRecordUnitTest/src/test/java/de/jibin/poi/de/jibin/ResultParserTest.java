package de.jibin.poi.de.jibin;

import org.junit.Test;

import de.jibin.commons.Constants;
import de.jibin.crawler.ResultParser;
import junit.framework.TestCase;

public class ResultParserTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ResultParserTest( String testName )
    {
        super( testName );
    }
    
    @Test
    public void testAgentName()
    {
    	String name = ResultParser.getChineseName("aa杨玲a");
    	assertEquals("agent name must be 杨玲", "杨玲", name);
    	
        name = ResultParser.getChineseName("<strong>200万</strong>");
    	assertEquals("agent name must be 万", "万", name);
    	
    	name =ResultParser.getChineseName(null);
    	assertEquals("agent name must be ", "", name);
    	
    	name = ResultParser.getChineseName("人人人");
    	assertEquals("agent name must be 人人人", "人人人", name);
    }
    
    @Test
    public void testPrice()
    {
    	final ResultParser tester = new ResultParser();
    	int price = tester.getPrice("<strong>200万</strong>");
    	assertEquals("price must be " + 200, 200, price);
    	price = tester.getPrice("<strong>200</strong>");
    	assertEquals("price must be " + 200, 200, price);
    	price = tester.getPrice("320");
    	assertEquals("price must be 320", 320, price);
    	price = tester.getPrice("<strong>200test320</strong>");
    	assertEquals("price must be -1", -1, price);
    	price = tester.getPrice(null);
    	assertEquals("price must be -1", -1, price);
    }
    
    @Test
    public void testRoomDirectionType()
    {
    	final ResultParser tester = new ResultParser();
    	int type = tester.getRoomDirectionType("高楼层（共30层）南北 ");
    	assertEquals("direction type count must be " + Constants.SOUTH_TYPE, Constants.SOUTH_TYPE, type);
    	type = tester.getRoomDirectionType("高楼层（共30层）东西");
    	assertEquals("direction type count must be " + Constants.EAST_TYPE, Constants.EAST_TYPE, type);
    	type = tester.getRoomDirectionType("高楼层（共30层）");
    	assertEquals("direction type count must be " + Constants.UNKNOWN_DIRECTION_TYPE, Constants.UNKNOWN_DIRECTION_TYPE, type);
    	type = tester.getRoomDirectionType(null);
    	assertEquals("direction type count must be " + Constants.UNKNOWN_DIRECTION_TYPE, Constants.UNKNOWN_DIRECTION_TYPE, type);
    }
    
    @Test
    public void testDecorateType()
    {
    	final ResultParser tester = new ResultParser();
        int decorateType = tester.getDecorateType("高楼层（共10层）东西 毛坯");
        assertEquals("decorate type count must be " + Constants.NO_DECORATED_TYPE, Constants.NO_DECORATED_TYPE, decorateType);
        
        decorateType = tester.getDecorateType("高楼层（共10层）东西 精装");
        assertEquals("decorate type count must be " + Constants.GOOD_DECORATED_TYPE, Constants.GOOD_DECORATED_TYPE, decorateType);
        
        decorateType = tester.getDecorateType("高楼层（共10层）东西 中装");
        assertEquals("decorate type count must be " + Constants.MIDDLE_DECORATED_TYPE, Constants.MIDDLE_DECORATED_TYPE, decorateType);
        
        decorateType = tester.getDecorateType("高楼层（共10层）东西 简装");
        assertEquals("decorate type count must be " + Constants.LOW_DECORATED_TYPE, Constants.LOW_DECORATED_TYPE, decorateType);
        
        decorateType = tester.getDecorateType("高楼层（共10层）东西 装");
        assertEquals("decorate type count must be " + Constants.UNKNOWN_DECORATED_TYPE, Constants.UNKNOWN_DECORATED_TYPE, decorateType);
        
        decorateType = tester.getDecorateType(null);
        assertEquals("decorate type count must be " + Constants.UNKNOWN_DECORATED_TYPE, Constants.UNKNOWN_DECORATED_TYPE, decorateType);
    }
    
    @Test
    public void testFloorCount()
    {
    	final ResultParser tester = new ResultParser();
        int count = tester.getTotalFloorCount("高楼层（共10层）东西 毛坯");
        assertEquals("total floor count must be 10", 10, count);
        
        count = tester.getTotalFloorCount(null);
        assertEquals("total floor count must be -1", -1, count);
    
        count = tester.getTotalFloorCount("");
        assertEquals("total floor count must be -1", -1, count);
        
        count = tester.getTotalFloorCount("（共10层）");
        assertEquals("total floor count must be 10", 10, count);
        
        count = tester.getTotalFloorCount("共10层）");
        assertEquals("total floor count must be -1", -1, count);
    }
    
    @Test
    public void testRoomCounts()
    {
    	final ResultParser tester = new ResultParser();
    	
    	int[] counts = tester.getCountInStr("3室2厅 96㎡");
    	assertEquals("room count must be 3", 3, counts[0]);
        assertEquals("hall count must be 2", 2, counts[1]);
        assertEquals("size count must be 96", 96, counts[2]);
        
        
        counts = tester.getCountInStr(null);
    	assertEquals("room count must be -1", -1, counts[0]);
        assertEquals("hall count must be -1", -1, counts[1]);
        assertEquals("size count must be -1", -1, counts[2]);
        
        counts = tester.getCountInStr("");
    	assertEquals("room count must be -1", -1, counts[0]);
        assertEquals("hall count must be -1", -1, counts[1]);
        assertEquals("size count must be -1", -1, counts[2]);
        
        counts = tester.getCountInStr("㎡");
    	assertEquals("room count must be -1", -1, counts[0]);
        assertEquals("hall count must be -1", -1, counts[1]);
        assertEquals("size count must be -1", -1, counts[2]);
        
        counts = tester.getCountInStr("9㎡");
    	assertEquals("room count must be -1", -1, counts[0]);
        assertEquals("hall count must be -1", -1, counts[1]);
        assertEquals("size count must be 9", 9, counts[2]);
        
        counts = tester.getCountInStr("96㎡");
    	assertEquals("room count must be -1", -1, counts[0]);
        assertEquals("hall count must be -1", -1, counts[1]);
        assertEquals("size count must be 96", 96, counts[2]);
        
        counts = tester.getCountInStr("96m");
    	assertEquals("room count must be -1", -1, counts[0]);
        assertEquals("hall count must be -1", -1, counts[1]);
        assertEquals("size count must be -1", -1, counts[2]);
        
        counts = tester.getCountInStr("32室32厅");
    	assertEquals("room count must be 32", 32, counts[0]);
        assertEquals("hall count must be 32", 32, counts[1]);
        assertEquals("size count must be -1", -1, counts[2]);
        
        counts = tester.getCountInStr("3室2厅");
    	assertEquals("room count must be 3", 3, counts[0]);
        assertEquals("hall count must be 2", 2, counts[1]);
        assertEquals("size count must be -1", -1, counts[2]);
        
        counts = tester.getCountInStr("3室");
    	assertEquals("room count must be 3", 3, counts[0]);
        assertEquals("hall count must be -1", -1, counts[1]);
        assertEquals("size count must be -1", -1, counts[2]);
        
        counts = tester.getCountInStr("32室");
    	assertEquals("room count must be 32", 32, counts[0]);
        assertEquals("hall count must be -1", -1, counts[1]);
        assertEquals("size count must be -1", -1, counts[2]);
        
        counts = tester.getCountInStr("2厅");
    	assertEquals("room count must be -1", -1, counts[0]);
        assertEquals("hall count must be 2", 2, counts[1]);
        assertEquals("size count must be -1", -1, counts[2]);
        
        counts = tester.getCountInStr("32厅");
    	assertEquals("room count must be -1", -1, counts[0]);
        assertEquals("hall count must be 2", 32, counts[1]);
        assertEquals("size count must be -1", -1, counts[2]);
        
        counts = tester.getCountInStr("室厅");
    	assertEquals("room count must be -1", -1, counts[0]);
        assertEquals("hall count must be -1", -1, counts[1]);
        assertEquals("size count must be -1", -1, counts[2]);
    }
}
