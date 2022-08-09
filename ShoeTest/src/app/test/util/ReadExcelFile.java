package app.test.util;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ReadExcelFile {

	public Object[] getDataFromExcel(String filepath, String sheetName, String colname) {
		Object[] object = null;
		try {
			Fillo fillo = new Fillo();

			Connection connection = fillo.getConnection(filepath);
			Recordset recordset = connection.executeQuery("SELECT * FROM " + sheetName);
			int numberOfRows = recordset.getCount();
			System.out.println("Size: " + numberOfRows);
			int i = 0;
			object = new Object[numberOfRows];
			while (recordset.next()) {
				object[i] = recordset.getField(colname);
				System.out.println(object[i]);
				i++;
			}
			recordset.close();
			connection.close();
			return object;
		} catch (FilloException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}