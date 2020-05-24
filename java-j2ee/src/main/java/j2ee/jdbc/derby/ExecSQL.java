package j2ee.jdbc.derby;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * 描述: 输入sql并执行 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/24 0024 <br>
 */
public class ExecSQL {

    public static void main(String[] args) throws IOException {
        Scanner in = args.length == 0 ? new Scanner(System.in) : new Scanner(Paths.get(args[0]));
        try (Connection conn = getConnection()) {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            // 返回0表示不限制或者未知的
            System.out.println("Max Connections:" + dbMetaData.getMaxConnections());
            System.out.println("Max Statements:" + dbMetaData.getMaxStatements());
            Statement stat = conn.createStatement();
            while (true) {
                if (args.length == 0)
                    System.out.println("Enter command or Exit to exit:");

                if (!in.hasNextLine())
                    return;

                String line = in.nextLine();
                if (line.equalsIgnoreCase("Exit"))
                    return;

                if (line.trim().endsWith(";")) {
                    line = line.trim();
                    line = line.substring(0, line.length() - 1);
                }



                try {
                    boolean isResult = stat.execute(line);
                    if (isResult) {
                        ResultSet resultSet = stat.getResultSet();
                        showResultSet(resultSet);
                    } else {
                        int updateCount = stat.getUpdateCount();
                        System.out.println(updateCount + " rows updated");
                    }
                } catch (SQLException e) {
                    for (Throwable throwable : e) {
                        throwable.printStackTrace();
                    }
                }

            }
        } catch (SQLException e) {
            for (Throwable throwable : e) {
                throwable.printStackTrace();
            }
        }

    }

    private static void showResultSet(ResultSet result) throws SQLException {
        ResultSetMetaData meta = result.getMetaData();
        int columnCount = meta.getColumnCount();

        // 注意：与数组的索引不同，数据库的序列号是从1开始计算的
        for (int i = 1; i <= columnCount; i++) {
            if (i > 1) System.out.print(", ");
            System.out.print(meta.getColumnLabel(i));
        }

        System.out.println();

        while (result.next()) {
            // 注意：与数组的索引不同，数据库的序列号是从1开始计算的
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) System.out.print(", ");
                System.out.print(result.getString(i));
            }
            System.out.println();
        }


    }

    /**
     * 根据配置文件信息获取连接
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public static Connection getConnection() throws IOException, SQLException {
        Properties props = new Properties();
        try (InputStream in = ExecSQL.class.getClassLoader().getResourceAsStream("database.properties")) {
            props.load(in);
        }

        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null)
            System.out.println(drivers);

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, username, password);
    }
}
