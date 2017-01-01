package exelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import resource.*;
import controller.*;

public class ReadExcel2 {
	
	public static void main(String[] args) throws IOException {
	    String excelFilePath = "test_export.xlsx";
	    ReadExcel2 reader = new ReadExcel2();
	    List<Book> listBooks = reader.readBooksFromExcelFile(excelFilePath);
	    System.out.println(listBooks);
	}
	
	public List<Book> readBooksFromExcelFile(String excelFilePath) throws IOException {
	    List<Book> listBooks = new ArrayList<>();
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	    
	    //support both Excel 2003 and 2007 file format
	    Workbook workbook = new WorkbookFactory().getWorkbook(inputStream, excelFilePath);
	    
	    Sheet firstSheet = workbook.getSheetAt(0);
	    Iterator<Row> iterator = firstSheet.iterator();
	 
	    while (iterator.hasNext()) {
	        Row nextRow = iterator.next();
	        Iterator<Cell> cellIterator = nextRow.cellIterator();
	        Book aBook = new Book();
	 
	        while (cellIterator.hasNext()) {
	            Cell nextCell = cellIterator.next();
	            int columnIndex = nextCell.getColumnIndex();
	 
	            switch (columnIndex) {
	            case 1:
	                aBook.setTitle((String) getCellValue(nextCell));
	                break;
	            case 2:
	                aBook.setAuthor((String) getCellValue(nextCell));
	                break;
	            case 3:
	                aBook.setPrice((double) getCellValue(nextCell));
	                break;
	            }
	 
	 
	        }
	        listBooks.add(aBook);
	    }
	 
	    workbook.close();
	    inputStream.close();
	 
	    return listBooks;
	}
	
	private Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }
	 
	    return null;
	}
}
