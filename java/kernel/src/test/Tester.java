package test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.junit.Test;

import com.tl.common.log.Log;
import com.tl.common.office.ExcelCell;
import com.tl.common.office.ExcelCellStyle;
import com.tl.common.office.ExcelCellStyle.BoldweightStyle;
import com.tl.common.office.ExcelCellStyle.BorderStyle;
import com.tl.common.office.ExcelCellStyle.FillPatternStyle;
import com.tl.common.office.ExcelCellType;
import com.tl.common.office.ExcelDataFormat;
import com.tl.common.office.ExcelReader;
import com.tl.common.office.ExcelRow;
import com.tl.common.office.ExcelSheet;
import com.tl.common.office.ExcelWriter;
import com.tl.common.office.ExcelCellStyle.Color;
import com.tl.db.DBSession;
import com.tl.db.cfg.ColumnMetaData;
import com.tl.db.cfg.DBType;
import com.tl.db.cfg.TableMetaData;
import com.tl.db.dialect.Dialect;
import com.tl.db.util.MetaDataUtils;
import com.tl.kernel.DataType;
import com.tl.kernel.EditType;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.init.Load;

public class Tester {
	private Log log = Context.getLog("tl");

	@Test
	public void testXLSWrite(){
		ExcelSheet sheet = new ExcelSheet();
		sheet.setName("test");
		ExcelRow[] rows = new ExcelRow[2];
		for (int i=0;i<rows.length;i++) {
			ExcelCell[] cells = new ExcelCell[5];
			
			if(i==0){
				ExcelCellStyle style = new ExcelCellStyle();
				style.setFillPattern(FillPatternStyle.SOLID_FOREGROUND);
				//style.setBackgroundColor(Color.GREY_25_PERCENT);
				style.setForegroundColor(Color.GREY_50_PERCENT);
				style.setBorderBottom(BorderStyle.THIN);
				style.setBorderBottomColor(Color.BLUE);
				style.setFontColor(Color.WHITE);
				style.setFontBoldweight(BoldweightStyle.BOLD);
				cells[0] = new ExcelCell(ExcelCellType.STRING, style);
				cells[0].setString("姓名");
				cells[1] = new ExcelCell(ExcelCellType.STRING, style);
				cells[1].setString("年龄");
				cells[2] = new ExcelCell(ExcelCellType.STRING, style);
				cells[2].setString("性别");
				cells[3] = new ExcelCell(ExcelCellType.STRING, style);
				cells[3].setString("生日");
				cells[4] = new ExcelCell(ExcelCellType.STRING, style);
				cells[4].setString("价格");
			}else {
				ExcelCellStyle style = new ExcelCellStyle();
				style.setBorderBottom(BorderStyle.THIN);
				style.setBorderBottomColor(Color.BLACK);
				
				cells[0] = new ExcelCell(ExcelCellType.STRING,style);
				cells[0].setString("王三");
				
				style = new ExcelCellStyle();
				style.setBorderBottom(BorderStyle.THIN);
				style.setBorderBottomColor(Color.BLACK);
				style.setFormat(ExcelDataFormat.Integer);
				cells[1] = new ExcelCell(ExcelCellType.NUMERIC, style);
				cells[1].setInt(29);
				
				style = new ExcelCellStyle();
				style.setBorderBottom(BorderStyle.THIN);
				style.setBorderBottomColor(Color.BLACK);
				style.setFormat(ExcelDataFormat.General);
				cells[2] = new ExcelCell(ExcelCellType.STRING, style);
				cells[2].setString("男");
				
				style = new ExcelCellStyle();
				style.setBorderBottom(BorderStyle.THIN);
				style.setBorderBottomColor(Color.BLACK);
				style.setFormat(ExcelDataFormat.Date);
				cells[3] = new ExcelCell(ExcelCellType.NUMERIC, style);
				cells[3].setDate("1983-06-05");
				
				style = new ExcelCellStyle();
				style.setBorderBottom(BorderStyle.THIN);
				style.setBorderBottomColor(Color.BLACK);
				style.setFormat(ExcelDataFormat.MoneyRMB2Scale);
				cells[4] = new ExcelCell(ExcelCellType.NUMERIC, style);
				cells[4].setDouble(100000.987671);
			}
			rows[i] = new ExcelRow();
			rows[i].setIndex(i);
			rows[i].setCells(cells);
			
		}
		sheet.setRows(rows);
		sheet.setFreezeCellCount(0);
		sheet.setFreezeRowCount(1);
		sheet.setFreezeShowCellCount(0);
		sheet.setFreezeShowRowCount(1);
		
		ExcelWriter writer = ExcelWriter.getInstance();
		writer.write("d:\\test111.xlsx", sheet);
	}
	
	@Test
	public void test4(){
		//Excel2003Reader reader = Excel2003Reader.getInstance("d:\\baobei.xls");
		//reader.getSheetDatas();
		ExcelReader reader = ExcelReader.getInstance();
		ExcelRow[] rows = reader.read("d:\\cust.xls").getRows();
//		if(rows!=null){
//			for (ExcelRow row : rows) {
//				if(row==null) continue;
//				String loginfo = row.getIndex()+":\t";
//				if(row.getCells() == null) continue;
//				for (ExcelCell cell : row.getCells()) {
//					if(cell==null) loginfo += "NULL\t\t\t";
//					loginfo += cell.getString()+"("+cell.getCountCells()+"x"+cell.getCountRows()+")\t\t\t";
//				}
//				
//				log.info(loginfo);
//			}
//		}
		//helper.read("d:\\cust2003.xls");
		
		//helper.read("d:\\baobei.xlsx");
		ExcelSheet sheet = new ExcelSheet();
		sheet.setName("test");
		
		sheet.setRows(rows);
		sheet.setFreezeCellCount(0);
		sheet.setFreezeRowCount(1);
		sheet.setFreezeShowCellCount(0);
		sheet.setFreezeShowRowCount(1);
		
		ExcelWriter writer = ExcelWriter.getInstance();
		writer.write("d:\\test111.xlsx", sheet);
	}
	
	@Test
	public void test3() throws Exception {
		for (EditType type : DataType.VARCHAR.EditType()) {
			log.info(type.id() + "==>" + type.typeName());
		}
		
		for (EditType type : DataType.INTEGER.EditType()) {
			log.info(type.id() + "==>" + type.typeName());
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test2() throws Exception{
		Load load = new Load();
		load.setIsWebProject(true);
		load.init();
		
		DAO dao = new DAO();
		DBSession dbSession = Context.getDBSession();
		
		ResultSet rs = dao.getSession().connection().getMetaData().getCatalogs();
		
		while(rs.next()){
			log.info(rs.getString(1));
		}
		rs.close();
	}
	
	@Test
	public void test1(){
		Load load = new Load();
		load.setIsWebProject(true);
		load.init();
		
		log.info(Context.getDBType().typeName());
		
		DBType dbType = Context.getDBType();
		Dialect dialect = dbType.dialcet();
		String topSql = dialect.getLimitString("select * from user", 0, 50);
		log.info(topSql);
		
		ColumnMetaData column = new ColumnMetaData("code");
		column.setType(java.sql.Types.VARCHAR);
		column.setDefaultValue("");
		column.setLength(255);
		column.setType(java.sql.Types.VARCHAR);
		
		log.info(dialect.getDDL4Column(column));
		
		
		ColumnMetaData[] columns = new ColumnMetaData[]{column};
		
		TableMetaData table = new TableMetaData("user");
		table.setColumns(columns);
		table.setIdColumn("AI_ID");
		String ddl = dialect.getDDL4CreateTable(table);
		log.info(ddl);
		DBSession dbSession = null;
		try {
			dbSession = Context.getDBSession();
			//dbSession.executeDDL(ddl);
			
			TableMetaData table2 = new TableMetaData("AdvItem2");
			MetaDataUtils.fillMetaData(table2, dbSession);
			log.info(table2.getName());
			ColumnMetaData ai_id = table2.getColumn("AI_ID");
			log.info(java.sql.Types.NUMERIC+"==="+ai_id.getType()+"---"+ai_id.getLength()+"---"+ai_id.getPrecision());
			log.info(table2.getIdColumn());
			log.info(table2.getIdColumns());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dbSession!=null)
				try {
					dbSession.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		
	}
}
