package com.revature.util;

import java.io.IOException;
import java.sql.SQLException;

public interface Record {
	
	public void save() throws SQLException, IOException;

}
