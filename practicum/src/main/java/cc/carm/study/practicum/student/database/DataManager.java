package cc.carm.study.practicum.student.database;

import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.beecp.BeeDataSourceConfig;

import java.io.File;
import java.sql.SQLException;

public class DataManager {

    private SQLManager sqlManager;

    public DataManager() {
    }

    public boolean initialize() {
        try {
            File folder = new File(".tmp");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            BeeDataSourceConfig config = new BeeDataSourceConfig();
            File file = new File(folder, "data").getCanonicalFile();
            config.setPrintRuntimeLog(false);
            config.setDriverClassName("org.h2.Driver");
            config.setJdbcUrl("jdbc:h2:" + file.getAbsolutePath() + ";DB_CLOSE_DELAY=-1;MODE=MYSQL;DB_CLOSE_ON_EXIT=TRUE");
            this.sqlManager = EasySQL.createManager(config);
            this.sqlManager.executeSQL("SET MODE=MYSQL");
        } catch (Exception exception) {
            System.out.println("无法连接到数据库，请检查配置文件。");
            exception.printStackTrace();
            return false;
        }

        try {
            DataTables.initializeTables(this.sqlManager);
        } catch (SQLException e) {
            System.out.println("无法创建服务所需的表，请检查数据库权限。");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void shutdown() {
        EasySQL.shutdownManager(sql());
    }

    public SQLManager sql() {
        return sqlManager;
    }


}
