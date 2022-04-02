package settingInterface;

public interface SqlSetting {
	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final String URL =
			"jdbc:mysql://ec2rdsdatabase.c0heqotratpa.ap-northeast-1.rds.amazonaws.com:3306/";
	final String USER_NAME = "ec2rdsAdmin";
	final String PASSWORD = "nakadashide19";
	final String DB_NAME = "aaaa";
	final String TABLE_NAME = "rdsTest";
	final String[] COLUMNS_NAMES = {"id","name","value","score"};
	final String[] COLUMNS_NAME_AS = {"","名前","",""};
	final String SDF_FORMAT = "yyyy/MM/dd";
}
