package study.mybatis;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 *
 */
public class App
{
    static int createTable(SqlSession session) {
    	int result = -1;
    	try {
    		result = session.update("study.mybatis.createTableUser");
    	} catch(Exception ex) {
    		throw ex;
    	}
		return result;
    }




	public static void main( String[] args )
    {
        // ★ルートとなる設定ファイルを読み込む
        try (InputStream in = App.class.getResourceAsStream("/mybatis-config.xml")) {
            // ★設定ファイルを元に、 SqlSessionFactory を作成する
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);


            // ★SqlSessionFactory から SqlSession を生成する
            try (SqlSession session = factory.openSession()) {

            	System.out.println("table作成");
            	// テーブルを作成する。
            	createTable(session);
            	System.out.println("table作成完了");

            	// データを登録する。
            	session.insert("study.mybatis.insertUser", new UserDTO(1, "あいうえお"));

                // ★SqlSession を使って SQL を実行する
                List<Map<String, Object>> result = session.selectList("study.mybatis.selectUser");

                result.forEach(row -> {
                    System.out.println("---------------");
                    row.forEach((columnName, value) -> {
                        System.out.printf("columnName=%s, value=%s%n", columnName, value);
                    });
                });


                // ★SqlSession を使って SQL を実行する
                List<UserDTO> userList = session.selectList("study.mybatis.selectUser");
                System.out.println(userList);

            }
        } catch (Exception ex) {
        	System.out.println(ex);
        }
    }
}
